package cm.commons.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cm.commons.controller.form.ConfigForm;
import cm.commons.pojos.System;
import cm.commons.sys.service.SystemService;
import cm.commons.util.AlarmUtil;
import cm.commons.util.NullUtil;

/**
 * 系统配置(管理员修改，删除)
 * @author Administrator
 *
 */
@Controller
@RequestMapping("admin/config")
public class SystemConfigController {
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 显示所有配置
	 * @return
	 */
	@RequestMapping("show_config")
	public ModelAndView showAllConfig(){
		ModelAndView mv = new ModelAndView();
		List<System> ls = systemService.getAll();
		List<ConfigForm> lc = new ArrayList<ConfigForm>();
		for(System s:ls){
			ConfigForm cf = new ConfigForm();
			cf.setId(s.getId());
			cf.setKey(s.getConfigKey());
			cf.setValue(s.getConfigValue());
			lc.add(cf);
		}
		mv.addObject("systemConfig", lc);
		mv.setViewName("show_config");
		return mv;
	}
	
	
	/**
	 * 显示修改配置
	 * @return
	 */
	@RequestMapping("show_modify_config")
	public ModelAndView showModifyConfig(@RequestParam int id){
		ModelAndView mv = new ModelAndView();
		System s = (System) systemService.get(id);
		ConfigForm cf = new ConfigForm();
		cf.setId(s.getId());
		cf.setKey(s.getConfigKey());
		cf.setValue(s.getConfigValue());
		mv.addObject("config", cf);
		mv.setViewName("add_config");
		return mv;
	}
	
	/**
	 * 添加或修改配置
	 * @return
	 */
	@RequestMapping("add_modify_config")
	public ModelAndView addOrModifyConfig(ConfigForm form, BindingResult result){
		ModelAndView mv = new ModelAndView();
		//如果是增加id=0,如果是修改id>0
		if(result.hasErrors() && form.getId() != 0){
			String error = "表单提交错误<^@^>:"+result.getAllErrors();
			mv.addObject("error", error);
			mv.setViewName("../public/error");
			return mv;
		}
		System s = new System();
		if(form.getId() != 0){
			s.setId(form.getId());
		}
		s.setConfigKey(form.getKey());
		s.setConfigValue(form.getValue());
		systemService.saveOrUpdate(s);
		return new ModelAndView(new RedirectView("show_config.do"));
	}
	
	/**
	 * 删除配置
	 */
	@RequestMapping("delete_config")
	public ModelAndView deleteConfig(@RequestParam int id){
		systemService.deleteById(id);
		return new ModelAndView(new RedirectView("show_config.do"));
	}
	/**
	 * 设置采集频率
	 */
	@RequestMapping("FrequencySetting")
	
	public String frequencySetting(HttpServletRequest request){
		String key="frequency";
		String value="";
		String newValue=request.getParameter(key);
		if(NullUtil.notNull(newValue)&&Integer.valueOf(newValue)>0){
			
			System system=systemService.getSystemConfigByKey(key);
			system.setConfigValue(newValue);
			systemService.saveOrUpdate(system);
			AlarmUtil.setFrequency(Integer.parseInt(newValue)*2);
			value=newValue;
		}else{
			System system=systemService.getSystemConfigByKey(key);
			if(NullUtil.notNull(system)){
				value=system.getConfigValue();
			}
		}	
		//默认是5分钟 5*60*1000
		if(NullUtil.isNull(value)){
			value="300000";
		}
		request.setAttribute(key, value);
		return "ConfigSetting/FrequencySetting";
		
	}
}
