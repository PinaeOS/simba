package org.pinae.simba.aop.target;

/**
 * Target接口的默认实现
 * 
 * @author Huiyugeng
 *
 */
public class DefaultTarget implements Target {
	private Object target;
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

}
