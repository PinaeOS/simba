package org.pinae.simba.aop.pointcut;

import static org.junit.Assert.assertEquals;

import org.aopalliance.aop.Advice;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.pinae.simba.aop.pointcut.advice.AroundAdviceTest;
import org.pinae.simba.aop.pointcut.resource.ITarget;
import org.pinae.simba.aop.pointcut.resource.MyTarget;
import org.pinae.simba.aop.proxy.ProxyFactory;

/**
 * 通过正则表达式对切入点匹配测试
 * 
 * @author Huiyugeng
 *
 */
public class RegexPointcutAdvisorTest {
	private static Logger logger = Logger.getLogger(RegexPointcutAdvisorTest.class);
	
	@Test
	public void testRegexPointcutAdvisor(){
		logger.debug("RegexPointcutAdvisor Test");
		
		RegexPointcutAdvisor around = new RegexPointcutAdvisor();
		around.setAdvice(new AroundAdviceTest());
		around.setPattern("ayHell*");
		
		ProxyFactory proxyFactory= new ProxyFactory();
		
		proxyFactory.setTarget(new MyTarget());
		proxyFactory.setIntercepyor(new Advice[]{around});
		
		ITarget target = (ITarget)proxyFactory.getObject();
		assertEquals(target.sayHello("Hui"), "Hello Hui");
		assertEquals(target.sayHello(21), "21 years old");
	}
}
