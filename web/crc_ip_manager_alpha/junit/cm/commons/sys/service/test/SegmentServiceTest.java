package cm.commons.sys.service.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.stat.service.SegmentService;

import junit.framework.TestCase;

public class SegmentServiceTest extends TestCase {
	
	private SegmentService ss;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		ss = (SegmentService) ac.getBean("segmentService");
	}
	
	public void testGet(){
		System.out.println(ss.get(1));
	}
	
	public void testDelete(){
		ss.delete(1);
	}
}
