package cm.commons.stat.dao.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.Station;
import cm.commons.stat.dao.impl.StationDaoImpl;
import junit.framework.TestCase;

public class StationDaoTest extends TestCase{

	private StationDaoImpl sd;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		sd = (StationDaoImpl) ac.getBean("stationDao");
	}
	
	public void testGet(){
		System.out.println(sd.get(1));
	}
	
	public void testDelete(){
		sd.delete(1);
	}
	
	public void testUpdate(){
		Station s = (Station) sd.getHibernateTemplate().load(Station.class, 2);
		s.setName("你好");
		sd.getHibernateTemplate().update(s);
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
		Station s = sd.get(1);
		System.out.println(s);
//		System.out.println(s.getComputer());
//		System.out.println(s.getRouter());
	}
}
