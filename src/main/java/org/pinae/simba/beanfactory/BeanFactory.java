package org.pinae.simba.beanfactory;

import org.pinae.simba.exception.InvokeException;
import org.pinae.simba.exception.NoFoundException;
import org.pinae.simba.resource.Resource;

/**
 * 根据配置信息生成对应的Bean
 * 
 * @author Huiyugeng
 *
 */
public interface BeanFactory {
	
	/**
	 * @param beanname Bean名称
	 * @return Object 返回对象
	 * @throws InvokeException 调用方法错误
	 * @throws NoFoundException 没有找到类或者方法引发的异常
	 */
	public Object getBean(String beanname) throws InvokeException, NoFoundException;
	
	/** 
	 * @return 返回加载资源的信息
	 */
	public Resource getConfig();
}
