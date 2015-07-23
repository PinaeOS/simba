package org.pinae.simba.resource;
/**
 * Bean中属性配置, 通过Bean的set方法进行注入
 * 
 * @author Huiyugeng
 *
 */
public class PropertyConfig {
	private String propertyName; //属性名称
	private String propertyType; //属性类型
	private String propertyValue; //属性值
	private String collectionClass; //Collection类
	private Object[] collectionItem; //Collection条目
	
	/**
	 * 返回属性的名称
	 * 
	 * @return 属性的名称
	 */
	public String getPropertyName(){
		return propertyName;
	}
	
	/**
	 * 返回属性的类
	 * 
	 * @return 属性的类
	 */
	public String getPropertyType(){
		return propertyType;
	}
	
	/**
	 * 返回属性的值
	 * 
	 * @return 属性的值
	 */
	public String getPropertyValue(){
		return propertyValue;
	}
	
	/**
	 * 设置属性的名称
	 * 
	 * @param propertyName 属性的名称
	 */
	public void setPropertyName(String propertyName){
		this.propertyName = propertyName;
	}
	
	/**
	 * 设置属性的类
	 * 
	 * @param propertyType 属性的类
	 */
	public void setPropertyType(String propertyType){
		this.propertyType = propertyType;
	}
	
	/**
	 * 设置属性的值
	 * 
	 * @param propertyValue 属性的值
	 */
	public void setPropertyValue(String propertyValue){
		this.propertyValue = propertyValue;
	}

	/**
	 * 当属性为Collection时, 返回Collection的类
	 * 
	 * @return 属性的Collection类
	 */
	public String getCollectionClass() {
		return collectionClass;
	}

	/**
	 * 当属性为Collection时, 设置Collection的类
	 * 
	 * @param collectionClass 属性的Collection类
	 */
	public void setCollectionClass(String collectionClass) {
		this.collectionClass = collectionClass;
	}

	/**
	 * 当属性为Collection时, 返回Collection中的条目
	 * 
	 * @return Collection中的条目
	 */
	public Object[] getCollectionItem() {
		return collectionItem;
	}

	/**
	 * 当属性为Collection时, 设置Collection中的条目
	 * 
	 * @param collectionItem Collection中的条目
	 */
	public void setCollectionItem(Object[] collectionItem) {
		this.collectionItem = collectionItem;
	}
}
