package org.pinae.simba.exception;

/**
 * 解析XML引发的异常
 * 
 * @author Huiyugeng
 *
 */
public class XMLParseException extends Exception {

	private static final long serialVersionUID = -2271115202598451664L;
	
	/**
	 * 构造函数
	 */
	public XMLParseException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param message 异常提示
	 */
	public XMLParseException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 * 
	 * @param cause 异常引发原因
	 */
	public XMLParseException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param message 异常提示
	 * @param cause 异常引发原因
	 */
	public XMLParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
