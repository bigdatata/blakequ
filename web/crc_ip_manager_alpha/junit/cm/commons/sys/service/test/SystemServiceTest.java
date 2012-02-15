package cm.commons.sys.service.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.sys.service.SystemService;
import junit.framework.TestCase;

public class SystemServiceTest extends TestCase {

	private SystemService ssd;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		ssd = (SystemService) ac.getBean("systemService");
	}
	
	public void testDelete(){
		ssd.delete(1);
	}
	
	public void testGet(){
		System.out.println(ssd.get(3));
	}
	
	
	
	public void testGetAll(){
		for(cm.commons.pojos.System s:(List<cm.commons.pojos.System>)ssd.getAll()){
			System.out.println(s);
		}
	}
	
	public void testSave(){
		cm.commons.pojos.System s = new cm.commons.pojos.System();
		s.setConfigKey("4");
		s.setConfigValue("44");
		ssd.save(s);
	}
	
	public void testSaveOrUpdate(){
		cm.commons.pojos.System s = new cm.commons.pojos.System();
		s.setId(4);
		s.setConfigKey("4");
		s.setConfigValue("44");
		ssd.saveOrUpdate(s);
	}
	
	public void testUpdate(){
		cm.commons.pojos.System s = ssd.getSystemConfigByKey("1");
		s.setConfigValue("你好");
		ssd.update(s);
	}
	
	public void testDeleteByConfigKey(){
		ssd.deleteByConfigKey("2");
	}
	
	public void testGetSystemConfigByKey(){
		System.out.println(ssd.getSystemConfigByKey("3"));
	}
	
	public void testSaveOrUpdateSystemConfigKey(){
		cm.commons.pojos.System s = new cm.commons.pojos.System();
		s.setConfigKey("4");
		s.setConfigValue("44");
		ssd.saveOrUpdateSystemConfigKey(s);
	}
	
	public void testUpdateSystemCOnfigKey(){
		cm.commons.pojos.System s = new cm.commons.pojos.System();
		s.setConfigKey("3");
		s.setConfigValue("333333");
		ssd.updateSystemConfigKey(s);
	}

}
