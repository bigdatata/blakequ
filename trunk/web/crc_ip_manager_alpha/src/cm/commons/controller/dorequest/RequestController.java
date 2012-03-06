package cm.commons.controller.dorequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cm.commons.controller.form.AlarmForm;
import cm.commons.json.constant.ComputerJson;
import cm.commons.json.constant.PortJson;
import cm.commons.json.constant.RouterJson;
import cm.commons.json.constant.StationJson;
import cm.commons.json.parse.BeanConverter;
import cm.commons.pojos.Computer;
import cm.commons.pojos.ComputerLog;
import cm.commons.pojos.Port;
import cm.commons.pojos.Router;
import cm.commons.pojos.RouterLog;
import cm.commons.pojos.Segment;
import cm.commons.pojos.Station;
import cm.commons.pojos.Warn;
import cm.commons.stat.service.ComputerService;
import cm.commons.stat.service.PortService;
import cm.commons.stat.service.RouterService;
import cm.commons.stat.service.SegmentService;
import cm.commons.stat.service.StationService;
import cm.commons.sys.service.SystemService;
import cm.commons.sys.service.WarnService;
import cm.commons.util.AlarmUtil;
import cm.commons.util.StationStateCheckTask;

@SuppressWarnings("unchecked")
@Controller
public class RequestController {

	private StationStateCheckTask task;
	@Autowired
	private StationService stationService;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private RouterService routerService;
	@Autowired
	private PortService portService;
	@Autowired
	private SystemService configService;
	@Autowired
	private WarnService warnService;
	@Autowired
	private SegmentService segmentService;
	
	
	/**
	 * 处理来自客户端的站点数据
	 */
	@RequestMapping("commit_data")
	public void doRequestData(HttpServletRequest request, HttpServletResponse response){
		
		task = StationStateCheckTask.getStateCheckTask(60);//默认5秒
		String frequency = configService.getSystemConfigByKey("frequency").getConfigValue();//获取更新频率
		int fgy = Integer.parseInt(frequency);
		task.setTimeToWarn(fgy*2);
		
		try {
			PrintWriter pw = response.getWriter();
			pw.print(frequency);
			pw.flush();
			request.setCharacterEncoding("utf-8");
			String content = request.getParameter("data");//获取数据*********
	        System.out.println("******"+content);
	        ComputerJson computerJson = new ComputerJson();
	        StationJson stationJson = new StationJson();
	        RouterJson routerJson = new RouterJson();
	        computerJson = (ComputerJson) BeanConverter.toJavaBean(computerJson, content);
	        stationJson = (StationJson) BeanConverter.toJavaBean(stationJson, content);
	        routerJson = (RouterJson) BeanConverter.toJavaBean(routerJson, content);
	        List<PortJson> portJsonList = BeanConverter.arrayToJavaBean(content, PortJson.class, "portDatas");
	        
	        //********save or update station, computer,log********
	        Station station = this.saveOrUpdateComputer(stationJson, computerJson);
//	        String name=station.getName();
//	        name=new String(name.getBytes("ISO8859-1"),"utf-8");
//	        System.out.println("-----"+name);
//	        System.out.println(new String(name.getBytes("ISO-8859-1"),"utf-8"));
//	        System.out.println(new String(name.getBytes("gbk"),"utf-8"));
	        
	        
	        //*********save or update router,log******************
	        Router router = this.saveOrUpdateRouter(routerJson, station);

	        System.out.println("****router:"+router);
	        //*********save or update port,log********************
	        this.saveOrUpdatePort(router, portJsonList);
	       
	        //"*********operate warn************************"*************
	        //1.特殊文件内容0（异常）、1（正常），空""（错误）
	        String fileFlag = stationJson.getFlag_value();
	        //2.路由器端口ifOperStatus(0init,1up,2down)
	        String[] portStates = new String[portJsonList.size()];
	        for(int i=0; i<portJsonList.size(); i++){
	        	portStates[i] = portJsonList.get(i).getIfOperStatus();
	        }
	        System.out.println(portStates);
	        //3.监听是否在一段时间没有发送请求
			task.addOrRefreshTime(station.getName());
			
			//记录状态未知站点
			this.saveAlarmToMap();
			
			//****************构造告警信息(台账和端口)******************
			String portState = this.checkPort(portStates);//端口状态检查
			AlarmForm wf = null;
			if(portState.length()>2 || (fileFlag != null && !fileFlag.equals("1"))){
				if(AlarmUtil.containsStation(stationJson.getStation_name())){
					wf = AlarmUtil.getByKey(stationJson.getStation_name());
				}else{
					wf = new AlarmForm();
				}
				//台账文件
				if(fileFlag.equals("0") || fileFlag.equals("")){
					if(wf.getInfo() == null){
						wf.setInfo("站点"+station.getName()+"台账文件读取异常!");
					}else{
						wf.setInfo(wf.getInfo()+" 站点"+station.getName()+"台账文件读取异常!");
					}
				}
				//端口
				if(portState.length()>2){
					if(wf.getInfo() == null){
						wf.setInfo(portState);
					}else{
						wf.setInfo(wf.getInfo()+" "+portState);
					}
				}
				wf.setStationName(station.getName());
				wf.setStation_id(station.getId());
				wf.setSegment_id(0);
				wf.setState(1);
				wf.setTime(new Date(System.currentTimeMillis()));
				//检查线段告警
				this.checkSegmentWarm(station);
				
				//加入告警
				AlarmUtil.addToMap(stationJson.getStation_name(), wf);
				saveWarnToDB(wf);
			}
			
			//如果本站点没告警则从map中移除
			if(wf == null){
				if(AlarmUtil.containsStation(station.getName())){
					AlarmUtil.removeStation(station.getName());
				}
			}
			
			//移除没有告警的线段告警
			this.removeSegmentWarn();
			
			System.out.println("****************warn*******************");
			List<AlarmForm> aff = AlarmUtil.getAllAlarm();
			for(AlarmForm a: aff){
				System.out.println(a);
			}
			
	    }catch(IOException e){
	    	e.printStackTrace();
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * 将告警放入map中
	 */
	private void saveAlarmToMap(){
		Set<String> warnStations = task.getWarnStation();
		Iterator itr = warnStations.iterator();
		while(itr.hasNext()){
			String name = (String) itr.next();
			int warnStation = ((Station)stationService.getStationByName(name)).getId();
			AlarmForm af = new AlarmForm();
			af.setState(1);
			af.setStation_id(warnStation);
			af.setTime(new Date(System.currentTimeMillis()));
			af.setInfo("站点:"+name+"状态未知！");
			AlarmUtil.addToMap(name, af);
		}
	}
	
	private void saveOrUpdatePort(Router router, List<PortJson> portJsonList){
		Map<Integer, Port> maps = null;
		Set<Port> ports = router.getPorts();
		 if(ports != null){
	        Iterator iterator = ports.iterator();
	        maps = new HashMap<Integer, Port>();
	        while(iterator.hasNext()){
	        	Port p = (Port) iterator.next();
	        	maps.put(p.getIfIndex(), p);
	        }
		 }
	     for(PortJson pj:portJsonList){
	    	Port p = this.toPort(pj, maps);
	        p.setRouter(router);
	        portService.saveOrUpdate(p);
	     }
		 
	}
	
	private Station saveOrUpdateComputer(StationJson stationJson, ComputerJson computerJson){
		Computer c = this.toComputer(computerJson);
        Station station = (Station) stationService.getStationByName(stationJson.getStation_name());
        if(station == null){
        	station = new Station();
        	station.setName(stationJson.getStation_name());
        	station.setState(0);//这个还有待验证（由三个决定）
        	station.setX("0");
        	station.setY("0");
        	station.setSegmentNum(0);
        	stationService.save(station);
        	station = (Station) stationService.getStationByName(station.getName());
        }
        c.setStation(station);
        computerService.saveOrUpdate(c);
        return station;
	}
	
	private Router saveOrUpdateRouter(RouterJson routerJson, Station station){
		Router router = routerService.getRouterByIp(routerJson.getRouter_ip());
        Router r = this.toRouter(routerJson, router);
        r.setStation(station);
        routerService.saveOrUpdate(r);
        return router;
	}
	
	
	/**
	 * 将json数据转换为pojos
	 * @param cj
	 * @return
	 */
	private Computer toComputer(ComputerJson cj){
		Computer c = computerService.getComputerByIp(cj.getPc_ip());
		if(c == null){ 
			c = new Computer();
		}
		c.setIp(cj.getPc_ip());
		c.setOs(cj.getPc_info());
		c.setState(Integer.parseInt(cj.getPc_state()));
		ComputerLog cl = new ComputerLog();
//		cl.setComputer(c);
		cl.setCupRate(Float.parseFloat(cj.getPc_cpu_usage()));
		cl.setMemRate(Float.parseFloat(cj.getPc_mem_usage()));
		cl.setCurrTime(new Date(System.currentTimeMillis()));
		c.getComputerLogs().add(cl);
//		c.setStation(station)
		return c;
	}
	
	/**
	 * 将json数据转换为pojos
	 * @param pj
	 * @param maps
	 * @return
	 */
	private Port toPort(PortJson pj, Map<Integer, Port> maps){
		Port p = null;
		Integer key = Integer.valueOf(pj.getIfIndex());
		if(maps != null && maps.containsKey(key)) {
			p = maps.get(key);
		}else{
			p = new Port();
		}
		p.setGetTime(new Date(System.currentTimeMillis()));
		p.setIfDescr(pj.getIfDescr());
		p.setIfIndex(Integer.parseInt(pj.getIfIndex()));
		p.setIfInOctets(Integer.parseInt(pj.getIfInOctets()));
		p.setIfOperStatus(Integer.parseInt(pj.getIfOperStatus()));
		p.setIfOutOctets(Integer.parseInt(pj.getIfOutOctets()));
		p.setIpRouteDest(pj.getIpRouteDest());
		p.setLocIfInBitsSec(Integer.parseInt(pj.getLocIfInBitsSec()));
		p.setLocIfInCrc(Integer.parseInt(pj.getLocIfInCRC()));
		p.setLocIfOutBitsSec(Integer.parseInt(pj.getLocIfOutBitsSec()));
		p.setPortIp(pj.getPortIp());
//		p.setRouter(router)
		return p;
	}
	
	/**
	 * 将json数据转换为pojos
	 * @param rj
	 * @param r
	 * @return
	 */
	private Router toRouter(RouterJson rj, Router r){
		if(r == null) r = new Router();
		r.setPortCount(Integer.parseInt(rj.getRouter_port_number()));
		r.setRouterInfo(rj.getRouter_info());
		r.setRouterIp(rj.getRouter_ip());
		r.setState(Integer.parseInt(rj.getRouter_state()));
		RouterLog rl = new RouterLog();
		rl.setCpuRate(Float.parseFloat(rj.getRouter_cpu_usage()));
		rl.setCurrTime(new Date(System.currentTimeMillis()));
		rl.setMemRate(Float.parseFloat(rj.getRouter_mem_usage()));
//		rl.setRouter(r);
		rl.setRouterInfo(rj.getRouter_info());
		r.getRouterLogs().add(rl);
//		r.setStation(station)
//		r.setPorts(ports)
		return r;
	}
	
	/**
	 * 判断端口的状态'ab' a是最新的，b是上次的端口状态
	 * @param ports
	 * @return
	 */
	private String checkPort(String[] ports){
		StringBuilder flag = new StringBuilder("端口");
		for(int i=0; i<ports.length; i++){
			if(!ports[i].equals("11") && !ports[i].equals("22")){
				if(ports[i].startsWith("1")){
					flag.append(i+": 状态由DOWN-->UP");
				}else if(ports[i].startsWith("2")){
					flag.append(i+": 状态由UP-->DOWN");
				}else{
					flag.append(i+": 状态由INIT-->UP(DOWN) ");
				}
			}
		}
		return flag.toString();
	}
	
	/**
	 * 将告警存储到数据库
	 * @param af
	 */
	private void saveWarnToDB(AlarmForm af){
		Warn w = new Warn();
		w.setStationId(af.getStation_id());
		w.setWarncontent(af.getInfo());
		w.setWarnstate(af.getState());
		w.setWarntime(af.getTime());
		warnService.save(w);
	}
	
	/**
	 * 检查线段异常
	 * @param station
	 * @return
	 */
	private void checkSegmentWarm(Station station){
		int id = station.getId();
		List<Segment> list = segmentService.getSegmentByStation(id);
		List<AlarmForm> alarmList = AlarmUtil.getAllAlarm();
		for(Segment s:list){
			for(AlarmForm af : alarmList){
				//看s1,s2是不是也在alarmList，如果是则说明该线段有异常
				int s1 = s.getStationByStation1Id().getId();
				int s2 = s.getStationByStation2Id().getId();
				if((s1 == id && s2 == af.getStation_id()) || (s2 == id && s1 == af.getStation_id())){
					System.out.println("_______有线段告警：");
					AlarmForm wff = new AlarmForm();
					wff.setInfo("线段id="+s.getId()+"告警，位于站点："+station.getName());
					wff.setSegment_id(s.getId());
					wff.setSg1(s1);
					wff.setSg2(s2);
					wff.setStation_id(0);
					wff.setState(1);
					wff.setStationName(station.getName());
					wff.setTime(new Date(System.currentTimeMillis()));
					AlarmUtil.addToMap(AlarmUtil.SEGMENTKEY+s.getId(), wff);
					saveWarnToDB(wff);
				}
			}
		}
	}
	
	/**
	 * 移除没有告警的线段
	 */
	private void removeSegmentWarn(){
		List<AlarmForm> all = AlarmUtil.getAlarmStation();
		List<AlarmForm> alarmList = AlarmUtil.getAlarmSegment();
		for(AlarmForm af:alarmList){
			int id = af.getSegment_id();
			int sg1 = af.getSg1();
			int sg2 = af.getSg2();
			int count = 0;
			for(AlarmForm a:all){
				if(a.getStation_id() == sg1 || a.getStation_id() == sg2){
					count++;
				}
			}
			//移除线段(如果线段两端的站点都有问题说明还是异常线段)
			if(count != 2){
				System.out.println("______remove segment______");
				AlarmUtil.removeStation(AlarmUtil.SEGMENTKEY+id);
			}
		}
	}
	
	
}
