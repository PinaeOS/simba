package org.pinae.simba.aop;

import java.lang.reflect.Method;

/**
 * 判断方法或者类是否满足做为切入点的条件
 * 
 * @author Huiyugeng
 *
 */
public interface Pointcut {
	
	/**
	 * 判断指定的方法是否满足做为切入点的条件
	 * 
	 * @param method 需要切入的方法
	 * @param arg 方法的参数
	 * @return 是否满足做为切入点
	 */
	public boolean matcher(Method method, Object arg[]);
	
	/**
	 * 判断装入的类是否满足做为切入点的条件
	 * 
	 * @param clazz 需要切入的类
	 * @return 是否满足做为切入点
	 */
	@SuppressWarnings("rawtypes")
	public boolean matcher(Class clazz);
}
