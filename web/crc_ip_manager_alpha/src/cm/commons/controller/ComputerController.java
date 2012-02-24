package cm.commons.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cm.commons.controller.form.ComputerForm;
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

	@RequestMapping("show_detail")
	public ModelAndView showComputer(@RequestParam int stationId, int station_id){
		ModelAndView mv = new ModelAndView();
		int id = stationId>0?stationId:station_id;
		System.out.println("*******id:"+id);
		Computer computer = (Computer) computerService.getComputerByStationId(id);
		ComputerForm cf = new ComputerForm();
		cf.setId(computer.getId());
		cf.setIp(computer.getIp());
		cf.setOs(computer.getOs());
		cf.setState(computer.getState());
		cf.setStationId(computer.getStation().getId());
		mv.addObject("computer", cf);
		mv.setViewName("show_computer");
		return mv;
	}
	
	@RequestMapping("add_computer")
	public ModelAndView addComputer(ComputerForm computerForm, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()){
			mv.addObject("error", "提交表单失败"+result.getAllErrors());
			mv.setViewName("error");
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
	
	@RequestMapping("delete_computer")
	public void deleteComputer(@RequestParam int computer_id ,HttpServletRequest request){
//		ModelAndView mv = new ModelAndView();
		computerService.deleteById(computer_id);
//		return mv;
	}
	
	@RequestMapping("modify_computer")
	public ModelAndView modifyComputer(ComputerForm computerForm, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()){
			mv.addObject("error", "提交表单失败"+result.getAllErrors());
			mv.setViewName("error");
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
