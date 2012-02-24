package cm.commons.stat.dao.test;

import java.util.Iterator;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.Port;
import cm.commons.pojos.Router;
import cm.commons.stat.dao.RouterDao;
import cm.commons.stat.dao.impl.RouterDaoImpl;

import junit.framework.TestCase;

public class RouterDaoTest extends TestCase {

	private RouterDao rd;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		rd = (RouterDao) ac.getBean("routerDao");
	}
	
	public void testDelete(){
		rd.deleteById(1);
//		Router r = (Router) rd.get(1);
//		rd.delete(r);
	}
	
	public void testSave(){
		Router r = new Router();
		r.setPortCount(3);
		r.setRouterInfo("44444444");
		r.setRouterIp("1111111");
		rd.save(r);
	}

	public void testDeleteRouterByIp(){
//		rd.deleteRouterByIp("432");
		rd.deleteById(3);
	}
	
	public void testGetRouterByIp(){
		System.out.println(rd.getRouterByIp("2"));
	}
	
	public void testGet(){
		Router r = (Router) rd.get(1);
		System.out.println(r);
		System.out.println(r.getStation());
		System.out.println(r.getRouterLogs());
		Iterator i = r.getPorts().iterator();
		while(i.hasNext()){
			Port p = (Port) i.next();
			System.out.println(p);
		}
	}
	
	public void testGetRouterByStationId(){
		System.out.println(rd.getRouterByStationId(1));
	}
}
