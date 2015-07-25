package org.pinae.simba;

import java.util.Enumeration;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestFailure;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.pinae.simba.aop.pointcut.NameMatcherPointcutAdvisorTest;
import org.pinae.simba.aop.pointcut.ParameterTypesPointcutAdvisorTest;
import org.pinae.simba.aop.pointcut.PointcutUnionTest;
import org.pinae.simba.aop.pointcut.RegexPointcutAdvisorTest;
import org.pinae.simba.aop.pointcut.TrackTracePointcutAdvisorTest;
import org.pinae.simba.context.BasicTest;
import org.pinae.simba.context.CollectionTest;
import org.pinae.simba.context.MapTest;

/**
 * Simba测试集合
 * 
 * @author Huiyugeng
 *
 */
public class SimbaTestSuite {
	
	private static Logger logger = Logger.getLogger(SimbaTestSuite.class);
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Simba-Test");
		//基础映射测试
		suite.addTest(new JUnit4TestAdapter(BasicTest.class));
		//Collection映射测试
		suite.addTest(new JUnit4TestAdapter(CollectionTest.class));
		//Map映射测试
		suite.addTest(new JUnit4TestAdapter(MapTest.class));
		
		//测试通过方法名对切入点匹配
		suite.addTest(new JUnit4TestAdapter(NameMatcherPointcutAdvisorTest.class));
		//测试调用方法的参数类型对切入点匹配
		suite.addTest(new JUnit4TestAdapter(ParameterTypesPointcutAdvisorTest.class));
		//测试通过正则表达式对切入点匹配
		suite.addTest(new JUnit4TestAdapter(RegexPointcutAdvisorTest.class));
		//测试根据方法运行的调用栈中包含特定的类中的方法来对切入点匹配
		suite.addTest(new JUnit4TestAdapter(TrackTracePointcutAdvisorTest.class));
		//测试Pointcut合并
		suite.addTest(new JUnit4TestAdapter(PointcutUnionTest.class));

		return suite;
	}
	
	public static void main(String arg[]){
		TestResult result = new TestResult();
		suite().run(result);
		
		logger.info(result.runCount() + " tests execute");
		
		if(result.wasSuccessful()){
			logger.info("All Test Pass!");
		}else{
			logger.error("Test No Pass");
			
			Enumeration<TestFailure> failures = result.failures();
			if(failures.hasMoreElements()){
				TestFailure failure = failures.nextElement();
				logger.error("Failure:" + failure.exceptionMessage());
			}
			
			Enumeration<TestFailure> errors = result.errors();
			if(errors.hasMoreElements()){
				TestFailure error = errors.nextElement();
				logger.error("Error: " + error.exceptionMessage());
			}
		}
	}
}
