package org.pinae.simba.aop.intercept;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.aop.AspectException;
import org.aopalliance.intercept.MethodInvocation;

/**
 * MethodInvocation接口的默认实现
 * 
 * @author Huiyugeng
 *
 */
public class DefaultMethodInvocation implements MethodInvocation {
	private Object target;
	private Method method;
	private Object[] args;

	/**
	 * 构造函数
	 * 
	 * @param target 包含指定方法的对象
	 * @param method 指定调用的方法
	 * @param args 指定方法的参数值
	 */
	public DefaultMethodInvocation(Object target, Method method, Object[] args) {
		this.target = target;
		this.method = method;
		this.args = args;
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getArguments() {
		return args;
	}

	public AccessibleObject getStaticPart() {
		return null;
	}

	public Object getThis() {
		return target;
	}

	public Object proceed() {
		Object result = null;
		try {
			result = method.invoke(target, args);
		} catch (IllegalArgumentException e) {
			throw new AspectException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new AspectException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new AspectException(e.getMessage(), e);
		}
		return result;
	}
}
