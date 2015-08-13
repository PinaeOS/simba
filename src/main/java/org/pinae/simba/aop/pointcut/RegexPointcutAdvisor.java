package org.pinae.simba.aop.pointcut;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pinae.simba.aop.Pointcut;


/**
 * 通过正则表达式对切入点进行匹配
 * 
 * @author Huiyugeng
 *
 */
public class RegexPointcutAdvisor extends AbstractPointcutAdvisor {
	private String pattern;

	/**
	 * 返回匹配方法或者类用的正则表达式
	 * 
	 * @return 正则表达式
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * 设置匹配方法或者类用的正则表达式
	 * 
	 * @param pattern 正则表达式
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public Pointcut getPointcut() {
		return new DefaultPointcut(){
			public boolean matcher(Method method, Object[] args) {
				String methodName = method.getName();
				Matcher matcher = Pattern.compile(pattern).matcher(methodName);
				if (matcher.find()){
					return true;
				}else{
					return false;
				}
				
			}
			
			public boolean matcher(Class<?> clazz) {
				String className = clazz.getName();
				Matcher matcher = Pattern.compile(pattern).matcher(className);
				if (matcher.find()){
					return true;
				}else{
					return false;
				}
			}
		};
	}
}
