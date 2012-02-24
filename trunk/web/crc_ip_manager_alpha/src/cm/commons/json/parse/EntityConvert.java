package cm.commons.json.parse;

import java.util.Date;

import cm.commons.json.constant.ComputerJson;
import cm.commons.json.constant.PortJson;
import cm.commons.json.constant.RouterJson;
import cm.commons.json.constant.StationJson;
import cm.commons.pojos.Computer;
import cm.commons.pojos.ComputerLog;
import cm.commons.pojos.Port;
import cm.commons.pojos.Router;
import cm.commons.pojos.RouterLog;
import cm.commons.pojos.Station;

public class EntityConvert {

	public static Computer toComputer(ComputerJson cj){
		Computer c = new Computer();
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
	
	public static Port toPort(PortJson pj){
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
	
	public static Router toRouter(RouterJson rj){
		Router r = new Router();
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
