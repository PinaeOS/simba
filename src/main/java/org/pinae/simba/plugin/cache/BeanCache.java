package org.pinae.simba.plugin.cache;

import java.util.HashMap;
import java.util.Map;
/**
 * Bean缓存池
 * 
 * @author Huiyugeng
 *
 */
public class BeanCache {
	private static Map<String, CacheBean> beanCache = new HashMap<String, CacheBean>();

	/**
	 * 指定的Bean是否已经被缓存
	 * 
	 * @param beanName Bean名称
	 * 
	 * @return 是否已经被存入缓存池中
	 */
	public static boolean isCache(String beanName){
		if(beanCache.get(beanName)!=null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 从缓冲取出Bean实例
	 * 
	 * @param beanName Bean的名称
	 * 
	 * @return Bean的实例
	 */
	public synchronized static CacheBean getBean(String beanName){
		return beanCache.get(beanName);
	}
	
	/**
	 * 从缓冲移除Bean实例
	 * 
	 * @param beanName Bean的名称
	 * 
	 * @return Bean的实例
	 */
	public synchronized static CacheBean removeBean(String beanName){
		return beanCache.remove(beanName);
	}
	
	/**
	 * 从缓冲移除所有Bean实例
	 */
	public synchronized static void clear(){
		beanCache.clear();
	}
	
	/**
	 * 向缓存中增加Bean实例
	 * 
	 * @param beanName Bean的名称
	 * @param beanObject Bean的实例
	 */
	public synchronized static void addBean(String beanName, Object beanObject){
		beanCache.put(beanName, new CacheBean(beanObject));
	}
	
	/**
	 * 缓冲对象
	 *
	 */
	public static class CacheBean{
		/**
		 * 缓冲时间戳
		 */
		private long timestamp;
		/**
		 * 缓冲的对象
		 */
		private Object bean;
		
		public CacheBean(Object bean) {
			this.bean = bean;
			this.timestamp = System.currentTimeMillis();
		}

		public long getTimestamp() {
			return timestamp;
		}

		public Object getBean() {
			return bean;
		}
	}
}
