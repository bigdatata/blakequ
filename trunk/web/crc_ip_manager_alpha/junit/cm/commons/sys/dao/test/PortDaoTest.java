package cm.commons.sys.dao.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.stat.dao.PortDao;
import junit.framework.TestCase;

public class PortDaoTest extends TestCase {

	private PortDao pd;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		pd = (PortDao) ac.getBean("portDao");
	}
	
	public void testDelete(){
		pd.deleteById(6);
	}
	
	public void testGet(){
		pd.get(1);
	}

}
