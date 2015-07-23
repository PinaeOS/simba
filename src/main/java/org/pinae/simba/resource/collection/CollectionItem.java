package org.pinae.simba.resource.collection;

/**
 * Collection配置
 * 
 * @author Huiyugeng
 *
 */
public class CollectionItem {
	private Object item;
	private int itemType;

	/**
	 * 返回条目的类别, 0为VALUE（值）, 1为REFLECTION（映射）
	 * 
	 * @return 条目的类别
	 */
	public int getItemType() {
		return itemType;
	}

	/**
	 * 设置条目的类别, 0为VALUE（值）, 1为REFLECTION（映射）
	 * 
	 * @param itemType 条目的类别
	 */
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param item Collection中的条目
	 */
	public CollectionItem(Object item) {
		super();
		this.item = item;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param item Collection中的条目
	 * @param itemType 条目的类别, 0为VALUE（值）, 1为REFLECTION（映射）
	 */
	public CollectionItem(Object item, int itemType) {
		super();
		this.item = item;
		this.itemType = itemType;
	}

	/**
	 * 返回Collection中的条目
	 * 
	 * @return Collection中的条目
	 */
	public Object getItem() {
		return item;
	}

	/**
	 * 设置Collection中的条目
	 * 
	 * @param item Collection中的条目
	 */
	public void setItem(Object item) {
		this.item = item;
	}
}
