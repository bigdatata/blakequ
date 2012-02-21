package cm.commons.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cm.commons.controller.form.RouterLogForm;
import cm.commons.pojos.RouterLog;
import cm.commons.stat.service.ComputerService;
import cm.commons.stat.service.RouterService;
import cm.commons.sys.service.RouterLogService;

/**
 * 日志管理(管理员查看和删除，普通用户只查看)
 * 按时间，站点等排序
 * @author Administrator
 *
 */
@Controller
@RequestMapping("router_log")
public class RouterLogController {

	@Autowired
	private RouterLogService routerLogService;
	@Autowired
	private RouterService routerService;
	/**
	 * 所有日志按时间排序
	 * @return
	 */
	@RequestMapping("get_log_by_time")
	public ModelAndView showAllLogByTime(){
		ModelAndView mv = new ModelAndView();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("routerLogs", this.getAllRouterLogByTime());
		mv.addAllObjects(modelMap);
		mv.setViewName("show_routerlog");
		return mv;
	}
	
	/**
	 * 所有日志按所在站点(路由)id排序
	 * @return
	 */
	@RequestMapping("get_log_by_id")
	public ModelAndView showAllLogById(){
		ModelAndView mv = new ModelAndView();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("routerLogs", this.getAllRouterLogById());
		mv.addAllObjects(modelMap);
		mv.setViewName("show_routerlog");
		return mv;
	}
	
	@RequestMapping("admin/delete_log")
	public ModelAndView deleteRouterLog(@RequestParam int router_id){
		ModelAndView mv = new ModelAndView();
		routerLogService.deleteById(router_id);
		mv.setView(new RedirectView("../get_log_by_time.do"));
		return mv;
	}
	
	/**
	 * 查询获取单个站点路由的日志
	 * @param computer_id
	 * @return
	 */
	@RequestMapping("get_single_log")
	public ModelAndView showComputerLog(String searchStr){
		ModelAndView mv = new ModelAndView();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		System.out.println("searchStr:"+searchStr);
		modelMap.put("routerLogs", this.getRouterLogByStationNameOrId(searchStr));
		mv.addAllObjects(modelMap);
		mv.setViewName("show_routerlog");
		return mv;
	}

	/**
	 * 显示站点路由
	 * @param computer_id
	 * @return
	 */
	@RequestMapping("detail_router")
	public ModelAndView detailComputerById(@RequestParam int router_id){
		ModelAndView mv = new ModelAndView();
		mv.addObject("router", routerService.get(router_id));
		mv.setViewName("error");
		return mv;
	}
	
	//**********************映射表单类封装*********************
	
	/**
	 * 获取所有路由日志（按时间）
	 * @return
	 */
	private List<RouterLogForm> getAllRouterLogByTime(){
		List<RouterLog> list = routerLogService.getAllSortByTime();
		List<RouterLogForm> routerLogs = new ArrayList<RouterLogForm>();
		if(list != null){
			for(RouterLog r:list){
				RouterLogForm rlf = new RouterLogForm();
				rlf.setId(r.getId());
				rlf.setCpuRate(r.getCpuRate());
				rlf.setCurrTime(r.getCurrTime());
				rlf.setErrorPacket(r.getErrorPacket());
				rlf.setMemRate(r.getMemRate());
				rlf.setRouterId(r.getRouter().getId());
				rlf.setRouterInfo(r.getRouterInfo());
				routerLogs.add(rlf);
			}
		}
		return routerLogs;
	}
	
	/**
	 * 获取所有路由日志（按站点）
	 * @return
	 */
	private List<RouterLogForm> getAllRouterLogById(){
		List<RouterLog> list = routerLogService.getAllSortByRouter();
		List<RouterLogForm> routerLogs = new ArrayList<RouterLogForm>();
		if(list != null){
			for(RouterLog r:list){
				RouterLogForm rlf = new RouterLogForm();
				rlf.setId(r.getId());
				rlf.setCpuRate(r.getCpuRate());
				rlf.setCurrTime(r.getCurrTime());
				rlf.setErrorPacket(r.getErrorPacket());
				rlf.setMemRate(r.getMemRate());
				rlf.setRouterId(r.getRouter().getId());
				rlf.setRouterInfo(r.getRouterInfo());
				routerLogs.add(rlf);
			}
		}
		return routerLogs;
	}
	
	/**
	 * 获取指定路由日志
	 * @return
	 */
	private List<RouterLogForm> getRouterLogById(int routerId){
		List<RouterLog> list = routerLogService.getRouterLog(routerId);
		List<RouterLogForm> routerLogs = new ArrayList<RouterLogForm>();
		if(list != null){
			for(RouterLog r:list){
				RouterLogForm rlf = new RouterLogForm();
				rlf.setId(r.getId());
				rlf.setCpuRate(r.getCpuRate());
				rlf.setCurrTime(r.getCurrTime());
				rlf.setErrorPacket(r.getErrorPacket());
				rlf.setMemRate(r.getMemRate());
				rlf.setRouterId(r.getRouter().getId());
				rlf.setRouterInfo(r.getRouterInfo());
				routerLogs.add(rlf);
			}
		}
		return routerLogs;
	}
	
	/**
	 * 获取指定路由日志,通过站点名字或id
	 * @return
	 */
	private List<RouterLogForm> getRouterLogByStationNameOrId(String searchStr){
		List<RouterLog> list = routerLogService.getRouterLogByStationNameOrId(searchStr);
		List<RouterLogForm> routerLogs = new ArrayList<RouterLogForm>();
		if(list != null){
			for(RouterLog r:list){
				RouterLogForm rlf = new RouterLogForm();
				rlf.setId(r.getId());
				rlf.setCpuRate(r.getCpuRate());
				rlf.setCurrTime(r.getCurrTime());
				rlf.setErrorPacket(r.getErrorPacket());
				rlf.setMemRate(r.getMemRate());
				rlf.setRouterId(r.getRouter().getId());
				rlf.setRouterInfo(r.getRouterInfo());
				routerLogs.add(rlf);
			}
		}
		return routerLogs;
	}
}
