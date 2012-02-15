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
		java.lang.System.out.println(sd.getSystemConfigByKey("key"));
	}
	
	public void testDelete(){
		sd.delete(2);
	}
	
	public void testGet(){
		java.lang.System.out.println(sd.get(9));
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
		s.setConfigKey("key");
		s.setConfigValue("1");
		sd.saveOrUpdate(s);
	}
	
	public void testUpdate(){
		System s = new System();
		s.setId(2);
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
		s.setConfigKey("key2");
		s.setConfigValue("1");
		sd.updateSystemConfigKey(s);
	}

	public void testDeleteByKey(){
		sd.deleteByConfigKey("key2");
	}
}
