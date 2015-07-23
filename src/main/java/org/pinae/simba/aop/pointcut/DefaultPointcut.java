package org.pinae.simba.aop.pointcut;

import java.lang.reflect.Method;

import org.pinae.simba.aop.Pointcut;


/**
 * 切入点的默认实现
 * 
 * @author Huiyugeng
 *
 */
public class DefaultPointcut implements Pointcut {
	private boolean methodMatcher = false;
	private boolean classMatcher = false;
	
	/**
	 * 构造函数
	 */
	public DefaultPointcut(){
		
	}
	
	/**
	 * 构造函数
	 * 
	 * @param methodMatcher 方法是否满足做为切入点的条件
	 * @param classMatcher 类是否满足做为切入点的条件
	 */
	public DefaultPointcut(boolean methodMatcher, boolean classMatcher) {
		super();
		this.methodMatcher = methodMatcher;
		this.classMatcher = classMatcher;
	}

	public boolean matcher(Method method, Object[] arg) {
		return methodMatcher;
	}

	public boolean matcher(Class clazz) {
		return classMatcher;
	}
	
	protected void setMethodMatcher(boolean methodMatcher){
		this.methodMatcher = methodMatcher;
	}
	
	protected void setClassMatcher(boolean classMatcher){
		this.classMatcher = classMatcher;
	}

}
