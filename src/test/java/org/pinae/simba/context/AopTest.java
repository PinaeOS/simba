package org.pinae.simba.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.pinae.simba.TestConstant;
import org.pinae.simba.aop.pointcut.resource.ITarget;

public class AopTest {
	
	private ResourceContext bean = null;
	
	@Test
	public void testNameMatcherPointcutAdvisor() {
		
		try {
			bean = new FileSystemResourceContext(TestConstant.TEST_AOP_XML);
			
			ITarget target = (ITarget)bean.getBean("NameMatcherPointcutAdvisorTest");
			assertEquals(target.sayHello("Hui"), "Hello Hui");
			assertEquals(target.sayHello(21), "21 years old");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
