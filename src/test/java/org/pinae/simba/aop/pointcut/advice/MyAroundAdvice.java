package org.pinae.simba.aop.pointcut.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

/**
 * AroundAdvice的例子
 * 
 * @author Huiyugeng
 *
 */
public class MyAroundAdvice implements MethodInterceptor{
	private static Logger logger = Logger.getLogger(MyAroundAdvice.class);

	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		Object args[] = invocation.getArguments();
		
		String argType = "";
		if(args.length>=0){
			argType = args[0].getClass().getName();
		}
		
		logger.debug("Around before the method :" + invocation.getMethod().getName() + "; args type : " + argType);
		Object object = invocation.proceed();
		logger.debug("Around after the method :" + invocation.getMethod().getName() + "; args type : " + argType);
		return object;
	}

}
