package cm.commons.sys.dao.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.ComputerLog;
import cm.commons.sys.dao.impl.ComputerLogDaoImpl;
import junit.framework.TestCase;

public class ComputerLogDaoTest extends TestCase {

	private ComputerLogDaoImpl cld;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		cld = (ComputerLogDaoImpl) ac.getBean("computerLogDao");
	}
	
	public void testGet(){
		System.out.println(cld.get(1));
	}
	
	public void testUpdate(){
		ComputerLog cl = (ComputerLog) cld.getHibernateTemplate().load(ComputerLog.class, 1);
		cl.setCupRate(new Float(32));
		cld.update(cl);
		
	}

}
