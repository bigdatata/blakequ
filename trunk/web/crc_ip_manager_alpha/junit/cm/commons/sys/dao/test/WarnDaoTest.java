package cm.commons.sys.dao.test;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.Warn;
import cm.commons.sys.dao.WarnDao;

import junit.framework.TestCase;

public class WarnDaoTest extends TestCase {
	
	private WarnDao wd;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		wd = (WarnDao) ac.getBean("warnDao");
	}
	
	public void testSave(){
		Warn w = new Warn("fds", 0, new Date(2011, 2, 1), 4);
		wd.save(w);
	}
	
	public void testDelteByState(){
		wd.deleteByState(1);
	}
	
	public void testDeleteByStation(){
		wd.deleteByStation(0);
	}
	
	public void testDeleteByTime(){
		Calendar calendar = Calendar. getInstance();
		calendar.set(2010, 1, 1);
		Calendar calendar2 = Calendar. getInstance();
		calendar2.set(2011, 8, 3);
		wd.deleteByTime(calendar.getTime(), calendar2.getTime());
	}
	
	public void testGetWarnByDate(){
		Calendar calendar = Calendar. getInstance();
		calendar.set(2010, 1, 1);
		Calendar calendar2 = Calendar. getInstance();
		calendar2.set(2014, 4, 3);
		System.out.println(wd.getWarnByDate(calendar.getTime(), calendar2.getTime()));
	}
	
	public void testGetWarnByState(){
		System.out.println(wd.getWarnByState(0));
	}
	
	public void testGetWarnByStation(){
		System.out.println(wd.getWarnByStation(4));
	}
	
}
