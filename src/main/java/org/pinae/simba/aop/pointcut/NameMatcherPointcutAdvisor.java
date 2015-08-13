package org.pinae.simba.aop.pointcut;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.pinae.simba.aop.Pointcut;

/**
 * 通过方法名对切入点进行匹配
 * 
 * @author Huiyugeng
 *
 */
public class NameMatcherPointcutAdvisor extends AbstractPointcutAdvisor {

	private List<String> mappedNames = new LinkedList<String>();

	/**
	 * 设置方法名队列，可以允许对象中多个方法进行匹配
	 * 
	 * @param mappedNames 方法名队列
	 */
	public void setMappedNames(List<String> mappedNames) {
		this.mappedNames = mappedNames;
	}

	/**
	 * 设置方法名，对象中只允许一个方法进行匹配
	 * 
	 * @param mappedName 方法名
	 */
	public void setMappedName(String mappedName) {
		mappedNames.add(mappedName);
	}

	public Pointcut getPointcut() {
		return new DefaultPointcut() {
			public boolean matcher(Method method, Object[] args) {
				String methodName = method.getName();
				for (String mappedName : mappedNames) {
					if (methodName.equals(mappedName)) {
						return true;
					}
				}
				return false;
			}

			public boolean matcher(Class<?> clazz) {
				String className = clazz.getName();
				for (String mappedName : mappedNames) {
					if (className.equals(mappedName)) {
						return true;
					}
				}
				return false;
			}
		};
	}

}
