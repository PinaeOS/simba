package org.pinae.simba.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.pinae.simba.TestConstant;
import org.pinae.simba.context.FileSystemResourceContext;
import org.pinae.simba.context.ResourceContext;
import org.pinae.simba.context.resource.Person;
import org.pinae.simba.exception.InvokeException;
import org.pinae.simba.exception.NoFoundException;
import org.pinae.simba.exception.XmlParseException;


public class BasicTest {
	
	private ResourceContext bean = null;
	
	@Test
	public void testPerson(){
		
		try {
			bean = new FileSystemResourceContext(TestConstant.TEST_XML);
		} catch (NoFoundException e) {
			fail(e.getMessage());
		} catch (XmlParseException e) {
			fail(e.getMessage());
		}
		
		try {
			Person person = (Person)bean.getBean("PersonFactory");
			
			assertEquals(person.getName().getLastName(),"hui");
			assertEquals(person.getName().getFirstName(),"yugeng");
			assertEquals(person.getAge(), 27);
			assertEquals(person.getEmail(), "interhui@21cn.com");
			assertEquals(person.getAddress().getCountry(), "China");
			assertEquals(person.isAdmin(), true);
			assertEquals(person.getAddress().getPostCode().getCode(), 528000);
			assertEquals(person.getAddress().getPostCode().isActive(), true);
			assertEquals(person.getAddress().getPostCode().getStation(), "103011");
			
			Thread.sleep(5000);
			
			person = (Person)bean.getBean("PersonFactory");
			
			assertEquals(person.getName().getLastName(),"hui");
			assertEquals(person.getName().getFirstName(),"yugeng");
			assertEquals(person.getAge(), 27);
			assertEquals(person.getEmail(), "interhui@21cn.com");
			assertEquals(person.getAddress().getCountry(), "China");
			assertEquals(person.isAdmin(), true);
			assertEquals(person.getAddress().getPostCode().getCode(), 528000);
			assertEquals(person.getAddress().getPostCode().isActive(), true);
			assertEquals(person.getAddress().getPostCode().getStation(), "103011");
			
		} catch (InvokeException e) {
			fail(e.getMessage());
		} catch (NoFoundException e) {
			fail(e.getMessage());
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
		
	}
	
}
