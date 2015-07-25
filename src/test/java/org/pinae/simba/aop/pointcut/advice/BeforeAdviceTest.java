package org.pinae.simba.aop.pointcut.advice;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.pinae.simba.aop.BeforeAdvice;


/**
 * BeforeAdvice的例子
 * 
 * @author 惠毓赓
 *
 */
public class BeforeAdviceTest implements BeforeAdvice {
	private static Logger logger = Logger.getLogger(BeforeAdviceTest.class);

	public void before(Object target, Method method, Object[] args) {
		String argType = "";
		if(args.length>=0){
			argType = args[0].getClass().getName();
		}
		logger.debug("After the method : " + method.getName() + "; args type : " + argType);
	}

}
