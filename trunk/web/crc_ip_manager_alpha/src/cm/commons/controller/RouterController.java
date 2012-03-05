package cm.commons.controller;


import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cm.commons.controller.form.PortForm;
import cm.commons.controller.form.RouterForm;
import cm.commons.controller.form.RouterLogForm;
import cm.commons.pojos.Port;
import cm.commons.pojos.Router;
import cm.commons.pojos.RouterLog;
import cm.commons.pojos.Station;
import cm.commons.stat.service.PortService;
import cm.commons.stat.service.RouterService;
import cm.commons.stat.service.StationService;
import cm.commons.sys.service.RouterLogService;

@Controller
@RequestMapping("router")
public class RouterController {
	@Autowired
	private StationService stationService;
	
	@Autowired
	private RouterService routerService;
	
	@RequestMapping("show_detail")
	public ModelAndView showRouter(@RequestParam int stationId){
		ModelAndView mv = new ModelAndView();
		System.out.println("*******id:"+stationId);
		RouterForm rf = this.getRouter(stationId);
		mv.addObject("router", rf);
		mv.addObject("routerLog", rf.getRouterLog());
		mv.addObject("ports", rf.getPorts());
		mv.setViewName("show_router");
		return mv;
	}
	
	@RequestMapping("add_router")
	public ModelAndView addRouter(RouterForm routerForm, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()){
			mv.addObject("error", "提交表单失败"+result.getAllErrors());
			mv.setViewName("../public/error");
			return mv;
		}
		Router router = new Router();
		router.setPortCount(routerForm.getPortCount());
		router.setRouterInfo(routerForm.getRouterInfo());
		router.setRouterIp(routerForm.getRouterIp());
		router.setState(routerForm.getState());
		router.setStation((Station)stationService.get(routerForm.getStationId()));
		routerService.saveOrUpdate(router);
		return mv;
	}
	
	@RequestMapping("delete_router")
	public void deleteRouter(@RequestParam int router_id ,HttpServletRequest request){
//		ModelAndView mv = new ModelAndView();
		routerService.deleteById(router_id);
//		return mv;
	}
	
	@RequestMapping("modify_router")
	public ModelAndView modifyRouter(RouterForm routerForm, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()){
			mv.addObject("error", "提交表单失败"+result.getAllErrors());
			mv.setViewName("../public/error");
			return mv;
		}
		Router router = (Router) routerService.get(routerForm.getId());
		router.setPortCount(routerForm.getPortCount());
		router.setRouterInfo(routerForm.getRouterInfo());
		router.setRouterIp(routerForm.getRouterIp());
		router.setState(routerForm.getState());
		routerService.saveOrUpdate(router);
		return mv;
	}
	
	/**
	 * 获取路由器信息
	 * @param id
	 * @return
	 */
	private RouterForm getRouter(int stationId){
		Router router = (Router) routerService.getRouterByStationId(stationId);
		RouterForm rf = new RouterForm();
		if(router != null){
			rf.setId(router.getId());
			rf.setPortCount(router.getPortCount());
			rf.setRouterInfo(router.getRouterInfo());
			rf.setRouterIp(router.getRouterIp());
			rf.setState(router.getState());
			
			//routerLog
			Set<RouterLogForm> set = router.getRouterLogs();
			if(set != null && set.size() >0){
				Iterator i = set.iterator();
				RouterLog rl = (RouterLog) i.next();
				RouterLogForm rlf = new RouterLogForm();
				rlf.setCpuRate(rl.getCpuRate());
				rlf.setCurrTime(rl.getCurrTime());
				rlf.setId(rl.getId());
				rlf.setMemRate(rl.getMemRate());
				rlf.setRouterInfo(rl.getRouterInfo());
				rf.setRouterLog(rlf);
			}
			//port
			Set<Port> ports = router.getPorts();
			if(ports != null && ports.size()>0){
				Iterator i = ports.iterator();
				while(i.hasNext()){
					Port p = (Port) i.next();
					PortForm pf = new PortForm();
					pf.setGetTime(p.getGetTime());
					pf.setId(p.getId());
					pf.setIfIndex(p.getIfIndex());
					pf.setIfInOctets(p.getIfInOctets());
					pf.setIfOperStatus(p.getIfOperStatus());
					pf.setIfOutOctets(p.getIfOutOctets());
					pf.setLocIfInBitsSec(p.getLocIfOutBitsSec());
					pf.setLocIfInCrc(p.getLocIfInCrc());
					pf.setLocIfOutBitsSec(p.getLocIfOutBitsSec());
					pf.setPortIp(p.getPortIp());
					rf.getPorts().add(pf);
				}
			}
		}
		return rf;
	}
	
	
}
