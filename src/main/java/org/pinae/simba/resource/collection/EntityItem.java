package org.pinae.simba.resource.collection;
/**
 * Map配置
 * 
 * @author Huiyugeng
 *
 */
public class EntityItem {
	private Object key;
	private Object value;
	private int valueType;

	/**
	 * 构造函数
	 * 
	 * @param key 条目的键
	 * @param value 条目的值
	 */
	public EntityItem(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	/**
	 * 构造函数
	 * 
	 * @param key 条目的键
	 * @param value 条目的值
	 * @param valueType 条目值的类别, 0为VALUE（值）, 1为REFLECTION（映射）
	 */
	public EntityItem(Object key, Object value, int valueType) {
		super();
		this.key = key;
		this.value = value;
		this.valueType = valueType;
	}

	/**
	 * 返回条目的键
	 * 
	 * @return 条目的键
	 */
	public Object getKey() {
		return key;
	}
	
	/**
	 * 设置条目的键
	 * 
	 * @param key 条目的键
	 */
	public void setKey(Object key) {
		this.key = key;
	}
	
	/**
	 * 返回条目的值
	 * 
	 * @return 条目的值
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * 设置条目的值
	 * 
	 * @param value 条目的值
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 返回条目值的类别, 0为VALUE（值）, 1为REFLECTION（映射）
	 * 
	 * @return 条目值的类别
	 */
	public int getValueType() {
		return valueType;
	}

	/**
	 * 设置值的类别, 0为VALUE（值）, 1为REFLECTION（映射）
	 * 
	 * @param valueType 条目值的类别
	 */
	public void setValueType(int valueType) {
		this.valueType = valueType;
	}
	
}
