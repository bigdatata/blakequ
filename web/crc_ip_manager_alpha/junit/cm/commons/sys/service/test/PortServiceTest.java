package cm.commons.sys.service.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.Port;
import cm.commons.stat.service.PortService;
import cm.commons.util.PageModel;
import junit.framework.TestCase;

public class PortServiceTest extends TestCase {

	private PortService ps;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		ps = (PortService) ac.getBean("portService");
	}
	
	public void testGet(){
		System.out.println(ps.get(1));
	}
	
	public void testDelete(){
		ps.deleteById(1);
	}
	
	public void testGetPortsByRouter(){
		PageModel<Port> pm = ps.getPortsByRouter(6, 0, 20);
		List<Port> list = pm.getList();
		for(Port p:list){
			System.out.println(p);
		}
	}
}
