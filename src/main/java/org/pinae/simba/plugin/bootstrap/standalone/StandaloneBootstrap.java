package org.pinae.simba.plugin.bootstrap.standalone;

import java.util.Iterator;

import org.pinae.simba.context.FileSystemResourceContext;
import org.pinae.simba.context.ResourceContext;
import org.pinae.simba.exception.InvokeException;
import org.pinae.simba.exception.NoFoundException;
import org.pinae.simba.exception.XmlParseException;
import org.pinae.simba.resource.Resource;

/**
 * @author Huiyugeng
 *
 */
public class StandaloneBootstrap {

	/**
	 * 执行Bean
	 * 
	 * @param args 运行参数 参数1为Bean的XML配置文件,  参数2为需要执行Bean的名称
	 * @throws NoFoundException 
	 * @throws InvokeException 
	 * @throws XmlParseException 
	 */
	public static void main(String[] args) throws InvokeException, NoFoundException, XmlParseException {
		String xmlFile = "bean-config.xml";
		if(args.length>0 && args[0]!=null && !args[0].equals("")){
			xmlFile = args[0];
		}
		ResourceContext bean = new FileSystemResourceContext(xmlFile);
		
		if(args.length>1 && args[1]!=null && !args[1].equals("")){
			bean.getBean(args[1]);
		}else{
			Resource config = bean.getConfig();
			Iterator beanNameList = config.getBeanNameList().iterator();
			while(beanNameList.hasNext()){
				String beanName = (String)beanNameList.next();
				bean.getBean(beanName);
			}
		}
		
	}
}
