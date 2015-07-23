package org.pinae.simba.aop.proxy;

import org.aopalliance.aop.Advice;
import org.apache.log4j.Logger;
import org.pinae.simba.aop.target.Target;


/**
 * 代理对象工厂，用于按照切入点规则来将通知织入到目标对象上
 * 
 * @author Huiyugeng
 *
 */
public class ProxyFactory{
	protected final Logger log = Logger.getLogger("ProxyFactoryBean");
	
	private Target target;
	private Advice[] intercepyors;
	/**
	 * 构造函数
	 */
	public ProxyFactory(){
		
	}
	
	/**
	 * 返回在目标对象上按照切入点匹配规则织入通知后对象
	 * 
	 * @return 织入通知后的对象
	 */
	public Object getObject(){
		DefaultProxyFactory proxyBean = new DefaultProxyFactory(target, intercepyors);
		return proxyBean.getObject();
	}

	/**
	 * 设置由切入点驱动的通知
	 * 
	 * @param intercepyors 切入点驱动的通知
	 */
	public void setIntercepyor(Advice[] intercepyors) {
		this.intercepyors = new Advice[intercepyors.length];
		for(int i=0; i<intercepyors.length; i++){
			this.intercepyors[i] = intercepyors[i];
		}
	}
	
	/**
	 * 设置目标对象
	 * 
	 * @param target 目标兑现
	 */
	public void setTarget(Target target) {
		this.target = target;
	}
}
