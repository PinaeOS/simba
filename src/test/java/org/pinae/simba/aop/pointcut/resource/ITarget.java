package org.pinae.simba.aop.pointcut.resource;

/**
 * 用于测试的目标类的接口
 * 
 * @author Huiyugeng
 *
 */
public interface ITarget {
	/**
	 * 说Hello
	 * 
	 * @param name 姓名
	 * @return 问候
	 */
	public String sayHello(String name);
	
	public String sayHello(int age);
}
