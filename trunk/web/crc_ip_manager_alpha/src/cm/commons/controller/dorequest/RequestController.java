package cm.commons.controller.dorequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import cm.commons.pojos.Station;
import cm.commons.stat.service.ComputerService;
import cm.commons.stat.service.PortService;
import cm.commons.stat.service.RouterService;
import cm.commons.stat.service.StationService;

@Controller
@RequestMapping("update")
public class RequestController {

	@Autowired
	private StationService stationService;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private RouterService routerService;
	@Autowired
	private PortService portService;
	/**
	 * 处理来自客户端的站点数据
	 */
	@RequestMapping("commit")
	public void doRequestData(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html");//注意加上  
	    BufferedReader reader = null;
	    try {  
	        reader = request.getReader();//获得字符流  
	        StringBuffer content= new StringBuffer();   
	        String line;  
	        while ((line = reader.readLine()) != null){  
	            content.append(line+"\r\n");
	        }
	        System.out.println(content.toString());
	        ComputerJson cj = new ComputerJson();
	        StationJson sj = new StationJson();
	        RouterJson rj = new RouterJson();
	        cj = (ComputerJson) BeanConverter.toJavaBean(cj, content.toString());
	        sj = (StationJson) BeanConverter.toJavaBean(sj, content.toString());
	        rj = (RouterJson) BeanConverter.toJavaBean(rj, content.toString());
	        List<PortJson> portList = BeanConverter.arrayToJavaBean(content.toString(), PortJson.class);
	        System.out.println("cj:"+cj);
	        System.out.println("sj:"+sj);
	        System.out.println("rj:"+rj);
	        for(PortJson p: portList){
	        	System.out.println(p);
	        }
	        
	        //save or update computer, computer_log
	        Computer c = this.toComputer(cj);
	        Station station = (Station) stationService.getStationByName(sj.getStation_name());
	        c.setStation(station);
	        computerService.saveOrUpdate(c);
	        System.out.println("*********computer,log*******");
	        /*
	        //save or update router, router_log
	        Router router = routerService.getRouterByIp(rj.getRouter_ip());
	        Router r = this.toRouter(rj, router);
	        r.setStation(station);
	        routerService.saveOrUpdate(r);
	        System.out.println("*********router,log*******");
	        
	        //save or update port
	        Set<Port> ports = router.getPorts();
	        Iterator iterator = ports.iterator();
	        Map<Integer, Port> maps = new HashMap<Integer, Port>();
	        while(iterator.hasNext()){
	        	Port p = (Port) iterator.next();
	        	maps.put(p.getIfIndex(), p);
	        }
	        for(PortJson pj:portList){
	        	Port p = this.toPort(pj, maps);
	        	p.setRouter(router);
	        	portService.saveOrUpdate(p);
	        }
	        System.out.println("*********port*******");
	        
	        //**********处理告警的几个字段
	        //1.特殊文件内容0（异常）、1（正常），空（错误）)
	        String fileFlag = sj.getFlag_value();
	        //2.ifOperStatus:10(up nochange),11(up change),20(down nochange),21(down change)
	        String[] portStates = new String[portList.size()];
	        for(int i=0; i<portList.size(); i++){
	        	portStates[i] = portList.get(i).getIfOperStatus();
	        }
	        //3.监听是否在一段时间没有发送请求
	        */
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{  
	        try {  
	            reader.close();  
	            reader = null;  
	        } catch (Exception e) {  
	      
	        }  
	    }  
	}
	
	private Computer toComputer(ComputerJson cj){
		Computer c = computerService.getComputerByIp(cj.getPc_ip());
		if(c == null){ 
			c = new Computer();
		}
		c.setIp(cj.getPc_ip());
		c.setOs(cj.getPc_info());
		c.setState(Integer.parseInt(cj.getPc_state()));
		ComputerLog cl = new ComputerLog();
		cl.setComputer(c);
		cl.setCupRate(Float.parseFloat(cj.getPc_cpu_usage()));
		cl.setMemRate(Float.parseFloat(cj.getPc_mem_usage()));
		cl.setCurrTime(new Date(System.currentTimeMillis()));
		c.getComputerLogs().add(cl);
//		c.setStation(station)
		return c;
	}
	
	private Port toPort(PortJson pj, Map<Integer, Port> maps){
		Port p = null;
		if(!maps.containsKey(pj.getIfIndex())) {
			p = new Port();
		}
		else{
			p = maps.get(pj.getIfIndex());
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
	
	private Router toRouter(RouterJson rj, Router r){
		if(r == null) r = new Router();
		r.setPortCount(Integer.parseInt(rj.getRouter_port_number()));
		r.setRouterInfo(rj.getRouter_info());
		r.setRouterIp(rj.getRouter_ip());
		r.setState(Integer.parseInt(rj.getRouter_state()));
		RouterLog rl = new RouterLog();
		rl.setCpuRate(Float.parseFloat(rj.getRouter_cup_usage()));
		rl.setCurrTime(new Date(System.currentTimeMillis()));
		rl.setMemRate(Float.parseFloat(rj.getRouter_mem_usage()));
		rl.setRouter(r);
		rl.setRouterInfo(rj.getRouter_info());
		r.getRouterLogs().add(rl);
//		r.setStation(station)
//		r.setPorts(ports)
		return r;
	}
}
