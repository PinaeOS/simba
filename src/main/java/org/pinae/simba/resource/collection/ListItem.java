package org.pinae.simba.resource.collection;
/**
 * List配置
 * 
 * @author Huiyugeng
 *
 */
public class ListItem {
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
	 * @param item List中的条目
	 */
	public ListItem(Object item) {
		super();
		this.item = item;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param item List中的条目
	 * @param itemType 条目的类别, 0为VALUE（值）, 1为REFLECTION（映射）
	 */
	public ListItem(Object item, int itemType) {
		super();
		this.item = item;
		this.itemType = itemType;
	}

	/**
	 * 返回List中的条目
	 * 
	 * @return List中的条目
	 */
	public Object getItem() {
		return item;
	}

	/**
	 * 设置List中的条目
	 * 
	 * @param item List中的条目
	 */
	public void setItem(Object item) {
		this.item = item;
	}
}
