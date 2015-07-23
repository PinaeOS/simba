package org.pinae.simba.exception;

/**	
* 方法调用异常
 * @author Huiyugeng
 *
 */
public class InvokeException extends Exception {
	private static final long serialVersionUID = 1753747998058774540L;
	
	/**
	 * 构造函数
	 */
	public InvokeException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param message 异常提示
	 */
	public InvokeException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 * 
	 * @param cause 异常引发原因
	 */
	public InvokeException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param message 异常提示
	 * @param cause 异常引发原因
	 */
	public InvokeException(String message, Throwable cause) {
		super(message, cause);
	}
}
