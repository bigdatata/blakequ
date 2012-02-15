package cm.commons.sys.service.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.Warn;
import cm.commons.sys.service.WarnService;

import junit.framework.TestCase;

public class WarnServiceTest extends TestCase {

	private WarnService ws;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		ws = (WarnService) ac.getBean("warnService");
	}
	
	public void testDeleteByState(){
		ws.deleteByState(1);
	}
	
	public void testDeleteByStation(){
		ws.deleteByStation(1);
	}
	
	public void testDeleteByTime(){
		Calendar calendar = Calendar. getInstance();
		calendar.set(2008, 1, 1);
		Calendar calendar2 = Calendar. getInstance();
		calendar2.set(2010, 4, 3);
		ws.deleteByTime(calendar.getTime(), calendar2.getTime());
	}
	
	public void testGetWarnByDate(){
		Calendar calendar = Calendar. getInstance();
		calendar.set(2008, 1, 1);
		Calendar calendar2 = Calendar. getInstance();
		calendar2.set(2012, 4, 3);
		for(Warn w : (List<Warn>)ws.getWarnByDate(calendar.getTime(), calendar2.getTime())){
			System.out.println(w);
		}
	}

	public void testGetWarnByState(){
		for(Warn w : (List<Warn>)ws.getWarnByState(1)){
			System.out.println(w);
		}
	}
	
	public void testgetWarnByStation(){
		System.out.println(ws.getWarnByStation(1));
	}
	
	public void testSave(){
		Warn w = new Warn();
		w.setStationId(1);
		w.setWarncontent("33333");
		w.setWarnstate(1);
		w.setWarntime(new Date(2011,1,2));
		ws.save(w);
	}
}
