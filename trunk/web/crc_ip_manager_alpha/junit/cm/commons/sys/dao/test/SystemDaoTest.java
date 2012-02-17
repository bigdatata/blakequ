package cm.commons.sys.dao.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.sys.dao.SystemDao;
import cm.commons.pojos.System;
import junit.framework.TestCase;

public class SystemDaoTest extends TestCase {

	private SystemDao sd;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		sd = (SystemDao) ac.getBean("systemDao");
	}
	
	public void testGetSystemConfigByKey(){
		java.lang.System.out.println(sd.getSystemConfigByKey("1"));
	}
	
	public void testDelete(){
		sd.deleteById(2);
	}
	
	public void testGet(){
		java.lang.System.out.println(sd.get(1));
	}
	
	public void testGetAll(){
		assertNotNull(sd.getAll());
	}
	
	public void testSave(){
		System s;
		for(int i=0; i<5; i++){
			s = new System();
			s.setConfigKey("rate"+i);
			s.setConfigValue("1"+i);
			sd.save(s);
		}
	}
	
	public void testSaveOrUpdate(){
		System s = new System();
		s.setConfigKey("1");
		s.setConfigValue("1sdafdafda");
		sd.saveOrUpdate(s);
	}
	
	public void testUpdate(){
		System s = new System();
		s.setId(4);
		s.setConfigKey("key2");
		s.setConfigValue("1");
		sd.update(s);
	}
	
	public void testSaveOrUpdateSystemConfigKey(){
		System s = new System();
		s.setConfigKey("key2");
		s.setConfigValue("1");
		sd.saveOrUpdateSystemConfigKey(s);
	}
	
	public void testUpdateSystemConfigKey(){
		System s = new System();
		s.setConfigKey("3");
		s.setConfigValue("133333333");
		sd.updateSystemConfigKey(s);
	}

	public void testDeleteByKey(){
		sd.deleteByConfigKey("3");
	}
}
