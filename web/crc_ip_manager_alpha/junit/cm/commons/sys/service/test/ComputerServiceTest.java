package cm.commons.sys.service.test;

import java.util.Iterator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.Computer;
import cm.commons.pojos.ComputerLog;
import cm.commons.stat.service.ComputerService;
import cm.commons.sys.service.ComputerLogService;
import junit.framework.TestCase;

public class ComputerServiceTest extends TestCase {

	private ComputerService computerService;
	private ComputerLogService cls;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		computerService = (ComputerService) ac.getBean("computerService");
		cls = (ComputerLogService) ac.getBean("computerLogService");
	}
	
	public void testDelete(){
		Computer c = (Computer) computerService.get(4);
		System.out.println(c);
		Iterator i = c.getComputerLogs().iterator();
		while(i.hasNext()){
			ComputerLog cl = (ComputerLog) i.next();
			cls.delete(cl.getId());
		}
		computerService.delete(4);
	}
	
	public void testGet(){
		Computer c = (Computer) computerService.get(1);
		System.out.println(c);
		System.out.println(c.getComputerLogs());
	}
	
	public void testDeleteComputerByIp(){
		computerService.deleteComputerByIp("32");
	}
	
	public void testGetCOmputerByIp(){
		System.out.println(computerService.getComputerByIp("32"));
	}

}
