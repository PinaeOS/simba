package org.pinae.simba.aop.pointcut;

import static org.junit.Assert.assertEquals;

import org.aopalliance.aop.Advice;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.pinae.simba.aop.Pointcut;
import org.pinae.simba.aop.PointcutAdvisor;
import org.pinae.simba.aop.pointcut.advice.AroundAdviceTest;
import org.pinae.simba.aop.pointcut.resource.ITarget;
import org.pinae.simba.aop.pointcut.resource.MyTarget;
import org.pinae.simba.aop.proxy.ProxyFactory;

/**
 * 测试Pointcut合并
 * 
 * @author Huiyugeng
 *
 */
public class PointcutUnionTest {
	private static Logger logger = Logger.getLogger(PointcutUnionTest.class);
	
	@Test
	public void testPointcutUnion(){
		logger.debug("PointcutUnion Test");
		
		NameMatcherPointcutAdvisor advisor1 = new NameMatcherPointcutAdvisor();
		advisor1.setMappedName("sayHello");
		Pointcut point1 = advisor1.getPointcut();
	
		ParameterTypesPointcutAdvisor advisor2 = new ParameterTypesPointcutAdvisor();
		advisor2.setClass(String.class);
		Pointcut point2 = advisor2.getPointcut();
		
		Pointcut unionPointcut = PointcutUtil.union(point1, point2);
		
		PointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setAdvice(new AroundAdviceTest());
		advisor.setPointcut(unionPointcut);
		
		ProxyFactory proxyFactory= new ProxyFactory();

		proxyFactory.setTarget(new MyTarget());
		proxyFactory.setIntercepyor(new Advice[]{advisor});
		
		ITarget target = (ITarget)proxyFactory.getObject();
		assertEquals(target.sayHello("Hui"), "Hello Hui");
	}
}
