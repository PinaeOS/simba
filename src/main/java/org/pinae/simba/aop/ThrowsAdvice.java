package org.pinae.simba.aop;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;

/**
 * ThrowsAdvice在指定方法抛出异常后执行
 * 
 * @author Huiyugeng
 *
 */
public interface ThrowsAdvice  extends Advice{
	/**
	 * 在指定方法抛出异常后进行回调
	 * 
	 * @param target 包含指定方法的对象 
	 * @param method 指定调用的方法
	 * @param args 指定方法的参数值
	 */
	public void afterThrowing(Object target, Method method, Object[] args);
}
