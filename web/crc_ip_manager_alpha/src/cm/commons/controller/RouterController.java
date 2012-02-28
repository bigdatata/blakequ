package cm.commons.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cm.commons.controller.form.RouterForm;
import cm.commons.pojos.Router;
import cm.commons.pojos.Station;
import cm.commons.stat.service.PortService;
import cm.commons.stat.service.RouterService;
import cm.commons.stat.service.StationService;

@Controller
@RequestMapping("router")
public class RouterController {
	@Autowired
	private StationService stationService;
	
	@Autowired
	private RouterService routerService;
	
	@RequestMapping("show_detail")
	public ModelAndView showAllComputer(@RequestParam int routerId, int router_id){
		ModelAndView mv = new ModelAndView();
		int id = routerId>0?routerId:router_id;
		System.out.println("*******id:"+id);
		Router router = (Router) routerService.getRouterByStationId(id);
		RouterForm rf = new RouterForm();
		rf.setId(router.getId());
		rf.setPortCount(router.getPortCount());
		rf.setRouterInfo(router.getRouterInfo());
		rf.setRouterIp(router.getRouterIp());
		rf.setState(router.getState());
		rf.setStationId(router.getStation().getId());
		mv.addObject("router", rf);
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
}
