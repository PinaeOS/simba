package org.pinae.simba.aop.target;

/**
 * 所有需要进行织入（Weaving）的目标对象，即设置被通知的对象
 * 
 * @author Huiyugeng
 *
 */
public interface Target {
	/**
	 * 返回目标对象
	 * 
	 * @return 目标对象
	 */
	public Object getTarget();
	/**
	 * 设置目标对象
	 * 
	 * @param target 目标对象
	 */
	public void setTarget(Object target);
}
