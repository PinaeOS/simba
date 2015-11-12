package org.pinae.simba.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.pinae.simba.TestConstant;
import org.pinae.simba.context.FileSystemResourceContext;
import org.pinae.simba.context.ResourceContext;
import org.pinae.simba.context.resource.MapBean;
import org.pinae.simba.exception.InvokeException;
import org.pinae.simba.exception.NoFoundException;
import org.pinae.simba.exception.XmlParseException;

public class MapTest {
	
	private ResourceContext bean = null;
	
	@Test
	public void testMap(){
		
		try {
			bean = new FileSystemResourceContext(TestConstant.TEST_XML);
		} catch (NoFoundException e) {
			fail(e.getMessage());
		} catch (XmlParseException e) {
			fail(e.getMessage());
		}
		
		try {
			MapBean mapBean = (MapBean)bean.getBean("Map");
			assertEquals(mapBean.getMap().size(), 3);
		} catch (InvokeException e) {
			fail(e.getMessage());
		} catch (NoFoundException e) {
			fail(e.getMessage());
		}
		
	}
	
}
