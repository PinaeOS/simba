package org.pinae.simba.resource;

import java.util.ArrayList;
import java.util.List;
/**
 * Bean配置属性
 * 
 * @author Huiyugeng
 *
 */
public class BeanConfig {
	private String beanName = null;
	private String beanClass = null;
	private String create = null;
	private String run = null;
	private String singleton = null;
	private String destroy = null;
	private String timeout = "0";
	private String factoryBean = null;
	private String factoryMethod = null;
	private List<Object> propertyConfig = new ArrayList<Object>();
	private ConstructorConfig constructorConfig;
	
	/**
	 * 获得构造函数配置
	 * 
	 * @return 构造函数配置
	 */
	public ConstructorConfig getConstructorConfig() {
		return constructorConfig;
	}

	/**
	 * 设置构造函数配置
	 * 
	 * @param constructorConfig 构造函数配置
	 */
	public void setConstructorConfig(ConstructorConfig constructorConfig) {
		this.constructorConfig = constructorConfig;
	}

	/**
	 * 获得Bean名称
	 * 
	 * @return Bean名称 
	 */
	public String getBeanName(){
		return beanName;
	}
	
	/**
	 * 返回Bean实例的类名称
	 * 
	 * @return Bean实例的类名称
	 */
	public String getBeanClass(){
		return beanClass;
	}
	
	/**
	 * 返回属性配置列表
	 * 
	 * @return 属性配置列表
	 */
	public List<Object> getPropertyConfig(){
		return propertyConfig;
	}
	
	/**
	 * 设置Bean名称
	 * 
	 * @param beanName Bean名称
	 */
	public void setBeanName(String beanName){
		this.beanName = beanName;
	}
	
	/**
	 * 设置Bean实例类的名称
	 * 
	 * @param beanClass Bean实例类的名称
	 */
	public void setBeanClass(String beanClass){
		this.beanClass = beanClass;
	}
	
	/**
	 * 向属性类表中增加一个属性
	 * 
	 * @param config 属性
	 */
	public void addPropertyConfig(PropertyConfig config){
		propertyConfig.add(config);
	}

	/**
	 * 返回当Bean实例被创建后调用的方法名称
	 * 
	 * @return Bean实例被创建后调用的方法名称
	 */
	public String getCreate() {
		return create;
	}

	/**
	 * 设置Bean实例被创建后调用的方法名称
	 * 
	 * @param create Bean实例被创建后调用的方法名称
	 */
	public void setCreate(String create) {
		this.create = create;
	}

	/**
	 * 返回Bean实例被注入初始值后调用的方法名称
	 * 
	 * @return Bean实例被注入初始值后调用的方法名称
	 */
	public String getRun() {
		return run;
	}

	/**
	 * 设置Bean实例被注入初始值后调用的方法名称
	 * 
	 * @param run Bean实例被注入初始值后调用的方法名称
	 */
	public void setRun(String run) {
		this.run = run;
	}

	/**
	 * 返回Bean是否为单例
	 * 
	 * @return Bean是否为单例（true/false）
	 */
	public String getSingleton() {
		return singleton;
	}

	/**
	 * 设置Bean是否为单例
	 * 
	 * @param singleton  Bean是否为单例（true/false）
	 */
	public void setSingleton(String singleton) {
		this.singleton = singleton;
	}

	/**
	 * 返回该Bean的工厂类
	 * 
	 * @return Bean的工厂Bean
	 */
	public String getFactoryBean() {
		return factoryBean;
	}

	/**
	 * 设置Bean的工厂类
	 * 
	 * @param factoryBean Bean的工厂Bean
	 */
	public void setFactoryBean(String factoryBean) {
		this.factoryBean = factoryBean;
	}

	/**
	 * 返回生成该Bean的工厂类的方法
	 * 
	 * @return 生成该Bean的工厂类的方法
	 */
	public String getFactoryMethod() {
		return factoryMethod;
	}

	/**
	 * 设置生成该Bean的工厂类的方法
	 * 
	 * @param factoryMethod 生成该Bean的工厂类的方法
	 */
	public void setFactoryMethod(String factoryMethod) {
		this.factoryMethod = factoryMethod;
	}
	/**
	 * 返回容器销毁时调用Bean的方法
	 * @return 容器销毁时调用Bean的方法
	 */
	public String getDestroy() {
		return destroy;
	}
	/**
	 * 设置容器销毁时调用Bean的方法
	 * @param destroy 容器销毁时调用Bean的方法
	 */
	public void setDestroy(String destroy) {
		this.destroy = destroy;
	}
	/**
	 * 返回Bean的超时时间
	 * @return Bean的超时时间(ms)
	 */
	public long getTimeout() {
		long _timeout = 0;
		try{
			_timeout = Long.parseLong(timeout);
		}catch(NumberFormatException e){
			_timeout = 0;
		}
		return _timeout * 1000;
	}
	/**
	 * 设置Bean的超时时间, 如果设置为0表示没有超时限制
	 * @param timeout Bean的超时时间 (s)
	 */
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
}
