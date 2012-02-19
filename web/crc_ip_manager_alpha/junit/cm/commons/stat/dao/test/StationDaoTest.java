package cm.commons.stat.dao.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.Station;
import cm.commons.stat.dao.StationDao;
import cm.commons.stat.dao.impl.StationDaoImpl;
import junit.framework.TestCase;

public class StationDaoTest extends TestCase{

	private StationDao sd;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		sd = (StationDao) ac.getBean("stationDao");
	}
	
	public void testGet(){
		Station s = (Station) sd.get(1);
//		System.out.println(s);
		System.out.println(s.getComputer());
		System.out.println(s.getRouter());
//		System.out.println(s.getSegmentsForStation1Id());
//		System.out.println(s.getSegmentsForStation2Id());
	}
	
	public void testDelete(){
		sd.deleteById(1);
	}
	
	public void testUpdate(){
		Station s = (Station) sd.get(2);
		s.setName("你好");
		sd.update(s);
	}
	
	
	public void testGetErrorStationCount(){
		System.out.println(sd.getErrorStationCount());
	}
	
	public void testGetStationCount(){
		System.out.println(sd.getStationCount());
	}
	
	public void testGetUnkonwStationCount(){
		System.out.println(sd.getUnknowStationCount());
	}
	

	public void testGetMainStation(){
		System.out.println(sd.getMainStaion());
	}
	
	public void testOther(){
//		Station s = sd.get(1);
//		System.out.println(s);
//		System.out.println(s.getComputer());
//		System.out.println(s.getRouter());
	}
	
	public void testAdd(){
		Station s = new Station();
		s.setName("fdae4343");
		s.setSegmentNum(3);
		s.setState(0);
		s.setX("44");
		s.setY("46");
		s.getSegmentsForStation1Id().add(sd.get(3));
		s.getSegmentsForStation2Id().add(sd.get(4));
		s.setSegmentsForStation1Id(s.getSegmentsForStation1Id());
		s.setSegmentsForStation2Id(s.getSegmentsForStation2Id());
		sd.saveOrUpdate(s);
		
	}
	
	public void testGetMainStationByRoute(){
		List<Station> ls = sd.getMainStationByRoute(2);
		for(Station s:ls){
			System.out.println(s);
		}
	}
	
	public void testGetStationsNotInSegment(){
		List<Station> ls = sd.getStationsNotInSegment();
		for(Station s:ls){
			System.out.println(s);
		}
	}
	
}
