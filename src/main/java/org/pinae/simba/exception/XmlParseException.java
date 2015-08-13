package org.pinae.simba.exception;

/**
 * 解析XML引发的异常
 * 
 * @author Huiyugeng
 *
 */
public class XmlParseException extends Exception {

	private static final long serialVersionUID = -2271115202598451664L;
	
	/**
	 * 构造函数
	 */
	public XmlParseException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param message 异常提示
	 */
	public XmlParseException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 * 
	 * @param cause 异常引发原因
	 */
	public XmlParseException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param message 异常提示
	 * @param cause 异常引发原因
	 */
	public XmlParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
