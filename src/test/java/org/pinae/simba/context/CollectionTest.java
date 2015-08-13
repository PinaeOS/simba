package org.pinae.simba.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.pinae.simba.SimbaTestConstant;
import org.pinae.simba.context.FileSystemResourceContext;
import org.pinae.simba.context.ResourceContext;
import org.pinae.simba.context.resource.CollectionBean;
import org.pinae.simba.exception.InvokeException;
import org.pinae.simba.exception.NoFoundException;
import org.pinae.simba.exception.XmlParseException;

public class CollectionTest {
	
	private ResourceContext bean = null;
	
	@Test
	public void testCollection(){
		
		try {
			bean = new FileSystemResourceContext(SimbaTestConstant.TEST_XML);
		} catch (NoFoundException e) {
			fail(e.getMessage());
		} catch (XmlParseException e) {
			fail(e.getMessage());
		}
		
		try {
			CollectionBean collectionBean = (CollectionBean)bean.getBean("CollectionBean");
			assertEquals(collectionBean.getList().size(), 5);
			
			assertEquals(collectionBean.getSet().size(), 6);
			
			assertEquals(collectionBean.getCollection().size(), 3);
		} catch (InvokeException e) {
			fail(e.getMessage());
		} catch (NoFoundException e) {
			fail(e.getMessage());
		}
		
	}
	
}
