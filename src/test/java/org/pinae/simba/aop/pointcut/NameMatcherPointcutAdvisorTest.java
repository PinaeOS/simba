package org.pinae.simba.aop.pointcut;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.pinae.simba.aop.pointcut.advice.AroundAdviceTest;
import org.pinae.simba.aop.pointcut.resource.ITarget;
import org.pinae.simba.aop.pointcut.resource.MyTarget;
import org.pinae.simba.aop.proxy.ProxyFactory;

/**
 * 通过方法名对切入点匹配测试
 * 
 * @author Huiyugeng
 *
 */
public class NameMatcherPointcutAdvisorTest {
	private static Logger logger = Logger.getLogger(NameMatcherPointcutAdvisorTest.class);
	
	@Test
	public void testNameMatcherPointcutAdvisor(){
		logger.debug("NameMatcherPointcutAdvisor Test");
		
		NameMatcherPointcutAdvisor around = new NameMatcherPointcutAdvisor();
		around.setAdvice(new AroundAdviceTest());
		around.setMappedName("sayHello");
		
		ProxyFactory proxyFactory= new ProxyFactory();
		
		proxyFactory.setTarget(new MyTarget());
		proxyFactory.setIntercepyor(around);
		
		ITarget target = (ITarget)proxyFactory.getObject();
		assertEquals(target.sayHello("Hui"), "Hello Hui");
		assertEquals(target.sayHello(21), "21 years old");
	}
}
