package org.pinae.simba.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.pinae.simba.SimbaTestConstant;
import org.pinae.simba.context.FileSystemResourceContext;
import org.pinae.simba.context.ResourceContext;
import org.pinae.simba.context.resource.Person;
import org.pinae.simba.exception.InvokeException;
import org.pinae.simba.exception.NoFoundException;
import org.pinae.simba.exception.XMLParseException;


public class BasicTest {
	
	private ResourceContext bean = null;
	
	@Test
	public void testPerson(){
		
		try {
			bean = new FileSystemResourceContext(SimbaTestConstant.TEST_XML);
		} catch (NoFoundException e) {
			fail(e.getMessage());
		} catch (XMLParseException e) {
			fail(e.getMessage());
		}
		
		try {
			Person person = (Person)bean.getBean("PersonFactory");
			
			assertEquals(person.getName().getLastName(),"hui");
			assertEquals(person.getName().getFirstName(),"yugeng");
			assertEquals(person.getAge(), 28);
			assertEquals(person.getEmail(), "interhui@21cn.com");
			assertEquals(person.getAddress().getCountry(), "China");
			assertEquals(person.isAdmin(), true);
			
			Thread.sleep(5000);
			
			person = (Person)bean.getBean("PersonFactory");
			
		} catch (InvokeException e) {
			fail(e.getMessage());
		} catch (NoFoundException e) {
			//fail(e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
		
	}
	
}
