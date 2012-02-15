package cm.commons.sys.service.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.Station;
import cm.commons.stat.service.StationService;

import junit.framework.TestCase;

public class StationServiceTest extends TestCase {

	private StationService ss;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		ss = (StationService) ac.getBean("stationService");
	}
	
	public void testGet(){
		Station s = (Station) ss.get(1);
		System.out.println(s);
		System.out.println(s.getComputer());
		System.out.println(s.getRouter());
		System.out.println(s.getSegmentsForStation1Id());
		System.out.println(s.getSegmentsForStation2Id());
	}
	
	public void testDelete(){
		ss.delete(1);
	}

}
