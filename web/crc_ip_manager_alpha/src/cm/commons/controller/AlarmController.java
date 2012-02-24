package cm.commons.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cm.commons.controller.form.AlarmForm;
import cm.commons.controller.form.PageModelForm;
import cm.commons.pojos.Warn;
import cm.commons.sys.service.WarnService;
import cm.commons.util.PageModel;

/**
 * 告警控制器(会定时从数据库读取状态)
 * @author Administrator
 */
@Controller
@RequestMapping("alarm")
public class AlarmController {
	
	@Autowired
	private WarnService warnService;

	@RequestMapping("get_all_by_page")
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
		mv.addObject("computerLogs", pmf.getData());
		mv.setViewName("show_alarm");
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
				af.setStation_id(w.getStationId());
//				af.setSegment_id(segmentId)*************************************88
				af.setInfo(w.getWarncontent());
				af.setTime(w.getWarntime());
				af.setState(w.getWarnstate());
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
