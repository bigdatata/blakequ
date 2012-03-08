package cm.commons.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import cm.commons.controller.form.AlarmForm;
import cm.commons.controller.form.PageModelForm;
import cm.commons.pojos.Station;
import cm.commons.pojos.Warn;
import cm.commons.stat.service.StationService;
import cm.commons.sys.service.WarnService;
import cm.commons.util.AlarmUtil;
import cm.commons.util.NullUtil;
import cm.commons.util.PageModel;
import cm.commons.util.StationStateCheckTask;

/**
 * 告警控制器(会定时从数据库读取状态)
 * @author Administrator
 */
@Controller
@RequestMapping("alarm")
public class AlarmController {
	
	@Autowired
	private WarnService warnService;
	@Autowired
	private StationService stationService;
	
	/**
	 * 删除告警
	 * @return
	 */
	@RequestMapping("admin/delete_alarm_by_ids")
	public ModelAndView deleteAlarm(@RequestParam String alarmIds){
		ModelAndView mv = new ModelAndView();
		if(NullUtil.notNull(alarmIds)){
			for(String id:alarmIds.split(",")){
				if(NullUtil.notNull(id)){
					warnService.deleteById(Integer.valueOf(id));
				}
			}
		}
		mv.setView(new RedirectView("../get_log_by_page.do?pageNo=1&queryString="));
		return mv;
	}
	
	/**
	 * 获取当前的告警信息,有告警则会跳转到主页面刷新视图
	 * 没有则什么都不做
	 * @return
	 */
	@RequestMapping("get_current_warn")
	public ModelAndView getCurrentLog(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		List<AlarmForm> list = AlarmUtil.getAllAlarm();
		if(list != null && list.size() > 0){
//			mv.addObject("alarm_list", list);
			//默认只是跳转到第一个含有告警的线路上
			View view = new RedirectView("../main.do?route_id="+list.get(0).getStation_id());
			mv.setView(view);
		}else{
			View view = new RedirectView("../main.do");//无告警信息，默认首页
			mv.setView(view);
		}
		return mv;
	}

	/**
	 * 这是获取的历史告警日志记录
	 * @param searchStr
	 * @param pageNo
	 * @param queryString
	 * @param request
	 * @return
	 */
	@RequestMapping("get_log_by_page")
	public ModelAndView getAllbyPage(String searchStr, @RequestParam int pageNo, @RequestParam String queryString, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String str = "";
		if(searchStr == null || searchStr.equals("")){
			str = (queryString == null || queryString.equals(""))?"":queryString;
		}else{
			str = searchStr;
		}
		//从web.xml的配置中配置页面大小
		int pageSize = Integer.parseInt(request.getSession().getServletContext().getInitParameter("page-size"));
		
		PageModelForm<AlarmForm> pmf = this.getAllByPage(queryString, pageNo, pageSize);
		mv.addObject("pageModel", pmf);
		mv.addObject("queryStr", str);
		mv.addObject("alarm_list", pmf.getData());
		mv.setViewName("AlarmManage/AlarmManage");
		return mv;
	}
	
	private PageModelForm<AlarmForm> getAllByPage(String queryString,int pageNo,int pageSize){
		PageModel pm = warnService.getAll(queryString, pageNo, pageSize);
		PageModelForm<AlarmForm> pmf = new PageModelForm<AlarmForm>();
		List<Warn> list = pm.getList();
		List<AlarmForm> lists = new ArrayList<AlarmForm>();
		if(list != null){
			for(Warn w:list){
				AlarmForm af = new AlarmForm();
				af.setId(w.getId());
				af.setStation_id(w.getStationId());
				af.setSegment_id(0);
				af.setInfo(w.getWarncontent());
				af.setTime(w.getWarntime());
				af.setState(w.getWarnstate());
				Station s = (Station)stationService.get(w.getStationId());
				if(s != null){
					String name = s.getName();
					af.setStationName(name);
				}
				List<AlarmForm> aff = AlarmUtil.getAllAlarm();
				for(AlarmForm a: aff){
					if(w.getStationId() == 0 && w.getWarncontent().equals(a.getInfo())){
						af.setSg1_name(a.getSg1_name());
						af.setSg1(a.getSg1());
						af.setSg2(a.getSg2());
						af.setSg2_name(a.getSg2_name());
						af.setSegment_id(a.getSegment_id());
					}
					System.out.println(a);
				}
				lists.add(af);
			}
		}
		pmf.setData(lists);
		pmf.setButtomPageNo(pm.getButtomPageNo());
		pmf.setTotalPages(pm.getTotalPages());
		pmf.setPageNo(pageNo);
		pmf.setPageSize(pageSize);
		return pmf;
	}
}
