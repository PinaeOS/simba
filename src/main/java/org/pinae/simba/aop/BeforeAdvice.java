package org.pinae.simba.aop;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;

/**
 * 
 * BeforeAdvicee在指定执行方法前执行 
 * 
 * @author Huiyugeng
 *
 */
public interface BeforeAdvice  extends Advice{
	/**
	 * Callback before a given method is invoked. 
	 * 
	 * @param target 包含指定方法的对象 
	 * @param method 指定调用的方法
	 * @param args 指定方法的参数值
	 */
	public void before(Object target, Method method, Object[] args);
}
