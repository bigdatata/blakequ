package cm.commons.sys.service.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cm.commons.dao.hiber.util.Element;
import cm.commons.dao.hiber.util.Link;
import cm.commons.dao.hiber.util.OP;
import cm.commons.pojos.RouterLog;
import cm.commons.sys.service.RouterLogService;
import cm.commons.util.DateAndTimestampUtil;
import cm.commons.util.NullUtil;

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
	public void testgetPagedWithCondition(){
		List<Element> conditions=new ArrayList<Element>();
		String stationName="test";
		String beginDate="";
		String endDate="";
		
		if(NullUtil.isNull(beginDate)){
			beginDate=DateAndTimestampUtil.getNowStr("yyyy-MM-01");
		}
		conditions.add(new Element(Link.WHERE,OP.GREAT_EQ,"currTime",beginDate));
		if(NullUtil.isNull(endDate)){
			endDate=DateAndTimestampUtil.getNowStr("yyyy-MM-dd");
		}
		conditions.add(new Element(Link.AND,OP.LESS_EQ,"currTime",endDate));
		
		if(NullUtil.notNull(stationName)){	
			conditions.add(new Element(Link.AND,OP.EQ,"router.station.name",stationName));
		}
		List<RouterLog>  lists=(List<RouterLog>)rls.getPagedWithCondition(conditions, 1, 20).getList();
		System.out.println(lists.size());
		for(RouterLog rl:lists){
			System.out.println(rl);
		}
	}
}
