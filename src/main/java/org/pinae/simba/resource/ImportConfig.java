package org.pinae.simba.resource;
/**
 * Bean导入信息配置
 * 
 * @author Huiyugeng
 *
 */
public class ImportConfig {
	private String url;
	
	/**
	 * 返回Bean信息来源URL
	 * 
	 * @return Bean信息来源URL
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置Bean信息来源URL
	 * 
	 * @param url Bean信息来源URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 将两个Bean配置信息进行合并
	 * 
	 * @param destination 目标Bean配置信息
	 * @param source 源Bean配置信息
	 * 
	 * @return 合并后的Bean配置信息
	 */
	public Resource mergeResource(Resource destination, Resource source){
		for(int i = 0 ; i<source.getSize(); i++){
			String beanName = (String)source.getBeanNameList().iterator().next();
			destination.addBeanConfig(beanName, source.getBeanConfig(beanName));
		}
		return destination;
	}
}
