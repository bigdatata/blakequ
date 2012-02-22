package cm.commons.controller;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cm.commons.pojos.Router;
import cm.commons.pojos.RouterLog;
import cm.commons.stat.service.PortService;
import cm.commons.stat.service.RouterService;
import cm.commons.sys.service.RouterLogService;

@Controller
@RequestMapping("router")
public class RouterController {
	
	@Autowired
	private PortService portService;
	@Autowired
	private RouterService routerService;
	@Autowired
	private RouterLogService routerLogService;
	
	@RequestMapping("show_all")
	public ModelAndView showAllComputer(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	@RequestMapping("")
	public ModelAndView addOrModifyRouter(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	public ModelAndView deleteRouter(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
}
