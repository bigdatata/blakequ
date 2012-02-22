package cm.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cm.commons.pojos.Computer;
import cm.commons.pojos.ComputerLog;
import cm.commons.stat.service.ComputerService;
import cm.commons.stat.service.StationService;
import cm.commons.sys.service.ComputerLogService;

@Controller
@RequestMapping("computer")
public class ComputerController {
	@Autowired
	private StationService stationService;
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private ComputerLogService computerLogService;

	@RequestMapping("show_detail")
	public ModelAndView showComputer(@RequestParam int stationId){
		ModelAndView mv = new ModelAndView();
		Computer computer = (Computer) stationService.get(stationId);
		
		return mv;
	}
	
	public ModelAndView addComputer(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	public ModelAndView deleteComputer(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	public ModelAndView modifyComputer(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}

}
