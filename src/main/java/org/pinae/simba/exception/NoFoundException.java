package org.pinae.simba.exception;

/**
 * 类以及方法没有找到异常
 * @author Huiyugeng
 *
 */
public class NoFoundException extends Exception{

	private static final long serialVersionUID = 6761638594527436116L;
	
	/**
	 * 构造函数
	 */
	public NoFoundException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param message 异常提示
	 */
	public NoFoundException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 * 
	 * @param cause 异常引发原因
	 */
	public NoFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param message 异常提示
	 * @param cause 异常引发原因
	 */
	public NoFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
