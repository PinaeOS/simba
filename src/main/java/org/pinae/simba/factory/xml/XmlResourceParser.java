package org.pinae.simba.factory.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.pinae.simba.exception.XmlParseException;
import org.pinae.simba.resource.AopConfig;
import org.pinae.simba.resource.BeanConfig;
import org.pinae.simba.resource.ConstructorConfig;
import org.pinae.simba.resource.ImportConfig;
import org.pinae.simba.resource.PropertyConfig;
import org.pinae.simba.resource.Resource;
import org.pinae.simba.resource.collection.CollectionConstant;
import org.pinae.simba.resource.collection.CollectionItem;
import org.pinae.simba.resource.collection.EntityItem;
import org.pinae.simba.resource.collection.ListItem;
import org.pinae.simba.resource.collection.SetItem;

/**
 * 通过解析XML获得Bean的配置信息
 * 
 * @author Huiyugeng
 */
public class XmlResourceParser implements XmlConstant, CollectionConstant {

	private String schemaFile;
	
	private Resource resource = new Resource();

	/**
	 * 从XML文件流获得Bean的配置信息
	 * 
	 * @param xml XML文件流
	 * @param validateXml 验证XML
	 * 
	 * @return 返回XML配置
	 * @throws XmlParseException XML解析发生的异常
	 */
	public Resource getConfig(InputStream xml, boolean validateXml) throws XmlParseException {
		Document doc = null;
		try {
			SAXBuilder builder = new SAXBuilder(validateXml);

			if (validateXml) {
				builder.setFeature(schemaFeatures, true);
				if (schemaFile == null || schemaFile.equals("")) {
					builder.setProperty(schemaProperties, defaultSchemaFile);
				} else {
					builder.setProperty(schemaProperties, schemaFile);
				}
			}
			doc = builder.build(xml);
		} catch (JDOMException e) {
			throw new XmlParseException(e);
		} catch (IOException e) {
			throw new XmlParseException(e);
		}

		Element root = doc.getRootElement();

		Object[] beans = root.getChildren().toArray();
		// 遍历配置文件中所有的Bean
		for (Object objBean : beans) {
			Element bean = (Element) objBean;
			if (bean.getName().equalsIgnoreCase(IMPORT)) {
				ImportConfig importConfig = new ImportConfig();
				Resource importResource = new Resource();
				importConfig.setUrl(bean.getAttributeValue(URL));
				try {
					String url = bean.getAttributeValue(URL);
					importResource = getConfig(new FileInputStream(url), validateXml);
				} catch (FileNotFoundException e) {
					throw new XmlParseException(e);
				}
				importConfig.mergeResource(this.resource, importResource);
			} else if (bean.getName().equalsIgnoreCase(BEAN)) {
				this.resource.addBeanConfig(bean.getAttributeValue(NAME), getBean(bean));
			} else if (bean.getName().equalsIgnoreCase(AOP)) {
				this.resource.addBeanConfig(bean.getAttributeValue(NAME), getAopBean(bean));
			}
		}
		return this.resource;
	}

	private BeanConfig getBean(Element bean) {
		BeanConfig beanConfig = new BeanConfig();

		beanConfig.setBeanName(bean.getAttributeValue(NAME));
		beanConfig.setBeanClass(bean.getAttributeValue(CLASS));
		beanConfig.setCreate(bean.getAttributeValue(CREATE));
		beanConfig.setRun(bean.getAttributeValue(RUN));
		beanConfig.setDestroy(bean.getAttributeValue(DESTROY));
		beanConfig.setTimeout(bean.getAttributeValue(TIMEOUT));
		beanConfig.setFactoryMethod(bean.getAttributeValue(FACTORY_METHOD));
		beanConfig.setSingleton(bean.getAttributeValue(SINGLETON));
		beanConfig.setFactoryBean(bean.getAttributeValue(FACTORY_BEAN));
		beanConfig.setFactoryMethod(bean.getAttributeValue(FACTORY_METHOD));

		Object[] propertys = bean.getChildren().toArray();
		for (Object objProperty : propertys) {
			Element property = (Element) objProperty;
			if (property.getName().equalsIgnoreCase(CONSTRUCTOR)) {
				beanConfig.setConstructorConfig(getConstructor(property));
			}
			if (property.getName().equalsIgnoreCase(PROPERTY)) {
				beanConfig.addPropertyConfig(getProperty(property));
			}

		}
		return beanConfig;
	}
	
	private BeanConfig getAopBean(Element bean) {
		AopConfig aopConfig = new AopConfig();
		
		aopConfig.setBeanName(bean.getAttributeValue(NAME));
		Object[] propertys = bean.getChildren().toArray();
		for (Object objProperty : propertys) {
			Element property = (Element) objProperty;
			if (property.getName().equalsIgnoreCase(AOP_TARGET)) {
				aopConfig.setTarget(property.getValue());
			}
			if (property.getName().equalsIgnoreCase(AOP_INTERCEPYOT)) {
				aopConfig.setIntercepyor(property.getValue());
			}

		}
		return aopConfig;
	}

	private ConstructorConfig getConstructor(Element constructor) {
		Object[] parameters = constructor.getChildren().toArray();

		if (parameters.length > 0) {
			List<Object> parameterList = new ArrayList<Object>();
			List<Object> parameterType = new ArrayList<Object>();
			for (Object parameter : parameters) {
				Element param = (Element) parameter;
				String paramType = param.getAttributeValue(VALUE_TYPE);
				if (StringUtils.isEmpty(paramType)) {
					paramType = param.getName();
				}
				String paramValue = param.getText();
				parameterType.add(paramType);
				parameterList.add(paramValue);
			}
			return new ConstructorConfig(parameterList.toArray(), parameterType.toArray(), parameters.length);
		} else {
			return new ConstructorConfig(null, null, 0);
		}
	}

	private PropertyConfig getProperty(Element property) {
		PropertyConfig propertyConfig = new PropertyConfig();
		String propertyType = ((Element) property.getChildren().get(0)).getName();
		propertyConfig.setPropertyName(property.getAttributeValue(NAME));

		if (propertyType.equalsIgnoreCase(PROPERTY_VALUE)) {
			propertyConfig.setPropertyType(PROPERTY_VALUE);
			if (property.getChild(PROPERTY_VALUE).getChildren().size() > 0) {
				propertyConfig.setPropertyValue(null);
			} else {
				Element propertyValue = property.getChild(PROPERTY_VALUE);
				String value = propertyValue.getText();
				String valueType = propertyValue.getAttributeValue(VALUE_TYPE);
				propertyConfig.setPropertyValue(value);
				if (StringUtils.isNotEmpty(valueType)) {
					propertyConfig.setPropertyType(valueType);
				}
			}
		} else if (propertyType.equalsIgnoreCase(PROPERTY_REFLECTION)) {
			propertyConfig.setPropertyType(PROPERTY_REFLECTION);
			propertyConfig.setPropertyValue(property.getChild(PROPERTY_REFLECTION).getText());
		} else if (propertyType.equalsIgnoreCase(PROPERTY_MAP)) {
			propertyConfig.setPropertyType(PROPERTY_MAP);
			propertyConfig.setCollectionClass(property.getChild(PROPERTY_MAP).getAttributeValue(CLASS));
			propertyConfig.setCollectionItem(getMap(property.getChild(PROPERTY_MAP)));
		} else if (propertyType.equalsIgnoreCase(PROPERTY_LIST)) {
			propertyConfig.setPropertyType(PROPERTY_LIST);
			propertyConfig.setCollectionClass(property.getChild(PROPERTY_LIST).getAttributeValue(CLASS));
			propertyConfig.setCollectionItem(getList(property.getChild(PROPERTY_LIST)));
		} else if (propertyType.equalsIgnoreCase(PROPERTY_SET)) {
			propertyConfig.setPropertyType(PROPERTY_SET);
			propertyConfig.setCollectionClass(property.getChild(PROPERTY_SET).getAttributeValue(CLASS));
			propertyConfig.setCollectionItem(getSet(property.getChild(PROPERTY_SET)));
		} else if (propertyType.equalsIgnoreCase(PROPERTY_COLLECTION)) {
			propertyConfig.setPropertyType(PROPERTY_COLLECTION);
			propertyConfig.setCollectionClass(property.getChild(PROPERTY_COLLECTION).getAttributeValue(CLASS));
			propertyConfig.setCollectionItem(getCollection(property.getChild(PROPERTY_COLLECTION)));
		}

		return propertyConfig;
	}

	private Object[] getMap(Element map) {
		Object[] entities = map.getChildren().toArray();
		EntityItem[] entitySet = new EntityItem[entities.length];

		for (int i = 0; i < entities.length; i++) {
			Element entity = (Element) entities[i];
			if (entity.getChild(PROPERTY_VALUE) != null) {
				entitySet[i] = new EntityItem(entity.getChildText(PROPERTY_MAP_KEY), entity.getChildText(PROPERTY_VALUE), CollectionConstant.VALUE);
			}
			if (entity.getChild(PROPERTY_REFLECTION) != null) {
				entitySet[i] = new EntityItem(entity.getChildText(PROPERTY_MAP_KEY), entity.getChildText(PROPERTY_REFLECTION),
						CollectionConstant.REFLECTION);
			}
		}
		return entitySet;
	}

	private Object[] getList(Element list) {
		Object[] items = list.getChildren().toArray();
		ListItem[] itemSet = new ListItem[items.length];

		for (int i = 0; i < items.length; i++) {
			Element item = (Element) items[i];
			if (item.getName().equals(PROPERTY_VALUE)) {
				itemSet[i] = new ListItem(item.getValue(), CollectionConstant.VALUE);
			}
			if (item.getName().equals(PROPERTY_REFLECTION)) {
				itemSet[i] = new ListItem(item.getValue(), CollectionConstant.REFLECTION);
			}
		}
		return itemSet;

	}

	private Object[] getSet(Element set) {
		Object[] items = set.getChildren().toArray();
		SetItem[] itemSet = new SetItem[items.length];

		for (int i = 0; i < items.length; i++) {
			Element item = (Element) items[i];
			if (item.getName().equals(PROPERTY_VALUE)) {
				itemSet[i] = new SetItem(item.getValue(), CollectionConstant.VALUE);
			}
			if (item.getName().equals(PROPERTY_REFLECTION)) {
				itemSet[i] = new SetItem(item.getValue(), CollectionConstant.REFLECTION);
			}
		}
		return itemSet;

	}

	private Object[] getCollection(Element collection) {
		Object[] items = collection.getChildren().toArray();
		CollectionItem[] itemSet = new CollectionItem[items.length];

		for (int i = 0; i < items.length; i++) {
			Element item = (Element) items[i];
			if (item.getName().equals(PROPERTY_VALUE)) {
				itemSet[i] = new CollectionItem(item.getValue(), CollectionConstant.VALUE);
			}
			if (item.getName().equals(PROPERTY_REFLECTION)) {
				itemSet[i] = new CollectionItem(item.getValue(), CollectionConstant.REFLECTION);
			}
		}
		return itemSet;
	}

	/**
	 * 设置验证文件的路径
	 * 
	 * @param schemaFile 验证文件的路径
	 */
	protected void setSchemaFile(String schemaFile) {
		this.schemaFile = schemaFile;
	}
}
