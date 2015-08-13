package org.pinae.simba.aop.pointcut.resource;

/**
 * 用于测试的目标类
 * 
 * @author Huiyugeng
 *
 */
public class MyTarget implements ITarget{

	public String sayHello(String name){
		return "Hello " + name;
	}

	public String sayHello(int age) {
		return age + " years old";
	}
	
	public int getAge(int age) {
		return age - 5;
	}
}
