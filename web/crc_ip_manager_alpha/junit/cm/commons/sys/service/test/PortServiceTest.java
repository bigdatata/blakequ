package cm.commons.sys.service.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.stat.service.PortService;
import junit.framework.TestCase;

public class PortServiceTest extends TestCase {

	private PortService ps;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		ps = (PortService) ac.getBean("portService");
	}
	
	public void testGet(){
		System.out.println(ps.get(1));
	}
	
	public void testDelete(){
		ps.deleteById(1);
	}
}
