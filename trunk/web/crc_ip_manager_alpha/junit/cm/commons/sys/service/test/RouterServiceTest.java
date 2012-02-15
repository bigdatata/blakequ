package cm.commons.sys.service.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.Router;
import cm.commons.stat.service.RouterService;
import junit.framework.TestCase;

public class RouterServiceTest extends TestCase {

	private RouterService routerService;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		routerService = (RouterService) ac.getBean("routerService");
	}
	
	public void testDelete(){
		routerService.delete(1);
	}
	
	public void testDeleteRouterByIp(){
		routerService.deleteRouterByIp("2121");
	}
	
	public void testGet(){
		Router r = (Router) routerService.get(1);
		System.out.println(r);
		System.out.println(r.getPorts());
		System.out.println(r.getRouterLogs());
		
	}
	
	public void testGetAll(){
		for(Router r: (List<Router>)routerService.getAll()){
			System.out.println(r);
		}
	}
	
	public void testGetRouterByIp(){
		System.out.println(routerService.getRouterByIp("321"));
	}

}
