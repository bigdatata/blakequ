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

import cm.commons.controller.form.PageModelForm;
import cm.commons.controller.form.PortForm;
import cm.commons.pojos.Port;
import cm.commons.pojos.Router;
import cm.commons.stat.service.PortService;
import cm.commons.util.NullUtil;
import cm.commons.util.PageModel;

@Controller
@RequestMapping("port")
public class PortController {
	@Autowired
	private PortService portService;
	
	@RequestMapping("admin/delete")
	public void deletePort(@RequestParam int port_id ,HttpServletRequest request){
		portService.delete(port_id);
	}
	
	
	/**
	 * 分页获取路由日志
	 * @param pageNo 页面(1,2...)
	 * @param routerId
	 * @param request 路由器id
	 * @return
	 */
	@RequestMapping("get_port_by_page")
	public ModelAndView getPortsByPage(@RequestParam int pageNo, @RequestParam Integer routerId, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		int pageSize = Integer.parseInt(request.getSession().getServletContext().getInitParameter("page-size"));
		PageModelForm<PortForm> pmf = this.getPortsByPage(routerId, pageNo, pageSize);
		mv.addObject("ports", pmf.getData());
		mv.addObject("pageModel",pmf);
		mv.addObject("queryStr", routerId);
		mv.setViewName("ShowComputer/ShowPortLog");
		return mv;
	}

	/**
	 * 删除告警
	 * @return
	 */
	@RequestMapping("admin/delete_port_log_by_ids")
	public ModelAndView deletePort(@RequestParam String portLogIds){
		ModelAndView mv = new ModelAndView();
		if(NullUtil.notNull(portLogIds)){
			for(String id:portLogIds.split(",")){
				if(NullUtil.notNull(id)){
					portService.deleteById(Integer.valueOf(id));
				}
			}
		}
		mv.setView(new RedirectView("../get_port_by_page.do?pageNo=1&routerId="));
		return mv;
	}
	private PageModelForm<PortForm> getPortsByPage(Integer routerId,
			int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		PageModel<Port> pm = portService.getPortsByRouter(routerId, pageNo, pageSize);
		PageModelForm<PortForm> pmf = new PageModelForm<PortForm>();
		
		List<Port> list = pm.getList();
		List<PortForm> listForm = new ArrayList<PortForm>();
		if(list != null){
			for(Port p : list){
				PortForm pf = new PortForm();
				pf.setGetTime(p.getGetTime());
				pf.setId(p.getId());
				pf.setIfDescr(p.getIfDescr());
				pf.setIfInOctets(p.getIfInOctets());
				pf.setIfOutOctets(p.getIfOutOctets());
				String state = p.getIfOperStatus().toString();
				if(state.startsWith("1")){
					pf.setIfOperStatus("开启状态");
				}else if(state.startsWith("2")){
					pf.setIfOperStatus("关闭状态");
				}
				pf.setLocIfInBitsSec(p.getLocIfInBitsSec());
				pf.setLocIfInCrc(p.getLocIfInCrc());
				pf.setLocIfOutBitsSec(p.getLocIfOutBitsSec());
				pf.setPortIp(p.getPortIp());
				Router r = p.getRouter();
				if(r != null){
					pf.setStationName(r.getStation().getName());
				}else{
					pf.setStationName("");
				}
				listForm.add(pf);
			}
		}
		pmf.setData(listForm);
		pmf.setButtomPageNo(pm.getButtomPageNo());
		pmf.setPageNo(pageNo);
		pmf.setPageSize(pageSize);
		pmf.setTotalPages(pm.getTotalPages());
		return pmf;
	}
	

}
