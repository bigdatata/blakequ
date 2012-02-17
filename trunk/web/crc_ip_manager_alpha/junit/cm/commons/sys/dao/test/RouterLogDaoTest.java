package cm.commons.sys.dao.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.RouterLog;
import cm.commons.sys.dao.RouterLogDao;
import cm.commons.sys.dao.impl.RouterLogDaoImpl;
import junit.framework.TestCase;

public class RouterLogDaoTest extends TestCase {

	private RouterLogDao rld;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		rld = (RouterLogDao) ac.getBean("routerLogDao");
	}
	
	public void testGet(){
		System.out.println(rld.get(1));
	}
	
	public void testUpdate(){
		RouterLog rl = (RouterLog) rld.get(1);
		rl.setMemRate(new Float(32));
		rld.update(rl);
	}
	
	public void testDelete(){
		rld.deleteById(1);
	}

}
