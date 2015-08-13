package org.pinae.simba.aop.pointcut.advice;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.pinae.simba.aop.AfterAdvice;

/**
 * AfterAdvice例子
 * 
 * @author Huiyugeng
 *
 */
public class AfterAdviceTest implements AfterAdvice {
	private static Logger logger = Logger.getLogger(AfterAdviceTest.class);

	public void after(Object target, Method method, Object[] args) {
		String argType = "";
		if (args.length >= 0) {
			argType = args[0].getClass().getName();
		}
		logger.debug("After the method : " + method.getName() + "; args type : " + argType);
		assertEquals(method.getName(), "sayHello");
	}

}
