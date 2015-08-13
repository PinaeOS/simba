package org.pinae.simba.beanfactory.xml;

import java.io.InputStream;

import org.pinae.simba.beanfactory.BasicBeanFactory;
import org.pinae.simba.exception.InvokeException;
import org.pinae.simba.exception.NoFoundException;
import org.pinae.simba.exception.XmlParseException;
import org.pinae.simba.resource.Resource;

/**
 * 通过XML生成Bean的配置信息
 * 
 * @author Huiyugeng
 *
 */
public class XmlBeanFactory extends BasicBeanFactory {

	private static Resource beanConfig = null;
	private InputStream xmlFile = null;

	/**
	 * 设置验证文件的路径
	 * 
	 * @param schemaFile 验证文件的路径
	 */
	protected static void setSchemaFile(String schemaFile) {
		XmlResourceParser.setSchemaFile(schemaFile);
	}

	/**
	 * 构造函数
	 */
	public XmlBeanFactory() {

	}

	/**
	 * 构造函数
	 * 
	 * @param xmlStream XML文件流
	 * @param validateXml 验证XML
	 * 
	 * @throws XmlParseException XML解析引发的异常
	 */
	public XmlBeanFactory(InputStream xmlStream, boolean validateXml) throws XmlParseException {
		setResourcePath(xmlStream, validateXml);
	}

	/**
	 * 从XML文件流获得Bean的配置信息并进行配置
	 * 
	 * @param xmlStream XML文件流
	 * @param validateXml 验证XML
	 * 
	 * @throws XmlParseException XML解析引发的异常
	 */
	public void setResourcePath(InputStream xmlStream, boolean validateXml) throws XmlParseException {
		this.xmlFile = xmlStream;
		beanConfig = XmlResourceParser.getConfig(this.xmlFile, validateXml);
		super.setConfig(beanConfig);
	}

	/**
	 * 获得Bean实例
	 * 
	 * @param beanname Bean名称
	 * @return Object 返回对象
	 * @throws NoFoundException 调用方法错误
	 * @throws InvokeException 没有找到类或者方法引发的异常
	 */
	public Object getBean(String beanname) throws InvokeException, NoFoundException {
		return super.getBean(beanname);
	}
}
