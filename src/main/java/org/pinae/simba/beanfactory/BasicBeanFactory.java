package org.pinae.simba.beanfactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pinae.simba.aop.proxy.ProxyFactory;
import org.pinae.simba.exception.InvokeException;
import org.pinae.simba.exception.NoFoundException;
import org.pinae.simba.plugin.cache.BeanCache;
import org.pinae.simba.plugin.cache.BeanCache.CacheBean;
import org.pinae.simba.resource.BeanConfig;
import org.pinae.simba.resource.ConstructorConfig;
import org.pinae.simba.resource.PropertyConfig;
import org.pinae.simba.resource.Resource;
import org.pinae.simba.resource.collection.CollectionConstant;
import org.pinae.simba.resource.collection.CollectionItem;
import org.pinae.simba.resource.collection.EntityItem;
import org.pinae.simba.resource.collection.ListItem;
import org.pinae.simba.resource.collection.SetItem;

/**
 * 实现BeanFactory接口, 实现基本的功能 包括对象生成和方法注入, 执行 create,run,destroy指定的方法
 * 
 * @author Huiyugeng
 *
 */
public class BasicBeanFactory implements BeanFactory, KernelConstant {

	private Resource config = null;

	/**
	 * 构造函数
	 */
	public BasicBeanFactory() {
		BeanCache.clear();
	}

	/**
	 * 构造函数
	 * 
	 * @param config Bean的资源信息
	 */
	public BasicBeanFactory(Resource config) {
		BeanCache.clear();
		this.config = config;
	}

	/**
	 * 设置Bean的资源信息
	 * 
	 * @param config Bean的资源信息
	 */
	public void setConfig(Resource config) {
		this.config = config;
	}

	/**
	 * 获取Bean的资源信息
	 * 
	 * @return Bean的资源信息
	 */
	public Resource getConfig() {
		return this.config;
	}

	/**
	 * 获得Bean实例
	 * 
	 * @param beanname Bean名称
	 * 
	 * @return Object 返回对象
	 * @throws InvokeException 调用异常
	 * @throws NoFoundException 没有找到类或者方法引发的异常
	 */
	@SuppressWarnings("rawtypes")
	public Object getBean(String beanname) throws InvokeException, NoFoundException {
		
		BeanConfig beanConfig = config.getBeanConfig(beanname);

		Class beanClass = null;
		Object beanObject = null;
		try {
			/*
			 * 从Bean缓存中查找该Bean是否被缓存过, 如果缓存且没有超时, 则从Cache中取对象
			 * 如果缓存对象超时则先执行destroy方法（如果destroy存在）从缓存中移除对象, 然后重新建立 如果缓存没有对象,
			 * 则新建对象
			 */
			if (beanConfig.getSingleton() == null || beanConfig.getSingleton().equals("") || beanConfig.getSingleton().equalsIgnoreCase(TRUE)) {
				if (BeanCache.isCache(beanConfig.getBeanName())) {
					CacheBean cacheBean = BeanCache.getBean(beanConfig.getBeanName());
					long now = System.currentTimeMillis();
					if (beanConfig.getTimeout() != 0 && now - cacheBean.getTimestamp() > beanConfig.getTimeout()) {
						if (beanConfig.getDestroy() != null && !beanConfig.getDestroy().equals("")) {
							invokeDestroy(beanConfig, cacheBean.getBean());
						}
						BeanCache.removeBean(beanConfig.getBeanName());
					} else {
						return cacheBean.getBean();
					}

				}
			}

			if (beanConfig.getFactoryBean() != null && beanConfig.getFactoryMethod() != null) {
				// 通过工厂方法生成一个Bean
				beanObject = getObjectByFactory(beanConfig);
				beanClass = beanObject.getClass();
			} else {
				// 构造Bean Class
				beanClass = Class.forName((String) beanConfig.getBeanClass());
				// Bean构造函数
				if (beanConfig.getConstructorConfig() != null && beanConfig.getConstructorConfig().getSize() > 0) {
					beanObject = constructor(beanClass, beanConfig.getConstructorConfig());
				} else {
					beanObject = beanClass.newInstance();
				}
			}

			// 在类创建后, 执行Bean指定的create方法
			if (beanConfig.getCreate() != null && !beanConfig.getCreate().equals("")) {
				invokeCreate(beanConfig, beanClass, beanObject);
			}

			// 向Bean中注入值, 包括值/映射/Map/List/Set/Collection
			Iterator methodConfigs = beanConfig.getPropertyConfig().iterator();
			while (methodConfigs.hasNext()) {
				PropertyConfig propertyConfig = (PropertyConfig) methodConfigs.next();
				setMethod(propertyConfig, beanClass, beanObject);
			}

			// 在值注入完毕后, 执行Bean指定的run方法
			if (beanConfig.getRun() != null && !beanConfig.getRun().equals("")) {
				invokeRun(beanConfig, beanClass, beanObject);
			}

			// 向缓存中增加Bean实例
			if (beanConfig.getSingleton() == null || beanConfig.getSingleton().equals("") || beanConfig.getSingleton().equals(TRUE)) {
				BeanCache.addBean(beanConfig.getBeanName(), beanObject);
			}
		} catch (SecurityException e) {
			throw new InvokeException(e);
		} catch (IllegalArgumentException e) {
			throw new InvokeException(e);
		} catch (IllegalAccessException e) {
			throw new InvokeException(e);
		} catch (ClassNotFoundException e) {
			throw new NoFoundException(e);
		} catch (InstantiationException e) {
			throw new InvokeException(e);
		} catch (NoSuchMethodException e) {
			throw new NoFoundException(e);
		} catch (InvocationTargetException e) {
			throw new InvokeException(e);
		}
		
		// 如果对象时AOP的代理工厂, 则直接从工厂中获得AOP后的对象
		if (beanObject != null && beanObject instanceof ProxyFactory) {
			beanObject = ((ProxyFactory)beanObject).getObject();
		}
		
		return beanObject;

	}

	/*
	 * 通过工厂类(factory-bean)和工厂方法(factory-method)返回对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object getObjectByFactory(BeanConfig beanConfig) throws ClassNotFoundException, SecurityException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class factoryBean = Class.forName((String) beanConfig.getFactoryBean());
		Method factoryMethod = factoryBean.getMethod(beanConfig.getFactoryMethod(), null);
		Object factoryObject = factoryBean.newInstance();
		return (factoryMethod.invoke(factoryObject, null));
	}

	/*
	 * 根据构造函数进行注入
	 */
	@SuppressWarnings("rawtypes")
	private Object constructor(Class cls, ConstructorConfig config) throws InstantiationException, IllegalAccessException, SecurityException,
			NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, InvokeException, NoFoundException {

		Constructor[] constructors = cls.getDeclaredConstructors();
		// 如果构造函数的参数类型为REFLECTION, 则生成对应的类写在ConstructorConfig的parameter中
		for (int i = 0; i < config.getParameter().length; i++) {
			if (config.getParameterType()[i].equals(PROPERTY_REFLECTION)) {
				config.getParameter()[i] = getBean((String) config.getParameter()[i]);
			}
		}

		// 当有多个构造函数时, 需要判断哪个构造函数适合, flag是判断构造函数时候满足注入的类型
		boolean flag = false;
		for (int i = 0; i < constructors.length; i++) {
			if (config.getSize() == constructors[i].getParameterTypes().length) {
				Class[] parameterTypes = new Class[config.getSize()];
				Object[] parameterValues = config.getParameter();

				for (int j = 0; j < constructors[i].getParameterTypes().length; j++) {
					parameterTypes[j] = constructors[i].getParameterTypes()[j];
					if (parameterTypes[j].equals(parameterValues[j].getClass())) {
						flag = true;
					} else {
						flag = false;
					}
				}
				if (flag) {
					return constructors[i].newInstance(parameterValues);
				}
			}
		}
		return null;
	}

	/*
	 * 设置方法, 将值/映射/Collection/Map/List/Set进行注入
	 */
	@SuppressWarnings("rawtypes")
	private void setMethod(PropertyConfig propertyConfig, Class cls, Object obj) throws InvokeException, NoFoundException {
		String name = propertyConfig.getPropertyName();
		String type = propertyConfig.getPropertyType();
		String methodName = "set" + Character.toString(name.charAt(0)).toUpperCase() + name.substring(1);
		String value = propertyConfig.getPropertyValue();
		if (type.equalsIgnoreCase(PROPERTY_VALUE)) {
			// 通过反射将属性值赋予对应的方法
			String parameterType = "string";
			try {
				Field field = cls.getDeclaredField(name);
				if (field != null) {
					parameterType = field.getType().getName();
				}
			} catch (SecurityException e) {
				throw new InvokeException(e);
			} catch (NoSuchFieldException e) {
				
			}
			setPropertyValue(methodName, parameterType, cls, obj, value);
		} else if (type.equalsIgnoreCase(PROPERTY_REFLECTION)) {
			// 从config表中搜索需要建立的类
			setPropertyReflection(methodName, cls, obj, value, this.config);
		} else if (type.equalsIgnoreCase(PROPERTY_MAP)) {
			setMap(methodName, propertyConfig, cls, obj, this.config);
		} else if (type.equalsIgnoreCase(PROPERTY_LIST)) {
			setList(methodName, propertyConfig, cls, obj, this.config);
		} else if (type.equalsIgnoreCase(PROPERTY_SET)) {
			setSet(methodName, propertyConfig, cls, obj, this.config);
		} else if (type.equalsIgnoreCase(PROPERTY_COLLECTION)) {
			setCollection(methodName, propertyConfig, cls, obj, this.config);
		}
	}

	/*
	 * 在对象被载入后, 执行create方法
	 */
	@SuppressWarnings("rawtypes")
	private void invokeCreate(BeanConfig beanConfig, Class cls, Object obj)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String createMethod = beanConfig.getCreate();
		Method method = cls.getMethod(createMethod, null);
		method.invoke(obj, null);
	}

	/*
	 * 在对象初始化完成后, 执行run方法
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void invokeRun(BeanConfig beanConfig, Class cls, Object obj)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String runMethod = beanConfig.getRun();
		Method method = cls.getMethod(runMethod, null);
		method.invoke(obj, null);
	}

	/*
	 * 在对象从缓存中移除时, 执行destroy方法
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void invokeDestroy(BeanConfig beanConfig, Object obj)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String createMethod = beanConfig.getDestroy();
		Class cls = obj.getClass();
		Method method = cls.getMethod(createMethod, null);
		method.invoke(obj, null);
	}

	/*
	 * 注入值
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setPropertyValue(String methodName, String type, Class cls, Object obj, String value) throws InvokeException, NoFoundException {

		Object methodValue = value;

		if (TypeConver.isBasicType(type)) {
			methodValue = TypeConver.converValue(type, value);
		}

		Method method = null;
		try {
			method = cls.getMethod(methodName, TypeConver.getTypeClass(type));
		} catch (SecurityException e) {
			throw new InvokeException(e);
		} catch (NoSuchMethodException e) {
			throw new NoFoundException(e);
		} catch (ClassNotFoundException e) {
			throw new NoFoundException(e);
		}
		try {
			if (method != null) {
				method.invoke(obj, methodValue);
			}
		} catch (IllegalArgumentException e) {
			throw new InvokeException(e);
		} catch (IllegalAccessException e) {
			throw new InvokeException(e);
		} catch (InvocationTargetException e) {
			throw new InvokeException(e);
		}
	}

	/*
	 * 注入映射方法
	 */
	@SuppressWarnings("rawtypes")
	private void setPropertyReflection(String methodName, Class cls, Object obj, String value, Resource config)
			throws InvokeException, NoFoundException {

		Object refObject = null;

		CacheBean cacheBean = BeanCache.getBean(value);
		if (cacheBean != null) {
			refObject = cacheBean.getBean();
		}
		if (refObject == null) {
			BasicBeanFactory tempObj = new BasicBeanFactory(config);
			// 从config表中搜索需要建立的类
			refObject = tempObj.getBean(value);
		}
		if (refObject != null) {
			Method method = findMethod(methodName, cls, refObject);
			try {
				if (method != null) {
					method.invoke(obj, refObject);
				} else {
					throw new NoFoundException("No such method: " + methodName);
				}
			} catch (IllegalArgumentException e) {
				throw new InvokeException(e);
			} catch (IllegalAccessException e) {
				throw new InvokeException(e);
			} catch (InvocationTargetException e) {
				throw new InvokeException(e);
			}
		}
	}

	/*
	 * 根据类, 方法名和参数, 查找合适的注入方法 首先从参数类型找, 没有则找参数类实现的方法, 如果没有则找超类
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Method findMethod(String methodName, Class cls, Object refObject) throws InvokeException, NoFoundException {
		Method method = null;
		Class refClass = refObject.getClass();
		try {
			method = cls.getMethod(methodName, refClass);
		} catch (SecurityException e) {
			throw new InvokeException(e);
		} catch (NoSuchMethodException e) {
			List<Class<?>> refClassList = getRefClass(refClass);
			for (int i = 0; i < refClassList.size(); i++) {
				try {
					method = cls.getMethod(methodName, refClassList.get(i));
				} catch (NoSuchMethodException _e) {
					method = null;
				}
				if (method != null) {
					return method;
				}
			}
			throw new NoFoundException(e);
		}
		return method;
	}

	/*
	 * 注入Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setMap(String methodName, PropertyConfig propertyConfig, Class cls, Object obj, Resource config)
			throws InvokeException, NoFoundException {
		Map map = null;
		try {
			map = (Map) (Class.forName((String) propertyConfig.getCollectionClass())).newInstance();
		} catch (InstantiationException e) {
			throw new InvokeException(e);
		} catch (IllegalAccessException e) {
			throw new InvokeException(e);
		} catch (ClassNotFoundException e) {
			throw new NoFoundException(e);
		}
		EntityItem[] entities = (EntityItem[]) propertyConfig.getCollectionItem();
		for (EntityItem entity : entities) {
			if (entity.getValueType() == CollectionConstant.VALUE)
				map.put(entity.getKey(), entity.getValue());
			if (entity.getValueType() == CollectionConstant.REFLECTION) {
				BasicBeanFactory tempObj = new BasicBeanFactory(config);
				Object refObject = tempObj.getBean(entity.getValue().toString());
				map.put(entity.getKey(), refObject);
			}

		}
		Method method = null;
		try {
			method = cls.getMethod(methodName, Map.class);
		} catch (SecurityException e) {
			throw new InvokeException(e);
		} catch (NoSuchMethodException e) {
			throw new NoFoundException(e);
		}
		try {
			method.invoke(obj, map);
		} catch (IllegalArgumentException e) {
			throw new InvokeException(e);
		} catch (IllegalAccessException e) {
			throw new InvokeException(e);
		} catch (InvocationTargetException e) {
			throw new InvokeException(e);
		}
	}

	/*
	 * 注入List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setList(String methodName, PropertyConfig propertyConfig, Class cls, Object obj, Resource config)
			throws InvokeException, NoFoundException {
		List list = null;
		try {
			list = (List) (Class.forName((String) propertyConfig.getCollectionClass())).newInstance();
		} catch (InstantiationException e) {
			throw new InvokeException(e);
		} catch (IllegalAccessException e) {
			throw new InvokeException(e);
		} catch (ClassNotFoundException e) {
			throw new NoFoundException(e);
		}
		ListItem[] items = (ListItem[]) propertyConfig.getCollectionItem();
		for (ListItem item : items) {
			if (item.getItemType() == CollectionConstant.VALUE)
				list.add(item.getItem());
			if (item.getItemType() == CollectionConstant.REFLECTION) {
				BasicBeanFactory tempObj = new BasicBeanFactory(config);
				Object refObject = tempObj.getBean(item.getItem().toString());
				list.add(refObject);
			}
		}
		Method method = null;
		try {
			method = cls.getMethod(methodName, List.class);
		} catch (SecurityException e) {
			throw new InvokeException(e);
		} catch (NoSuchMethodException e) {
			throw new NoFoundException(e);
		}
		try {
			method.invoke(obj, list);
		} catch (IllegalArgumentException e) {
			throw new InvokeException(e);
		} catch (IllegalAccessException e) {
			throw new InvokeException(e);
		} catch (InvocationTargetException e) {
			throw new InvokeException(e);
		}
	}

	/*
	 * 注入Set
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setSet(String methodName, PropertyConfig propertyConfig, Class cls, Object obj, Resource config)
			throws InvokeException, NoFoundException {
		Set set = null;
		try {
			set = (Set) (Class.forName((String) propertyConfig.getCollectionClass())).newInstance();
		} catch (InstantiationException e) {
			throw new InvokeException(e);
		} catch (IllegalAccessException e) {
			throw new InvokeException(e);
		} catch (ClassNotFoundException e) {
			throw new NoFoundException(e);
		}
		SetItem[] items = (SetItem[]) propertyConfig.getCollectionItem();
		for (SetItem item : items) {
			if (item.getItemType() == CollectionConstant.VALUE)
				set.add(item.getItem());
			if (item.getItemType() == CollectionConstant.REFLECTION) {
				BasicBeanFactory tempObj = new BasicBeanFactory(config);
				Object refObject = tempObj.getBean(item.getItem().toString());
				set.add(refObject);
			}
		}
		Method method = null;
		try {
			method = cls.getMethod(methodName, Set.class);
		} catch (SecurityException e) {
			throw new InvokeException(e);
		} catch (NoSuchMethodException e) {
			throw new NoFoundException(e);
		}
		try {
			method.invoke(obj, set);
		} catch (IllegalArgumentException e) {
			throw new InvokeException(e);
		} catch (IllegalAccessException e) {
			throw new InvokeException(e);
		} catch (InvocationTargetException e) {
			throw new InvokeException(e);
		}
	}

	/*
	 * 注入Collection
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setCollection(String methodName, PropertyConfig propertyConfig, Class cls, Object obj, Resource config)
			throws InvokeException, NoFoundException {
		Collection collection = null;
		try {
			collection = (Collection) (Class.forName((String) propertyConfig.getCollectionClass())).newInstance();
		} catch (InstantiationException e) {
			throw new InvokeException(e);
		} catch (IllegalAccessException e) {
			throw new InvokeException(e);
		} catch (ClassNotFoundException e) {
			throw new NoFoundException(e);
		}
		CollectionItem[] items = (CollectionItem[]) propertyConfig.getCollectionItem();
		for (CollectionItem item : items) {
			if (item.getItemType() == CollectionConstant.VALUE)
				collection.add(item.getItem());
			if (item.getItemType() == CollectionConstant.REFLECTION) {
				BasicBeanFactory tempObj = new BasicBeanFactory(config);
				Object refObject = tempObj.getBean(item.getItem().toString());
				collection.add(refObject);
			}
		}
		Method method = null;
		try {
			method = cls.getMethod(methodName, Collection.class);
		} catch (SecurityException e) {
			throw new InvokeException(e);
		} catch (NoSuchMethodException e) {
			throw new NoFoundException(e);
		}
		try {
			method.invoke(obj, collection);
		} catch (IllegalArgumentException e) {
			throw new InvokeException(e);
		} catch (IllegalAccessException e) {
			throw new InvokeException(e);
		} catch (InvocationTargetException e) {
			throw new InvokeException(e);
		}
	}
	
	private List<Class<?>> getRefClass(Class<?> clazz) {
		List<Class<?>> refClassList = new ArrayList<Class<?>>();
		
		if (clazz != null) {
			Class<?> refClassInterfaces[] = clazz.getInterfaces();
			
			for (Class<?> refClassInterface : refClassInterfaces) {
				refClassList.add(refClassInterface);
				refClassList.addAll(getRefClass(refClassInterface));
			}
			
			Class<?> refSuperClass = clazz.getSuperclass();
			if (refSuperClass != null) {
				refClassList.add(refSuperClass);
				refClassList.addAll(getRefClass(refSuperClass));
			}
			
		}
		
		return refClassList;
	}
	
}
