package org.pinae.simba.context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.pinae.simba.beanfactory.xml.XmlBeanFactory;
import org.pinae.simba.exception.InvokeException;
import org.pinae.simba.exception.NoFoundException;
import org.pinae.simba.exception.XMLParseException;

/**
 * 从XML文件获得Bean资源
 * 
 * @author Huiyugeng
 *
 */
public class FileSystemResourceContext extends XmlBeanFactory implements 
		ResourceContext {
	
	private String filename = null;
	private InputStream xmlStream = null;
	
	/**
	 * 构造函数
	 * 
	 * @param filename XML文件名
	 * 
	 * @throws NoFoundException 文件未找到异常处理
	 * @throws XMLParseException XML解析异常处理
	 */
	
	public FileSystemResourceContext(String filename) throws NoFoundException, XMLParseException{
		this(filename, true);
	}
	
	/**
	 * 构造函数
	 * 
	 * @param filename XML文件名
	 * @param validateXml 验证XML
	 * 
	 * @throws NoFoundException 文件未找到异常处理
	 * @throws XMLParseException XML解析异常处理
	 */
	public FileSystemResourceContext(String filename, boolean validateXml) throws NoFoundException, XMLParseException{
		this.filename = filename;
		
		try {
			this.xmlStream = new FileInputStream(this.filename);
		} catch (FileNotFoundException e) {
			throw new NoFoundException(e);
		}
		
		super.setResourcePath(this.xmlStream, validateXml);
	}   

	
	/**
	 * 构造函数
	 * 
	 * @param filename XML文件名
	 * @param schemaFile 验证文件
	 * 
	 * @throws NoFoundException 文件未找到异常处理
	 * @throws XMLParseException XML解析异常处理
	 */
	public FileSystemResourceContext(String filename, String schemaFile) throws NoFoundException, XMLParseException{
		this.filename = filename;
		try {
			this.xmlStream = new FileInputStream(this.filename);
		} catch (FileNotFoundException e) {
			throw new NoFoundException(e);
		}
		super.setSchemaFile(schemaFile);
		super.setResourcePath(this.xmlStream, true);
	}
	
	public Object getBean(String beanname) throws InvokeException, NoFoundException {
		return super.getBean(beanname);
	}

	public URL getURL() throws NoFoundException {
		try {
			return new URL(this.filename);
		} catch (MalformedURLException e) {
			throw new NoFoundException(e);
		}
	}
}
