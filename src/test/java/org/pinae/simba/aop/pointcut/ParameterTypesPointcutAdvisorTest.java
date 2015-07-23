package org.pinae.simba.aop.pointcut;

import static org.junit.Assert.assertEquals;

import org.aopalliance.aop.Advice;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.pinae.simba.aop.pointcut.ParameterTypesPointcutAdvisor;
import org.pinae.simba.aop.pointcut.advice.MyAfterAdvice;
import org.pinae.simba.aop.pointcut.resource.ITarget;
import org.pinae.simba.aop.pointcut.resource.MyTarget;
import org.pinae.simba.aop.proxy.ProxyFactory;
import org.pinae.simba.aop.target.DefaultTarget;
import org.pinae.simba.aop.target.Target;

/**
 * 调用方法的参数类型对切入点匹配测试
 * 
 * @author Huiyugeng
 *
 */
public class ParameterTypesPointcutAdvisorTest {
	private static Logger logger = Logger.getLogger(ParameterTypesPointcutAdvisorTest.class);
	
	@Test
	public void testParameterTypesPointcutAdvisor(){
		logger.debug("ParameterTypesPointcutAdvisor Test");
		
		//只要执行的方法参数为String则进行切入
		ParameterTypesPointcutAdvisor after= new ParameterTypesPointcutAdvisor();
		after.setAdvice(new MyAfterAdvice());
		after.setClass("java.lang.String");
		
		ProxyFactory proxyFactory= new ProxyFactory();
		Target myTarget = new DefaultTarget();
		myTarget.setTarget(new MyTarget());
		proxyFactory.setTarget(myTarget);
		
		proxyFactory.setIntercepyor(new Advice[]{after});
		
		ITarget target = (ITarget)proxyFactory.getObject();
		assertEquals(target.sayHello("Hui"), "Hello Hui");
		assertEquals(target.sayHello(21), "21 years old");
	}
}
