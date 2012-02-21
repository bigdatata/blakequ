package cm.commons.sys.dao.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.ComputerLog;
import cm.commons.pojos.RouterLog;
import cm.commons.sys.dao.ComputerLogDao;
import cm.commons.sys.dao.impl.ComputerLogDaoImpl;
import junit.framework.TestCase;

public class ComputerLogDaoTest extends TestCase {

	private ComputerLogDao cld;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		cld = (ComputerLogDao) ac.getBean("computerLogDao");
	}
	
	public void testGet(){
		System.out.println(cld.get(1));
	}
	
	public void testUpdate(){
		ComputerLog cl = (ComputerLog) cld.get(1);
		cl.setCupRate(new Float(32));
		cld.update(cl);
		
	}
	
	public void testDelete(){
		cld.deleteById(1);
	}
	
	public void testGetComputerLogByStationNameOrId(){
		List<ComputerLog> r = cld.getComputerLogByStationNameOrId("ggg");
		if(r != null){
			for(ComputerLog rl:r){
				System.out.println(r);
			}
		}
	}

}
