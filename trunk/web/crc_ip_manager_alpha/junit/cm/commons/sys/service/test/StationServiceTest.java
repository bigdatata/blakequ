package cm.commons.sys.service.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.Segment;
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
		ss.deleteById(1);
	}
	
	public void testGetAllStationFromRoute(){
//		ss.getAllStationByRoute(3);
		for(Station s:(List<Station>)ss.getAllStationByRoute(1)){
			System.out.println(s);
		}
	}
	
	public void testSaveStationAndSegment(){
//		ss.saveStationAndSegment(ss.get(3), ss.get(4), 1);
		System.out.println(Integer.valueOf("43.4").intValue());
		System.out.println(Integer.parseInt("23.0"));
	}
	
	public void testGetStationsNotInSegment(){
		List<Station> list = ss.getStationsNotInSegment();
		for(Station s:list){
			System.out.println(s);
		}
	}
}
