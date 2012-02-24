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
import cm.commons.controller.form.PageModelForm;
import cm.commons.pojos.ComputerLog;
import cm.commons.stat.service.ComputerService;
import cm.commons.sys.service.ComputerLogService;
import cm.commons.util.PageModel;

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
	 * 查询获取站点电脑的日志,按站点排序
	 * @param computer_id
	 * @return
	 */
	@RequestMapping("get_by_station_name")
	public ModelAndView showComputerLog(String searchStr, @RequestParam int pageNo, @RequestParam String queryString, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String str = "";
		if(searchStr == null || searchStr.equals("")){
			str = (queryString == null || queryString.equals(""))?"":queryString;
		}else{
			str = searchStr;
		}
		int pageSize = Integer.parseInt(request.getSession().getServletContext().getInitParameter("page-size"));
		PageModelForm<ComputerLogForm> pmf = this.getAllComputerLogByName(str, pageNo, pageSize);
		mv.addObject("pageModel", pmf);
		mv.addObject("computerLogs", pmf.getData());
		mv.setViewName("show_computerlog");
		return mv;
	}
	
	/**
	 * 获取所有数据通过分页,按时间排序
	 * @return
	 */
	@RequestMapping("get_by_time")
	public ModelAndView getAllByPage(String searchStr, @RequestParam int pageNo, @RequestParam String queryString, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String str = "";
		if(searchStr == null || searchStr.equals("")){
			str = (queryString == null || queryString.equals(""))?"":queryString;
		}else{
			str = searchStr;
		}
		//从web.xml的配置中配置页面大小
		int pageSize = Integer.parseInt(request.getSession().getServletContext().getInitParameter("page-size"));
		
		PageModelForm<ComputerLogForm> pmf = this.getAllComputerLogByTime(str, pageNo, pageSize);
		mv.addObject("pageModel", pmf);
		mv.addObject("computerLogs", pmf.getData());
		mv.addObject("queryStr", str);
		mv.setViewName("show_computerlog");
		return mv;
	}
	
	/**
	 * @return
	 */
	@RequestMapping("detail_computer")
	public ModelAndView showLogDetail(@RequestParam int computer_id){
		ModelAndView mv = new ModelAndView();
		mv.addObject("computer", computerService.get(computer_id));
		mv.setViewName("error");
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
	//**********************映射表单类封装*********************
	
	/**
	 * 获取所有电脑日志（按站点）,分页
	 */
	private PageModelForm<ComputerLogForm> getAllComputerLogByName(String queryString,int pageNo,int pageSize){
		PageModel<ComputerLog> pm = computerLogService.getAllSortByComputer(queryString, pageNo, pageSize);
		PageModelForm<ComputerLogForm> pmf = new PageModelForm<ComputerLogForm>();
		
		List<ComputerLog> list = pm.getList();
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
		pmf.setData(computerLogs);
		pmf.setButtomPageNo(pm.getButtomPageNo());
		pmf.setTotalPages(pm.getTotalPages());
		pmf.setPageNo(pageNo);
		pmf.setPageSize(pageSize);
		return pmf;
	}

	
	/**
	 * 获取指定电脑日志,通过站点名字
	 */
	private PageModelForm<ComputerLogForm> getAllComputerLogByTime(String queryString,int pageNo,int pageSize){
		PageModel<ComputerLog> pm = computerLogService.getAll(queryString, pageNo, pageSize);
		PageModelForm<ComputerLogForm> pmf = new PageModelForm<ComputerLogForm>();
		
		List<ComputerLog> list = pm.getList();
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
		pmf.setData(computerLogs);
		pmf.setButtomPageNo(pm.getButtomPageNo());
		pmf.setTotalPages(pm.getTotalPages());
		pmf.setPageNo(pageNo);
		pmf.setPageSize(pageSize);
		return pmf;
	}
	
	/**
	 * 获取指定电脑日志,通过站点名字或id
	 */
	/*
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
	*/
}
