package cm.commons.controller;

import java.util.List;

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
	
	@Autowired
	private ComputerLogService computerLogService;

	@RequestMapping("show_computer_and_computerlog_detail")
	public ModelAndView showComputer(@RequestParam int stationId){
		ModelAndView mv = new ModelAndView();
		System.out.println("*******id:"+stationId);
		Computer computer = (Computer) computerService.getComputerByStationId(stationId);
		List<ComputerLog> computerlog_list = (List<ComputerLog>) computerLogService.getComputerLog(computer.getId());
		ComputerLog computerlog = (ComputerLog)computerlog_list.get(0);
		ComputerForm cf = new ComputerForm();
		ComputerLogForm clf = new ComputerLogForm();
		if(computer != null){
			cf.setId(computer.getId());
			cf.setIp(computer.getIp());
			cf.setOs(computer.getOs());
			cf.setState(computer.getState());
			cf.setStationId(computer.getStation().getId());
		}
		if(computerlog != null){
			clf.setId(computerlog.getId());
			clf.setCupRate(computerlog.getCupRate());
			clf.setMemRate(computerlog.getMemRate());
		}
		mv.addObject("computer", cf);
		mv.addObject("computerlog",clf);
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

}
