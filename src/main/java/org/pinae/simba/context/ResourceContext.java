package org.pinae.simba.context;

import java.net.URL;

import org.pinae.simba.exception.NoFoundException;
import org.pinae.simba.factory.BeanFactory;

/**
 * 
 * Bean资源管理
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
