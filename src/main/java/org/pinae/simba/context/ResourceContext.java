package org.pinae.simba.context;

import java.net.URL;

import org.pinae.simba.beanfactory.BeanFactory;
import org.pinae.simba.exception.NoFoundException;

/**
 * 
 * 
 * @author Huiyugeng
 *
 */
public interface ResourceContext extends BeanFactory{
	/**
	 * 获得资源的URL地址
	 * 
	 * @return URL地址
	 * 
	 * @throws NoFoundException 查找URL失败引发的异常
	 */
	public URL getURL() throws NoFoundException;
}
