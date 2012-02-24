package cm.commons.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cm.commons.pojos.Port;
import cm.commons.stat.service.PortService;

@Controller
@RequestMapping("port")
public class PortController {

	@Autowired
	private PortService portService;
	
	public ModelAndView addPort(Port port, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()){
			mv.addObject("error", "提交表单失败"+result.getAllErrors());
			mv.setViewName("error");
			return mv;
		}
		portService.saveOrUpdate(port);
		return mv;
	}
	
	public void deletePort(@RequestParam int port_id ,HttpServletRequest request){
		portService.delete(port_id);
	}
	
	public ModelAndView modifyPort(Port port, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		if(result.hasErrors()){
			mv.addObject("error", "提交表单失败"+result.getAllErrors());
			mv.setViewName("error");
			return mv;
		}
		portService.saveOrUpdate(port);
		return mv;
	}
}
