package org.pinae.simba.aop.pointcut;

import org.aopalliance.aop.Advice;
import org.pinae.simba.aop.PointcutAdvisor;

/**
 * 返回真值，永远匹配的切入点
 * 
 * @author Huiyugeng
 *
 */
public class TruePointcutAdvisor extends AbstractPointcutAdvisor {
	private TruePointcutAdvisor() {

	}

	/**
	 * 建立返回真值的切入点驱动的通知（PointcutAdvisor）
	 * 
	 * @param intercepyor 通知
	 * @return 切入点驱动的通知
	 */
	public static PointcutAdvisor buildPointAdvisor(Advice intercepyor) {
		TruePointcutAdvisor pointAdvisor = new TruePointcutAdvisor();
		pointAdvisor.setAdvice(intercepyor);
		pointAdvisor.setPointcut(new DefaultPointcut(true, true));
		return pointAdvisor;
	}
}
