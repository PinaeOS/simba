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
import org.pinae.simba.aop.target.Target;



final class DefaultProxyFactory implements InvocationHandler{
	private Object target;
	private Advice[] intercepyors;
	
	private List<PointcutAdvisor> methodBeforeIntercepyor = new ArrayList<PointcutAdvisor>();
	private List<PointcutAdvisor> methodAfterIntercepyor = new ArrayList<PointcutAdvisor>();
	private List<PointcutAdvisor> methodAroundIntercepyor = new ArrayList<PointcutAdvisor>();
	private List<PointcutAdvisor> methodThrowsIntercepyor = new ArrayList<PointcutAdvisor>();
	private List<PointcutAdvisor> classIntercepyor = new ArrayList<PointcutAdvisor>();
	
	protected DefaultProxyFactory(Target target, Advice[] intercepyors){
		this.target = target.getTarget();
		this.intercepyors = intercepyors;
		groupIntercepyor();
	}
	
	private DefaultProxyFactory(Object target, Advice[] intercepyors){
		this.target = target;
		this.intercepyors = intercepyors;
		groupIntercepyor();
	}
	
	private void groupIntercepyor(){
		for(int i=0;i<intercepyors.length;i++){
			Advice intercepyor = intercepyors[i];
			PointcutAdvisor pointcutAdvisor = null;
			if(intercepyor instanceof PointcutAdvisor){
				pointcutAdvisor = (PointcutAdvisor)intercepyor;
				intercepyor = pointcutAdvisor.getAdvice();
			}else{
				pointcutAdvisor = buildPointAdvisor(intercepyor);
			}
			
			if(intercepyor instanceof BeforeAdvice){
				methodBeforeIntercepyor.add(pointcutAdvisor);
			}else if(intercepyor instanceof AfterAdvice){
				methodAfterIntercepyor.add(pointcutAdvisor);
			}else if(intercepyor instanceof MethodInterceptor){
				methodAroundIntercepyor.add(pointcutAdvisor);
			}else if(intercepyor instanceof ThrowsAdvice){
				methodThrowsIntercepyor.add(pointcutAdvisor);
			}else if(intercepyor instanceof ClassAdvice){
				classIntercepyor.add(pointcutAdvisor);
			}
		}
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		
		for(Iterator<?> beforeIterator = methodBeforeIntercepyor.iterator();beforeIterator.hasNext();){
			PointcutAdvisor beforeAdvice = (PointcutAdvisor)beforeIterator.next();
			if(beforeAdvice.getPointcut().matcher(method, args)){
				((BeforeAdvice)beforeAdvice.getAdvice()).before(target, method, args);
			}
		}
		try{
			if(methodAroundIntercepyor==null|| methodAroundIntercepyor.size()==0){
				result = method.invoke(target, args);
			}else{
				for(Iterator<?> aroundIterator = methodAroundIntercepyor.iterator();aroundIterator.hasNext();){
					PointcutAdvisor aroundAdvice = (PointcutAdvisor)aroundIterator.next();
					if(aroundAdvice.getPointcut().matcher(method, args)){
						result = ((MethodInterceptor)aroundAdvice.getAdvice()).invoke(new DefaultMethodInvocation(target, method, args));
					}
				}
			}
		}catch(Exception e){
			for(Iterator<?> throwsIterator = methodThrowsIntercepyor.iterator();throwsIterator.hasNext();){
				PointcutAdvisor throwsAdvice = (PointcutAdvisor)throwsIterator.next();
				if(throwsAdvice.getPointcut().matcher(method, args)){
					((ThrowsAdvice)throwsAdvice.getAdvice()).afterThrowing(target, method, args);
				}
			}
		}finally{
			for(Iterator<?> afterIterator = methodAfterIntercepyor.iterator();afterIterator.hasNext();){
				PointcutAdvisor afterAdvice = (PointcutAdvisor)afterIterator.next();
				if(afterAdvice.getPointcut().matcher(method, args)){
					((AfterAdvice)afterAdvice.getAdvice()).after(target, method, args);
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
	public Object getObject(){
		Object objReturn =  new Object();
		for(Iterator<?> classIterator = classIntercepyor.iterator();classIterator.hasNext();){
			PointcutAdvisor classAdvice = (PointcutAdvisor)classIterator.next();
			if(classAdvice.getPointcut().matcher(target.getClass())){
				target = ((ClassAdvice)classAdvice.getAdvice()).load(target);
			}
		}
		ClassLoader loader = target.getClass().getClassLoader();
		Class[] interfaces = target.getClass().getInterfaces();
		if(target!=null){
			objReturn =  Proxy.newProxyInstance(loader, interfaces, new DefaultProxyFactory(target, intercepyors));
		}
		return objReturn;
	}
	
	private PointcutAdvisor buildPointAdvisor(Advice intercepyor){
		return TruePointcutAdvisor.buildPointAdvisor(intercepyor);
	}
}
