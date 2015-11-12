package org.pinae.simba.aop.proxy;

import java.util.ArrayList;
import java.util.List;

import org.aopalliance.aop.Advice;
import org.apache.log4j.Logger;

/**
 * 代理对象工厂 用于按照切入点规则来将通知织入到目标对象上
 * 
 * @author Huiyugeng
 *
 */
public class ProxyFactory {
	protected final Logger logger = Logger.getLogger("ProxyFactoryBean");

	private Object target;
	private List<Advice> intercepyors;

	public ProxyFactory() {
		this.intercepyors = new ArrayList<Advice>();
	}

	/**
	 * 返回在目标对象上按照切入点匹配规则织入通知后对象
	 * 
	 * @return 织入通知后的对象
	 */
	public Object getObject() {
		DefaultProxyFactory proxyBean = new DefaultProxyFactory(target, intercepyors);
		return proxyBean.getObject();
	}

	/**
	 * 设置由切入点驱动的通知
	 * 
	 * @param intercepyors 切入点驱动的通知数组
	 */
	public void setIntercepyor(Advice[] intercepyors) {
		if (intercepyors != null) {
			this.intercepyors = new ArrayList<Advice>(intercepyors.length);
			for (int i = 0; i < intercepyors.length; i++) {
				this.intercepyors.add(intercepyors[i]);
			}
		}
	}

	/**
	 * 设置由切入点驱动的通知
	 * 
	 * @param intercepyors 切入点驱动的通知列表
	 */
	public void setIntercepyor(List<Advice> intercepyors) {
		if (intercepyors != null) {
			this.intercepyors = intercepyors;
		}
	}

	/**
	 * 设置由切入点驱动的通知
	 * 
	 * @param intercepyor 切入点驱动的通知
	 */
	public void setIntercepyor(Advice intercepyor) {
		if (intercepyor != null) {
			this.intercepyors.add(intercepyor);
		}
	}

	/**
	 * 设置目标对象
	 * 
	 * @param target 目标兑现
	 */
	public void setTarget(Object target) {
		this.target = target;
	}
}
