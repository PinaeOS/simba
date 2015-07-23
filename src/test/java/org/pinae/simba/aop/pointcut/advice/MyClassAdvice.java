package org.pinae.simba.aop.pointcut.advice;

import org.apache.log4j.Logger;
import org.pinae.simba.aop.ClassAdvice;

/**
 * ClassAdvice的例子
 * 
 * @author Huiyugeng
 *
 */
public class MyClassAdvice implements ClassAdvice {
	private static Logger logger = Logger.getLogger(MyClassAdvice.class);
	
	public Object load(Object target) {
		logger.debug("Class Target : " + target.getClass().getName());
		return target;
	}

}
