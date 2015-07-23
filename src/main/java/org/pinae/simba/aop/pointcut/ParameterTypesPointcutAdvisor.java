package org.pinae.simba.aop.pointcut;

import java.lang.reflect.Method;

import org.pinae.simba.aop.Pointcut;


/**
 * 通过调用方法的参数类型对切入点进行匹配
 * 
 * @author Huiyugeng
 *
 */
public class ParameterTypesPointcutAdvisor extends AbstractPointcutAdvisor {
	private String className;

	/**
	 * 设置参数类型
	 * 
	 * @param className 参数类型
	 */
	public void setClass(String className) {
		this.className = className;
	}
	
	public Pointcut getPointcut() {
		return new DefaultPointcut(){
			public boolean matcher(Method method, Object[] args) {
				if(className!=null && !className.equals("")){
					for(int i=0;i<args.length;i++){
						if(args[i].getClass().getName().equals(className)){
							return true;
						}
					}
				}
				return false;	
			}
			
			public boolean matcher(Class clazz) {
				throw new UnsupportedOperationException("Illegal Class Filter usage");
			}
		};
	}
}
