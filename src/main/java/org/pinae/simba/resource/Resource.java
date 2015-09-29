package org.pinae.simba.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * Bean资源配置, Bean的资源配置由一个Bean池维护
 * 
 * @author Huiyugeng
 *
 */
public class Resource {
	private static Map<String, Object> beanList = new HashMap<String, Object>();
	
	/**
	 * 根据Bean的名称从Bean池中返回Bean的配置
	 * 
	 * @param beanName Bean名称
	 * 
	 * @return Bean配置
	 */
	public BeanConfig getBeanConfig(String beanName){
		return (BeanConfig)beanList.get(beanName);
	}

	/**
	 * 向Bean池中加入一个Bean
	 * 
	 * @param beanName Bean的名称
	 * @param beanConfig Bean的配置
	 */
	public void addBeanConfig(String beanName, BeanConfig beanConfig){
		beanList.put(beanName, beanConfig);
	}
	
	/**
	 * 返回Bean池中Bean的数量
	 * 
	 * @return Bean的数量
	 */
	public int getSize(){
		return beanList.size();
	}
	
	/**
	 * 返回Bean池中Bean的名称集合
	 * 
	 * @return Bean的名称集合
	 */
	public Set getBeanNameList(){
		return beanList.keySet();
	}
	
	/**
	 * 返回Bean池中的所有Bean
	 * 
	 * @return Bean池中的所有Bean
	 */
	public Map getBeanList(){
		return beanList;
	}
}
