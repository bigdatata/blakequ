package cm.commons.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cm.commons.controller.form.ComputerForm;
import cm.commons.controller.form.ComputerLogForm;
import cm.commons.pojos.Computer;
import cm.commons.pojos.ComputerLog;
import cm.commons.pojos.Station;
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

	@RequestMapping("show_computer_and_computerlog_detail")
	public ModelAndView showComputer(@RequestParam int stationId){
		ModelAndView mv = new ModelAndView();
		System.out.println("*******id:"+stationId);
		ComputerForm cf = this.getComputer(stationId);
		mv.addObject("computer", cf);
		mv.addObject("computerlog",cf.getClf());
		mv.setViewName("StationMonitor/StationInfo");
		return mv;
	}
	
	@RequestMapping("add_computer")
	public ModelAndView addComputer(ComputerForm computerForm, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()){
			mv.addObject("error", "提交表单失败"+result.getAllErrors());
			mv.setViewName("../public/error");
			return mv;
		}
		Computer computer = new Computer();
		computer.setId(computerForm.getId());
		computer.setIp(computerForm.getIp());
		computer.setOs(computerForm.getOs());
		computer.setState(computerForm.getState());
		computer.setStation((Station)stationService.get(computerForm.getStationId()));
		computerService.saveOrUpdate(computer);
		return mv;
	}
	
	@RequestMapping("admin/delete_computer")
	public void deleteComputer(@RequestParam int computer_id ,HttpServletRequest request){
//		ModelAndView mv = new ModelAndView();
		computerService.deleteById(computer_id);
//		return mv;
	}
	
	@RequestMapping("admin/modify_computer")
	public ModelAndView modifyComputer(ComputerForm computerForm, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()){
			mv.addObject("error", "提交表单失败"+result.getAllErrors());
			mv.setViewName("../public/error");
			return mv;
		}
		Computer computer = (Computer) computerService.get(computerForm.getId());
		computer.setId(computerForm.getId());
		computer.setIp(computerForm.getIp());
		computer.setOs(computerForm.getOs());
		computer.setState(computerForm.getState());
		computerService.saveOrUpdate(computer);
		return mv;
	}
	
	/**
	 * 返回computer
	 * @param stationId
	 * @return
	 */
	private ComputerForm getComputer(int stationId){
		Computer computer = (Computer) computerService.getComputerByStationId(stationId);
		ComputerForm cf = new ComputerForm();
		if(computer != null){
			cf.setId(computer.getId());
			cf.setIp(computer.getIp());
			cf.setOs(computer.getOs());
			cf.setState(computer.getState());
			cf.setStationId(computer.getStation().getId());
			Set<ComputerLog> set = computer.getComputerLogs();
			if(set != null && set.size()>0){
					Iterator i = set.iterator();
					ComputerLog cl = (ComputerLog) i.next();
					ComputerLogForm clf = new ComputerLogForm();
					clf.setCupRate(cl.getCupRate());
					clf.setCurrTime(cl.getCurrTime());
					clf.setId(cl.getId());
					clf.setMemRate(cl.getMemRate());
					cf.setClf(clf);
			}
		}
		return cf;
	}

}
