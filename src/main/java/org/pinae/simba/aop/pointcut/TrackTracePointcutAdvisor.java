package org.pinae.simba.aop.pointcut;

import java.lang.reflect.Method;

import org.pinae.simba.aop.Pointcut;


/**
 * 根据方法运行的调用栈中是否包含特定的类中的方法来对切入点进行匹配
 * 
 * @author Huiyugeng
 *
 */
public class TrackTracePointcutAdvisor extends AbstractPointcutAdvisor {
	private String className;
	private String methodName;

	/**
	 * 设置调用栈中指定的类
	 * 
	 * @param className 类名称
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	/**
	 * 设置调用栈中指定的类的方法
	 * 
	 * @param methodName 方法名称
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Pointcut getPointcut() {

		return new DefaultPointcut() {
			public boolean matcher(Method method, Object[] arg) {
				if(className!=null){
					if(methodName!=null && !methodName.equals("")){
						return isMatchStackTrace(className, methodName);
					}else{
						return isMatchStackTrace(className, null);
					}
				}else{
					return false;
				}
			}

			public boolean matcher(Class clazz) {
				if(className!=null){
					return isMatchStackTrace(className, null);
				}else{
					return false;
				}
				
			}
			
			private boolean isMatchStackTrace(String className,String methodName){
				StackTraceElement stack[] = (new Throwable()).getStackTrace();
				for (int i = 0; i < stack.length; i++) {
					StackTraceElement ste = stack[i];
					if(ste.getClassName().equals(className)){
						if(methodName!=null){
							if(ste.getMethodName().equals(methodName)){
								return true;
							}else{
								return false;
							}
						}
						return true;
					}
				}
				return false;
			}
		};
	}
}
