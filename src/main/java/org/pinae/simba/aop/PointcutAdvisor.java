package org.pinae.simba.aop;

import org.aopalliance.aop.Advice;


/**
 * 所有由切入点(Pointcut)驱动的通知(Advisor)的接口，用于决定该通知(Advisor)在满足何种切入点(Pointcut)
 * 才被调用
 * 
 * @author Huiyugeng
 *
 */
public interface PointcutAdvisor extends Advice{
	/**
	 * 设置当切入点(Pointcut)匹配时，需要触发的通知
	 * 
	 * @param advice 通知
	 */
	public void setAdvice(Advice advice);
	/**
	 * 返回触发通知
	 * 
	 * @return 通知
	 */
	public Advice getAdvice();
	
	/**
	 * 设置驱动通知（Advice）的切入点
	 * 
	 * @param pointcut 切入点
	 */
	public void setPointcut(Pointcut pointcut);
	/**
	 * 返回可以驱动通知(Advice)的切入点
	 * 
	 * @return 切入点
	 */
	public Pointcut getPointcut();
}
