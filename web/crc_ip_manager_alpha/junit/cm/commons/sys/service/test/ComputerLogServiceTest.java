package cm.commons.sys.service.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.pojos.ComputerLog;
import cm.commons.pojos.RouterLog;
import cm.commons.sys.service.ComputerLogService;

import junit.framework.TestCase;

public class ComputerLogServiceTest extends TestCase {

	private ComputerLogService cls;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		String []locations={ "classpath:spring/applicationContext-*.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(locations);
		cls = (ComputerLogService) ac.getBean("computerLogService");
	}
	
	public void testGetAllSortByComputer(){
		for(ComputerLog rl:(List<ComputerLog>)cls.getAllSortByComputer()){
			System.out.println(rl);
		}
	}
	
	public void testGetAllSortByTime(){
		for(ComputerLog rl:(List<ComputerLog>)cls.getAllSortByTime()){
			System.out.println(rl);
		}
	}
	
	public void testComputerLog(){
		for(ComputerLog rl:(List<ComputerLog>)cls.getComputerLog(1)){
			System.out.println(rl);
		}
	}

}
