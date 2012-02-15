package cm.commons.sys.service.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.sys.service.RouterLogService;

import junit.framework.TestCase;

public class RouterLogServiceTest extends TestCase {
	private RouterLogService rls;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		rls = (RouterLogService) ac.getBean("routerLogService");
	}
	
	public void testGet(){
		System.out.println(rls.get(1));
	}
}
