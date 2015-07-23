package org.pinae.simba.aop.pointcut;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.pinae.simba.aop.Pointcut;
import org.pinae.simba.aop.PointcutAdvisor;


/**
 * 所有切入点驱动通知（PointcutAdvisor）的抽象实现
 * 
 * @author Huiyugeng
 *
 */
public abstract class AbstractPointcutAdvisor implements PointcutAdvisor {
	
	private Advice advice;
	private Pointcut pointcut;
	
	public Advice getAdvice() {
		return advice;
	}

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}
	
	/**
	 * 判断方法是否满足做为切入点的条件
	 * 
	 * @param method 需要切入的方法
	 * @param args 方法的参数
	 * @return 是否满足做为切入点
	 */
	public boolean matcher(Method method, Object[] args) {
		return true;
	}
	
	/**
	 * 判断类是否满足做为切入点的条件
	 * 
	 * @param clazz 需要切入的类
	 * @return 是否满足做为切入点
	 */
	public boolean matcher(Class clazz){
		return true;
	}
	
	public Pointcut getPointcut() {
		return pointcut;
	}

	public void setPointcut(Pointcut pointcut) {
		this.pointcut = pointcut;
	}

}
