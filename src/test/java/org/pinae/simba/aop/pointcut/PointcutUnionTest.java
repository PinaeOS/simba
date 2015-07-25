package org.pinae.simba.aop.pointcut;

import static org.junit.Assert.assertEquals;

import org.aopalliance.aop.Advice;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.pinae.simba.aop.Pointcut;
import org.pinae.simba.aop.pointcut.advice.AroundAdviceTest;
import org.pinae.simba.aop.pointcut.resource.ITarget;
import org.pinae.simba.aop.pointcut.resource.MyTarget;
import org.pinae.simba.aop.proxy.ProxyFactory;
import org.pinae.simba.aop.target.DefaultTarget;
import org.pinae.simba.aop.target.Target;

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
		advisor2.setClass("java.lang.String");
		Pointcut point2 = advisor2.getPointcut();
		
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setAdvice(new AroundAdviceTest());
		advisor.setPointcut(PointcutUtil.union(point1, point2));
		
		ProxyFactory proxyFactory= new ProxyFactory();
		Target myTarget = new DefaultTarget();
		myTarget.setTarget(new MyTarget());
		proxyFactory.setTarget(myTarget);
		
		proxyFactory.setIntercepyor(new Advice[]{advisor});
		
		ITarget target = (ITarget)proxyFactory.getObject();
		assertEquals(target.sayHello("Hui"), "Hello Hui");
	}
}
