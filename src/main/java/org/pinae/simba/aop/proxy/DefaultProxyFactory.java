package org.pinae.simba.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.pinae.simba.aop.AfterAdvice;
import org.pinae.simba.aop.BeforeAdvice;
import org.pinae.simba.aop.ClassAdvice;
import org.pinae.simba.aop.PointcutAdvisor;
import org.pinae.simba.aop.ThrowsAdvice;
import org.pinae.simba.aop.intercept.DefaultMethodInvocation;
import org.pinae.simba.aop.pointcut.TruePointcutAdvisor;

/**
 * 默认代理工厂类
 * 
 * @author Huiyugeng
 *
 */
final class DefaultProxyFactory implements InvocationHandler {
	
	private Object target;
	private List<Advice> intercepyors;

	private List<PointcutAdvisor> methodBeforeIntercepyor = new ArrayList<PointcutAdvisor>();
	private List<PointcutAdvisor> methodAfterIntercepyor = new ArrayList<PointcutAdvisor>();
	private List<PointcutAdvisor> methodAroundIntercepyor = new ArrayList<PointcutAdvisor>();
	private List<PointcutAdvisor> methodThrowsIntercepyor = new ArrayList<PointcutAdvisor>();
	private List<PointcutAdvisor> classIntercepyor = new ArrayList<PointcutAdvisor>();

	protected DefaultProxyFactory(Object target, List<Advice> intercepyors) {
		this.target = target;
		this.intercepyors = intercepyors;
		groupIntercepyor();
	}

	private void groupIntercepyor() {
		if (this.intercepyors != null) {
			for (int i = 0; i < this.intercepyors.size(); i++) {
				Advice intercepyor = this.intercepyors.get(i);
				PointcutAdvisor pointcutAdvisor = null;
				if (intercepyor instanceof PointcutAdvisor) {
					pointcutAdvisor = (PointcutAdvisor) intercepyor;
					intercepyor = pointcutAdvisor.getAdvice();
				} else {
					pointcutAdvisor = buildPointAdvisor(intercepyor);
				}
	
				if (intercepyor instanceof BeforeAdvice) {
					this.methodBeforeIntercepyor.add(pointcutAdvisor);
				} else if (intercepyor instanceof AfterAdvice) {
					this.methodAfterIntercepyor.add(pointcutAdvisor);
				} else if (intercepyor instanceof MethodInterceptor) {
					this.methodAroundIntercepyor.add(pointcutAdvisor);
				} else if (intercepyor instanceof ThrowsAdvice) {
					this.methodThrowsIntercepyor.add(pointcutAdvisor);
				} else if (intercepyor instanceof ClassAdvice) {
					this.classIntercepyor.add(pointcutAdvisor);
				}
			}
		}
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;

		for (Iterator<?> beforeIterator = this.methodBeforeIntercepyor.iterator(); beforeIterator.hasNext();) {
			PointcutAdvisor beforeAdvice = (PointcutAdvisor) beforeIterator.next();
			if (beforeAdvice.getPointcut().matcher(method, args)) {
				((BeforeAdvice) beforeAdvice.getAdvice()).before(this.target, method, args);
			}
		}
		try {
			if (this.methodAroundIntercepyor == null || this.methodAroundIntercepyor.size() == 0) {
				result = method.invoke(this.target, args);
			} else {
				for (Iterator<?> aroundIterator = this.methodAroundIntercepyor.iterator(); aroundIterator.hasNext();) {
					PointcutAdvisor aroundAdvice = (PointcutAdvisor) aroundIterator.next();
					if (aroundAdvice.getPointcut().matcher(method, args)) {
						result = ((MethodInterceptor) aroundAdvice.getAdvice()).invoke(new DefaultMethodInvocation(this.target, method, args));
					}
				}
			}
		} catch (Exception e) {
			for (Iterator<?> throwsIterator = this.methodThrowsIntercepyor.iterator(); throwsIterator.hasNext();) {
				PointcutAdvisor throwsAdvice = (PointcutAdvisor) throwsIterator.next();
				if (throwsAdvice.getPointcut().matcher(method, args)) {
					((ThrowsAdvice) throwsAdvice.getAdvice()).afterThrowing(this.target, method, args);
				}
			}
		} finally {
			for (Iterator<?> afterIterator = this.methodAfterIntercepyor.iterator(); afterIterator.hasNext();) {
				PointcutAdvisor afterAdvice = (PointcutAdvisor) afterIterator.next();
				if (afterAdvice.getPointcut().matcher(method, args)) {
					((AfterAdvice) afterAdvice.getAdvice()).after(this.target, method, args);
				}
			}
		}
		return result;
	}

	/**
	 * 返回在目标对象上按照切入点匹配规则织入通知后对象
	 * 
	 * @return 织入通知后的对象
	 */
	@SuppressWarnings("rawtypes")
	public Object getObject() {
		Object objReturn = new Object();
		for (Iterator<?> classIterator = this.classIntercepyor.iterator(); classIterator.hasNext();) {
			PointcutAdvisor classAdvice = (PointcutAdvisor) classIterator.next();
			if (classAdvice.getPointcut().matcher(this.target.getClass())) {
				this.target = ((ClassAdvice) classAdvice.getAdvice()).load(this.target);
			}
		}
		ClassLoader loader = this.target.getClass().getClassLoader();
		Class[] interfaces = this.target.getClass().getInterfaces();
		if (this.target != null) {
			objReturn = Proxy.newProxyInstance(loader, interfaces, new DefaultProxyFactory(this.target, this.intercepyors));
		}
		return objReturn;
	}

	private PointcutAdvisor buildPointAdvisor(Advice intercepyor) {
		return TruePointcutAdvisor.buildPointAdvisor(intercepyor);
	}
}
