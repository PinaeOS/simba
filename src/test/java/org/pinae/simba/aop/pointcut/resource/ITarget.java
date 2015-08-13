package org.pinae.simba.aop.pointcut.resource;

/**
 * 用于测试的目标类的接口
 * 
 * @author Huiyugeng
 *
 */
public interface ITarget {

	public String sayHello(String name);
	
	public String sayHello(int age);
	
	public int getAge(int age);
}
