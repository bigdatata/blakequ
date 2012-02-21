package cm.commons.sys.service.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.Router;
import cm.commons.pojos.RouterLog;
import cm.commons.sys.service.RouterLogService;

import junit.framework.TestCase;

public class RouterLogServiceTest extends TestCase {
	private RouterLogService rls;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		rls = (RouterLogService) ac.getBean("routerLogService");
	}
	
	public void testGet(){
		System.out.println(rls.get(1));
	}
	
	public void testGetAllSortByRouter(){
		for(RouterLog rl:(List<RouterLog>)rls.getAllSortByRouter()){
			System.out.println(rl);
		}
	}
	
	public void testGetAllSortByTime(){
		for(RouterLog rl:(List<RouterLog>)rls.getAllSortByTime()){
			System.out.println(rl);
		}
	}
	
	public void testRouterLog(){
		for(RouterLog rl:(List<RouterLog>)rls.getRouterLog(3)){
			System.out.println(rl);
		}
	}
}
