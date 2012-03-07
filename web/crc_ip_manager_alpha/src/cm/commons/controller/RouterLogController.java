package cm.commons.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cm.commons.controller.form.PageModelForm;
import cm.commons.controller.form.RouterLogForm;
import cm.commons.dao.hiber.util.Element;
import cm.commons.dao.hiber.util.Link;
import cm.commons.dao.hiber.util.OP;
import cm.commons.pojos.Router;
import cm.commons.pojos.RouterLog;
import cm.commons.pojos.User;
import cm.commons.stat.service.RouterService;
import cm.commons.sys.service.RouterLogService;
import cm.commons.util.DateAndTimestampUtil;
import cm.commons.util.NullUtil;
import cm.commons.util.PageModel;

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
	 * 删除多个日志记录
	 * @param routerLogIds
	 * @return
	 */
	@RequestMapping("admin/delete_router_log_by_ids")
	public ModelAndView deleteItems(@RequestParam String routerLogIds){
		ModelAndView mv = new ModelAndView();
		if(NullUtil.notNull(routerLogIds)){
			for(String id:routerLogIds.split(",")){
				if(NullUtil.notNull(id)){
					routerLogService.deleteById(Integer.valueOf(id));
				}
			}
		}
		mv.setView(new RedirectView("../get_log_by_page.do?pageNo=1&queryString="));
		return mv;
	}
	/**
	 * 分页显示所有日志，时间排序
	 * 可以显示指定站点日志
	 * @return
	 * @param searchStr 是查询关键字，车站名
	 * @param queryString
	 */
	@RequestMapping("get_log_by_page_old")
	public ModelAndView showAllByPage(String searchStr, @RequestParam int pageNo, @RequestParam String queryString, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String str = "";
		if(searchStr == null || searchStr.equals("")){
			str = (queryString == null || queryString.equals(""))?"":queryString;
		}else{
			str = searchStr;
		}
		//从web.xml的配置中配置页面大小
		int pageSize = Integer.parseInt(request.getSession().getServletContext().getInitParameter("page-size"));
		PageModelForm<RouterLogForm> rmf = this.getAllByPage(queryString, pageNo, pageSize);
		mv.addObject("pageModel", rmf);
		mv.addObject("routerLogs", rmf.getData());
		mv.addObject("queryStr", str);
		mv.setViewName("ShowComputer/ShowRouterLog");
		return mv;
	}
	@RequestMapping("get_log_by_page")
	public ModelAndView showQueryByPage(@RequestParam int pageNo,HttpServletRequest request,
			HttpServletResponse response){
		User condition=new User();
//		String id=request.getParameter("id");
//		if(NullUtil.notNull(id)){
//			condition.setId(Integer.valueOf(id));
//			request.setAttribute("id", id);
//		}
		List<Element> conditions=new ArrayList<Element>();
		String stationName=request.getParameter("stationName");
		String beginDate=request.getParameter("beginDate");
		String endDate=request.getParameter("endDate");
		
		if(NullUtil.isNull(beginDate)){
			beginDate=DateAndTimestampUtil.getNowStr("yyyy-MM-01");
		}
		request.setAttribute("beginDate", beginDate);
		conditions.add(new Element(Link.WHERE,OP.GREAT_EQ,"currTime",beginDate));
		if(NullUtil.isNull(endDate)){
			endDate=DateAndTimestampUtil.getNowStr("yyyy-MM-dd");
		}
		request.setAttribute("endDate", endDate);			
		conditions.add(new Element(Link.AND,OP.LESS_EQ,"currTime",endDate));
		
		if(NullUtil.notNull(stationName)){	
			conditions.add(new Element(Link.AND,OP.LIKE,"router.station.name",stationName));
		}
		request.setAttribute("stationName", stationName);
		conditions.add(new Element(Link.ORDER,"currTime",false));
		ModelAndView mv = new ModelAndView();
		//从web.xml的配置中配置页面大小
		int pageSize = Integer.parseInt(request.getSession().getServletContext().getInitParameter("page-size"));
		PageModel<User> pageModel=routerLogService.getPagedWithCondition(conditions, pageNo, pageSize);
		mv.addObject("pageModel", pageModel);
		mv.addObject("routerLogs", pageModel.getList());
		mv.setViewName("ShowComputer/ShowRouterLog");
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
		mv.setViewName("ShowComputer/ShowRouterLog");
		return mv;
	}
	
	@RequestMapping("admin/delete_log")
	public ModelAndView deleteRouterLog(@RequestParam int router_id){
		ModelAndView mv = new ModelAndView();
		routerLogService.deleteById(router_id);
		mv.setView(new RedirectView("../get_log_by_page.do?pageNo=1"));
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
		mv.setViewName("ShowComputer/ShowRouterLog");
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
				rlf.setRouterInfo(r.getRouterInfo());
				Router router = r.getRouter();
				if(router != null){
					rlf.setRouterId(router.getId());
					rlf.setStationName(router.getStation().getName());
				}else{
					rlf.setRouterId(0);
					rlf.setStationName("");
				}
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
				rlf.setMemRate(r.getMemRate());
				rlf.setRouterInfo(r.getRouterInfo());
				Router router = r.getRouter();
				if(router != null){
					rlf.setRouterId(router.getId());
					rlf.setStationName(router.getStation().getName());
				}else{
					rlf.setRouterId(0);
					rlf.setStationName("");
				}
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
				rlf.setMemRate(r.getMemRate());
				rlf.setRouterInfo(r.getRouterInfo());
				Router router = r.getRouter();
				if(router != null){
					rlf.setRouterId(router.getId());
					rlf.setStationName(router.getStation().getName());
				}else{
					rlf.setRouterId(0);
					rlf.setStationName("");
				}
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
				rlf.setMemRate(r.getMemRate());
				rlf.setRouterInfo(r.getRouterInfo());
				Router router = r.getRouter();
				if(router != null){
					rlf.setRouterId(router.getId());
					rlf.setStationName(router.getStation().getName());
				}else{
					rlf.setRouterId(0);
					rlf.setStationName("");
				}
				routerLogs.add(rlf);
			}
		}
		return routerLogs;
	}
	
	private PageModelForm<RouterLogForm> getAllByPage(String queryString,int pageNo,int pageSize){
		PageModel<RouterLog> pm = routerLogService.getAll(queryString, pageNo, pageSize);
		PageModelForm<RouterLogForm> pmf = new PageModelForm<RouterLogForm>();
		
		List<RouterLog> list = pm.getList();
		List<RouterLogForm> lists = new ArrayList<RouterLogForm>();
		if(list != null){
			for(RouterLog rl: list){
				RouterLogForm rlf = new RouterLogForm();
				rlf.setCpuRate(rl.getCpuRate());
				rlf.setCurrTime(rl.getCurrTime());
				rlf.setId(rl.getId());
				rlf.setMemRate(rl.getMemRate());
				Router router = rl.getRouter();
				if(router != null){
					rlf.setRouterId(router.getId());
					rlf.setStationName(router.getStation().getName());
				}else{
					rlf.setRouterId(0);
					rlf.setStationName("");
				}
				rlf.setRouterInfo(rl.getRouterInfo());
				lists.add(rlf);
			}
		}
		pmf.setButtomPageNo(pm.getButtomPageNo());
		pmf.setData(lists);
		pmf.setPageNo(pageNo);
		pmf.setPageSize(pageSize);
		pmf.setTotalPages(pm.getTotalPages());
		return pmf;
	}
	

}
