package org.pinae.simba.resource;
/**
 * 
 * Bean的构造函数配置
 * 
 * @author Huiyugeng
 *
 */
public class ConstructorConfig {
	private Object[] parameter;
	private Object[] parameterType;
	private int size = 0;
	
	/**
	 * 构造函数
	 * 
	 * @param parameter 构造函数中的参数
	 * @param parameter_type 构造函数的参数类型
	 * @param size 构造函数的参数个数
	 */
	public ConstructorConfig(Object[] parameter, Object[] parameterType, int size) {
		super();
		this.parameter = parameter;
		this.parameterType = parameterType;
		this.size = size;
	}
	
	/**
	 * 返回构造函数的参数
	 * 
	 * @return 构造函数的参数
	 */
	public Object[] getParameter() {
		return parameter;
	}
	/**
	 * 设置构造函数的参数
	 * 
	 * @param parameter
	 */
	public void setParameter(Object[] parameter) {
		this.parameter = parameter;
	}
	/**
	 * 返回构造函数的参数个数
	 * 
	 * @return 构造函数的参数个数
	 */
	public int getSize() {
		return size;
	}
	/**
	 * 设置构造函数的参数个数
	 * 
	 * @param size 构造函数的参数个数
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * 返回构造函数的参数类型
	 * @return 构造函数的参数类型 
	 */
	public Object[] getParameterType() {
		return parameterType;
	}
	/**
	 * 设置构造函数的参数类型 
	 * @param parameter_type 构造函数的参数类型 
	 */
	public void setParameterType(Object[] parameterType) {
		this.parameterType = parameterType;
	}
	
	
}
