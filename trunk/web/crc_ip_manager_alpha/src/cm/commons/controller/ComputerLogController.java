package cm.commons.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.RequestWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cm.commons.controller.form.ComputerLogForm;
import cm.commons.pojos.ComputerLog;
import cm.commons.stat.service.ComputerService;
import cm.commons.sys.service.ComputerLogService;

/**
 * 日志管理(管理员查看和删除，普通用户只查看)
 * 按时间，站点等排序
 * @author Administrator
 *
 */
@Controller
@RequestMapping("computer_log")
public class ComputerLogController {
	
	@Autowired
	private ComputerLogService computerLogService;
	@Autowired
	private ComputerService computerService;
	
	/**
	 * 所有日志按时间排序
	 * @return
	 */
	@RequestMapping("get_log_by_time")
	public ModelAndView showAllLogByTime(){
		ModelAndView mv = new ModelAndView();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("computerLogs", this.getAllComputerLogByTime());
		mv.addAllObjects(modelMap);
		mv.setViewName("show_computerlog");
		return mv;
	}
	
	/**
	 * 所有日志按所在站点(电脑)id排序
	 * @return
	 */
	@RequestMapping("get_log_by_id")
	public ModelAndView showAllLogById(){
		ModelAndView mv = new ModelAndView();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("computerLogs", this.getAllComputerLogById());
		mv.addAllObjects(modelMap);
		mv.setViewName("show_computerlog");
		return mv;
	}
	
	/**
	 * 查询获取单个站点电脑的日志
	 * @param computer_id
	 * @return
	 */
	@RequestMapping("get_single_log")
	public ModelAndView showComputerLog(String searchStr){
		ModelAndView mv = new ModelAndView();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		System.out.println("searchStr:"+searchStr);
		modelMap.put("computerLogs", this.getComputerLogByStationNameOrId(searchStr));
		mv.addAllObjects(modelMap);
		mv.setViewName("show_computerlog");
		return mv;
	}
	
	/**
	 * 删除日志
	 * @param computer_id
	 * @return
	 */
	@RequestMapping("admin/delete_log")
	public ModelAndView deleteComputerLog(@RequestParam int computer_id){
		ModelAndView mv = new ModelAndView();
		computerLogService.deleteById(computer_id);
		mv.setView(new RedirectView("../get_log_by_time.do"));
		return mv;
	}
	
	/**
	 * 显示站点电脑
	 * @param computer_id
	 * @return
	 */
	@RequestMapping("detail_computer")
	public ModelAndView detailComputerById(@RequestParam int computer_id){
		ModelAndView mv = new ModelAndView();
		mv.addObject("computer", computerService.get(computer_id));
		mv.setViewName("error");
		return mv;
	}
	
	//**********************映射表单类封装*********************
	/**
	 * 获取所有电脑日志（按时间）
	 */
	private List<ComputerLogForm> getAllComputerLogByTime(){
		List<ComputerLog> list = computerLogService.getAllSortByTime();
		List<ComputerLogForm> computerLogs = new ArrayList<ComputerLogForm>();
		if(list != null){
			for(ComputerLog cl:list){
				ComputerLogForm clf = new ComputerLogForm();
				clf.setComputer_id(cl.getComputer().getId());
				clf.setCupRate(cl.getCupRate());
				clf.setCurrTime(cl.getCurrTime());
				clf.setId(cl.getId());
				clf.setMemRate(cl.getMemRate());
				computerLogs.add(clf);
			}
		}
		return computerLogs;
	}
	
	/**
	 * 获取所有电脑日志（按站点）
	 */
	private List<ComputerLogForm> getAllComputerLogById(){
		List<ComputerLog> list = computerLogService.getAllSortByComputer();
		List<ComputerLogForm> computerLogs = new ArrayList<ComputerLogForm>();
		if(list != null){
			for(ComputerLog cl:list){
				ComputerLogForm clf = new ComputerLogForm();
				clf.setComputer_id(cl.getComputer().getId());
				clf.setCupRate(cl.getCupRate());
				clf.setCurrTime(cl.getCurrTime());
				clf.setId(cl.getId());
				clf.setMemRate(cl.getMemRate());
				computerLogs.add(clf);
			}
		}
		return computerLogs;
	}
	
	/**
	 * 获取指定电脑日志
	 */
	private List<ComputerLogForm> getComputerLogById(int computerId){
		List<ComputerLog> list = computerLogService.getComputerLog(computerId);
		List<ComputerLogForm> computerLogs = new ArrayList<ComputerLogForm>();
		if(list != null){
			for(ComputerLog cl:list){
				ComputerLogForm clf = new ComputerLogForm();
				clf.setComputer_id(cl.getComputer().getId());
				clf.setCupRate(cl.getCupRate());
				clf.setCurrTime(cl.getCurrTime());
				clf.setId(cl.getId());
				clf.setMemRate(cl.getMemRate());
				computerLogs.add(clf);
			}
		}
		return computerLogs;
	}
	
	/**
	 * 获取指定电脑日志,通过站点名字或id
	 */
	private List<ComputerLogForm> getComputerLogByStationNameOrId(String searchStr){
		List<ComputerLog> list = computerLogService.getComputerLogByStationNameOrId(searchStr);
		List<ComputerLogForm> computerLogs = new ArrayList<ComputerLogForm>();
		if(list != null){
			for(ComputerLog cl:list){
				ComputerLogForm clf = new ComputerLogForm();
				clf.setComputer_id(cl.getComputer().getId());
				clf.setCupRate(cl.getCupRate());
				clf.setCurrTime(cl.getCurrTime());
				clf.setId(cl.getId());
				clf.setMemRate(cl.getMemRate());
				computerLogs.add(clf);
			}
		}
		return computerLogs;
	}
	
}
