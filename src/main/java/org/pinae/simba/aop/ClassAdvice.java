package org.pinae.simba.aop;

import org.aopalliance.aop.Advice;

/**
 * ClassAdvice在指定类装入前执行
 * 
 * @author Huiyugeng
 *
 */
public interface ClassAdvice  extends Advice{
	/**
	 * 在对象装入前执行的方法
	 * 
	 * @param target 指定装入的对象
	 * @return 返回被包装后的对象
	 */
	public Object load(Object target);
}
