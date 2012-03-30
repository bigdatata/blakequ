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
		task.setTimeToWarn(fgy+60);
		//设置告警清除频率
		AlarmUtil.setTime(Integer.parseInt(frequency)+120);
		
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
	        
	        //*********save or update router,log******************
	        Router router = this.saveOrUpdateRouter(routerJson, station);

	        System.out.println("****router:"+router);
	        //*********save or update port,log********************
	        this.saveOrUpdatePort(router, portJsonList);
	       
	        //"*********operate warn************************"*************
	        //1.特殊文件内容0（异常）、1（正常）
	        String fileFlag = stationJson.getFlag_value();
	        //2.路由器端口ifOperStatus(0init,1up,2down)
	        String[] portStates = new String[portJsonList.size()];
	        for(int i=0; i<portJsonList.size(); i++){
	        	portStates[i] = portJsonList.get(i).getIfOperStatus();
	        }
	        //3.监听是否在一段时间没有发送请求
			task.addOrRefreshTime(station.getName());
			
			//记录状态未知站点
			this.saveAlarmToMap();
			
			//****************构造告警信息(台账和端口)******************
			String[] portState = this.checkPort(portStates, portJsonList, station);//端口状态检查
			AlarmForm wf = null;
			if(portState[0].length()>2 || (fileFlag != null && !fileFlag.equals("1"))){
				if(AlarmUtil.containsStation(stationJson.getStation_name())){
					wf = AlarmUtil.getByKey(stationJson.getStation_name());
					wf.setInfo("");
				}else{
					wf = new AlarmForm();
				}
				//台账文件
				if(fileFlag.equals("0") || fileFlag.equals("")){
					if(wf.getInfo() == null){
						wf.setInfo("请单击"+station.getName()+"图标右键查看设备故障!");
					}else{
						wf.setInfo(wf.getInfo()+"请单击车站"+station.getName()+"图标右键查看设备故障!");
					}
					wf.setState(3);
				}else{
					if(wf.getInfo() == null){
						wf.setInfo("无法获取"+station.getName()+"固定盘固定路径文件内容!");
					}else{
						wf.setInfo(wf.getInfo()+"无法获取"+station.getName()+"固定盘固定路径文件内容!");
					}
					wf.setState(1);
				}
				//端口未知
				if(portState[0].length()>2){
					if(wf.getInfo() == null){
						wf.setInfo(portState[0]);
					}else{
						wf.setInfo(wf.getInfo()+" "+portState[0]);
					}
				}
				wf.setStationName(station.getName());
				wf.setStation_id(station.getId());
				wf.setSegment_id(0);
				wf.setTime(new Date(System.currentTimeMillis()));
				
				//加入告警
				AlarmUtil.addToMap(stationJson.getStation_name(), wf);
				saveWarnToDB(wf);
			}
			//检查线段告警
			this.checkSegmentWarm(station, portState);
			
			//如果本站点没告警则从map中移除(定时清除)
			if(wf == null){
				if(AlarmUtil.containsStation(stationJson.getStation_name())){
					AlarmUtil.removeStation(stationJson.getStation_name());
				}
			}
			
			//移除没有告警的线段告警(定时清除)
//			this.removeSegmentWarn(station, portState);
			
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
			Station station = (Station)stationService.getStationByName(name);
			int warnStation = station.getId();
			AlarmForm af = new AlarmForm();
			af.setState(2);
			af.setStation_id(warnStation);
			af.setTime(new Date(System.currentTimeMillis()));
			af.setInfo("对"+name+"方向通信信号未正常获取！");
			AlarmUtil.addToMap(name, af);
			saveUnknowAlarmSegmentToMap(station);
		}
	}
	
	/**
	 * 将未知站点相连线段加入map
	 * @param station
	 */
	private void saveUnknowAlarmSegmentToMap(Station station){
		List<Segment> list = segmentService.getSegmentByStation(station.getId());
		if(list != null && list.size()>0){
			for(Segment s:list){
				AlarmForm af = new AlarmForm();
				af.setState(2);
				af.setStation_id(station.getId());
				af.setStationName(station.getName());
				af.setTime(new Date(System.currentTimeMillis()));
				af.setInfo("对"+station.getName()+"方向通信信号未正常获取！");
				af.setSegment_id(s.getId());
				af.setSg1(s.getStationByStation1Id().getId());
				af.setSg1_name(s.getStationByStation1Id().getName());
				af.setSg2(s.getStationByStation2Id().getId());
				af.setSg2_name(s.getStationByStation2Id().getName());
				AlarmUtil.addToMap(AlarmUtil.SEGMENTKEY+s.getId(), af);
				//存储到数据库
				saveWarnToDB(af);
			}
		}
	}
	
	private void saveOrUpdatePort(Router router, List<PortJson> portJsonList){
		//只是更新port
	     for(PortJson pj:portJsonList){
//	    	Port p = this.toPort(pj, maps);
	    	Port p = this.toPort(pj);
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
	 *, Map<Integer, Port> maps
	 */
	private Port toPort(PortJson pj){
		Port p = new Port();
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
	 * @param ports ports[0]:告警内容，ports[1]:告警对端车站
	 * @return
	 */
	private String[] checkPort(String[] ports, List<PortJson> portJsonList, Station station){
		String[] info = new String[2];
		StringBuilder flag = null;
		if(ports.length == 0){
			flag = new StringBuilder("对"+station.getName()+"方向通信信号未正常获取");
			info[0] = flag.toString();
			info[1] = "";
			saveUnknowAlarmSegmentToMap(station);
			return info;
		}else{
			flag = new StringBuilder("告警");
			info[0] = flag.toString();
			info[1] = "";
		}
		for(int i=0; i<ports.length; i++){
			if(ports[i].equals("10") || ports[i].equals("20")){
				break;
			}
			if(!ports[i].equals("11") && !ports[i].equals("22")){
				if(ports[i].startsWith("2")){
//					flag.append(i+": 状态由UP-->DOWN ");
					String names = portJsonList.get(i).getWarnStation();
					info[0] = flag.toString();
					info[1] = names;
				}
//				else if(ports[i].startsWith("1")){
//					flag.append(i+": 状态由DOWN-->UP ");
//				}else{
//					flag.append(i+": 状态由INIT-->UP(DOWN) ");
//				}
			}
		}
		return info;
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
	 * @return sgId:异常线段id
	 */
	private int checkSegmentWarm(Station station, String[] portState){
		int sgId = -1;
		if(null != portState[1] && !"".equals(portState[1])){
			System.out.println("-----有线段告警："+portState[1]);
			int id = station.getId();
			String[] name = portState[1].split(",");
			System.out.println("告警线段："+station.getName()+"--"+portState[1]);
			for(int i=0; i<name.length; i++){
				Station sp = (Station) stationService.getStationByName(name[i]);
				if(sp != null){
					//查询告警线段
					List<Segment> list = segmentService.getSegmentByStation(id);
					for(Segment s:list){
						int s1 = s.getStationByStation1Id().getId();
						int s2 = s.getStationByStation2Id().getId();
						if((s1 == id && s2 == sp.getId()) || (s1 == sp.getId() && s2 == id)){
							sgId = s.getId();
						}
					}
					
					//构造告警线段
					AlarmForm wff = new AlarmForm();
					wff.setInfo("对"+name[i]+"方向通信  有异常");
					wff.setSegment_id(sgId);
					wff.setSg1(id);
					wff.setSg2(sp.getId());
					wff.setSg1_name(station.getName());
					wff.setSg2_name(sp.getName());
					wff.setStation_id(station.getId());
					wff.setState(1);
					wff.setStationName(station.getName());
					wff.setTime(new Date(System.currentTimeMillis()));
					AlarmUtil.addToMap(AlarmUtil.SEGMENTKEY+sgId, wff);
					saveWarnToDB(wff);
				}else{
					System.out.println("------未查询到对端站点："+name[i]);
				}
			}
		}
		return sgId;
	}
	
}
