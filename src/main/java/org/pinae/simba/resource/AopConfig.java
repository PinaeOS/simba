package org.pinae.simba.resource;

import org.pinae.simba.aop.proxy.ProxyFactory;
import org.pinae.simba.beanfactory.KernelConstant;

public class AopConfig extends BeanConfig {

	public String getBeanClass() {
		return ProxyFactory.class.getName();
	}

	public void setTarget(String target) {
		PropertyConfig targetConfig = new PropertyConfig();
		targetConfig.setPropertyName(KernelConstant.AOP_TARGET);
		targetConfig.setPropertyType(KernelConstant.PROPERTY_REFLECTION);
		targetConfig.setPropertyValue(target);
		
		super.addPropertyConfig(targetConfig);
	}
	
	public void setIntercepyor(String intercepyor) {
		
		PropertyConfig intercepyorConfig = new PropertyConfig();
		intercepyorConfig.setPropertyName(KernelConstant.AOP_INTERCEPYOT);
		intercepyorConfig.setPropertyType(KernelConstant.PROPERTY_REFLECTION);
		intercepyorConfig.setPropertyValue(intercepyor);
		
		super.addPropertyConfig(intercepyorConfig);
	}
	
	public void setAdvice(String advice) {
		PropertyConfig intercepyorConfig = new PropertyConfig();
		intercepyorConfig.setPropertyName(KernelConstant.AOP_ADVICE);
		intercepyorConfig.setPropertyType(KernelConstant.PROPERTY_REFLECTION);
		intercepyorConfig.setPropertyValue(advice);
		
		super.addPropertyConfig(intercepyorConfig);
	}
}
