package org.pinae.simba.aop.pointcut;

import static org.junit.Assert.assertEquals;

import org.aopalliance.aop.Advice;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.pinae.simba.aop.pointcut.TrackTracePointcutAdvisor;
import org.pinae.simba.aop.pointcut.advice.MyBeforeAdvice;
import org.pinae.simba.aop.pointcut.resource.ITarget;
import org.pinae.simba.aop.pointcut.resource.MyTarget;
import org.pinae.simba.aop.proxy.ProxyFactory;
import org.pinae.simba.aop.target.DefaultTarget;
import org.pinae.simba.aop.target.Target;

/**
 * 根据方法运行的调用栈中包含特定的类中的方法来对切入点匹配测试
 * 
 * @author Huiyugeng
 *
 */
public class TrackTracePointcutAdvisorTest {
	private static Logger logger = Logger.getLogger(TrackTracePointcutAdvisorTest.class);
	
	@Test
	public void testTrackTracePointcutAdvisor(){
		logger.debug("TrackTracePointcutAdvisor Test");
		
		TrackTracePointcutAdvisor before = new TrackTracePointcutAdvisor();
		before.setAdvice(new MyBeforeAdvice());
		before.setClassName("org.pinae.zazu.aop.proxy.DefaultProxyFactory");
		
		ProxyFactory proxyFactory= new ProxyFactory();
		Target myTarget = new DefaultTarget();
		myTarget.setTarget(new MyTarget());
		proxyFactory.setTarget(myTarget);
		
		proxyFactory.setIntercepyor(new Advice[]{before});
		
		ITarget target = (ITarget)proxyFactory.getObject();
		assertEquals(target.sayHello("Hui"), "Hello Hui");
		assertEquals(target.sayHello(21), "21 years old");
	}
}
