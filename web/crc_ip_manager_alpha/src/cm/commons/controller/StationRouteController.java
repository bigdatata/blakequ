package cm.commons.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cm.commons.controller.form.AlarmForm;
import cm.commons.controller.form.SegmentForm;
import cm.commons.controller.form.StationForm;
import cm.commons.controller.form.StationInfoForm;
import cm.commons.pojos.Route;
import cm.commons.pojos.Segment;
import cm.commons.pojos.Station;
import cm.commons.stat.service.RouteService;
import cm.commons.stat.service.SegmentService;
import cm.commons.stat.service.StationService;
import cm.commons.sys.service.WarnService;
import cm.commons.util.AlarmUtil;

@Controller
public class StationRouteController {

	@Autowired
	private RouteService routeService;
	@Autowired
	private SegmentService segmentService;
	@Autowired
	private StationService stationService;
	
	/**
	 * 返回绘制线路相关的信息
	 * (List<SegmentForm>, List<StationFrom>, StationInfoForm),告警信息
	 * @return
	 */
	@RequestMapping("main")
	public ModelAndView getRoute(HttpServletRequest request){
		int route_id = 1;//默认线路
		String id = request.getParameter("route_id");
		System.out.println("****id="+id);
		if(id != null || "".equals(id)){
			route_id = Integer.parseInt(id);
		}
		
		request.getSession().setAttribute("current_route_id", route_id);
		Route r = (Route) routeService.get(route_id);
		//如果站点为空，返回
		if(r.getStationNum() == 0){
			ModelAndView mv = new ModelAndView();
			mv.setViewName("error");
			mv.addObject("error", "对不起，该条线路还没有站点！");
			return mv;
		}
		
		//获取所有线段
		List<SegmentForm> segmentForms = getAllSegmentByRoute(route_id);
		
		System.out.println("****当前线路信息**********");
		StationInfoForm sif = new StationInfoForm();
		sif.setRid(route_id);
		sif.setSg(segmentForms.size());//线段数目
		sif.setSt(r.getStationNum());
		sif.setName(r.getName());
		
		System.out.println("****所有线路信息**********");
		List<Route> routes = routeService.getAll();
		
		System.out.println("****设置模型视图**********");
		ModelAndView mv = new ModelAndView();
		Map<String, Object> m = new HashMap<String, Object>();
		List<AlarmForm> list = AlarmUtil.getAllAlarm();
		m.put("all_route", routes);
		m.put("station_info", sif);
		m.put("alarm_list", list);
		m.put("segment_list", segmentForms);
		m.put("station_list", getStationFromRoute(route_id));
		mv.addAllObjects(m);
		mv.setViewName("StationMonitor/StationMonitor");
		return mv;
	}
	
	/**
	 * 站点信息
	 * @return
	 */
	@RequestMapping("detail_station")
	public ModelAndView showStation(@RequestParam int station_id){
		ModelAndView mv = new ModelAndView();
		Station s = (Station) stationService.get(station_id);
		mv.addObject("station", s);
		mv.setViewName("StationMonitor/StationInfo");
		return mv;
	}
	
	/**
	 * 线段信息
	 * @param segment_id
	 * @return
	 */
	@RequestMapping("detail_segment")
	public ModelAndView showSegment(@RequestParam int segment_id){
		ModelAndView mv = new ModelAndView();
		Segment s = (Segment) segmentService.get(segment_id);
		mv.addObject("segment", s);
		mv.setViewName("error");
		return mv;
	}
	
	/**
	 * 添加线路
	 * @return
	 */
	@RequestMapping("admin/add_route")
	public ModelAndView addRoute(@RequestParam String name, HttpServletRequest request){
		Integer route_id = (Integer) request.getSession().getAttribute("current_route_id");
		if(route_id == null){
			String error = "显示页面失败！添加线路失败";
			ModelAndView mv = new ModelAndView();
			mv.addObject("error", error);
			mv.setViewName("error");
			return mv;
		}
		Route r = new Route();
		r.setName(name);
		r.setStationNum(0);
		routeService.save(r);
		return new ModelAndView(new RedirectView("../main.do?route_id="+route_id));
	}
	
	/**
	 * 增加站点(管理员)
	 * @return
	 */
	@RequestMapping("admin/add_station")
	public ModelAndView addStation(StationForm stationForm, BindingResult result, HttpServletRequest request){
		Integer route_id = (Integer) request.getSession().getAttribute("current_route_id");
		if(result.hasErrors() || route_id == null){
			String error = "表单提交错误<^@^>:"+result.getAllErrors();
			if(route_id == null) error = "你没有选择添加站点的线路！添加站点失败";
			ModelAndView mv = new ModelAndView();
			mv.addObject("error", error);
			mv.setViewName("error");
			return mv;
		}
		
		int select1 = Integer.parseInt((String)stationForm.getStation1().iterator().next());
		int select2 = Integer.parseInt((String)stationForm.getStation2().iterator().next());
		//如果select1,2=-1表示没有上行或下行线路
		Station station1 = null;
		Station station2 = null;
		if(select1 != -1){
			station1 = (Station) stationService.get(select1);
		}
		if(select2 != -1){
			station2 = (Station) stationService.get(select2);
		}
		
		
		Station s = new Station();
		String name = stationForm.getName();
		//如果是主站点就加TDCS
		if(stationForm.getIsMainStation()){
			name = "TDCS"+name;
		}
		s.setName(name);
		s.setSegmentNum(2);
		s.setState(0);
		s.setX(stationForm.getX()+"");
		s.setY(stationForm.getY()+"");
		stationService.saveOrUpdate(s);
		Station station3 = (Station) stationService.getStationByName(stationForm.getName());
		stationService.saveStationAndSegment(station1, station2, station3, route_id);
		return new ModelAndView(new RedirectView("../main.do?route_id="+route_id));
	}
	
	/**
	 * 删除站点(管理员)
	 * 删除站点需要将该条线路重新导入，实际上是删除该条线路的所有数据并重新配置
	 * 如果该站点关联两条线路，则两条线路都要重新导入
	 * @return
	 */
	@RequestMapping("admin/delete_station")
	public ModelAndView deleteStation(@RequestParam int station_id){
		ModelAndView mv = new ModelAndView();
		//在确定删除的时候，只有当验证导入数据合法后才能删除原来线路的数据
		//注：要将该条线路站点置为0
		return mv;
	}
	
	
	/**
	 * 显示修改站点
	 * @param station_id
	 * @return
	 */
	@RequestMapping("show_modify_station")
	public ModelAndView showModifyStation(@RequestParam int station_id){
		ModelAndView mv = new ModelAndView();
		Station s = (Station) stationService.get(station_id);
		StationForm sf = new StationForm();
		sf.setId(s.getId());
		String name = s.getName();
		if(name.startsWith("TDCS")){
			sf.setIsMainStation(true);
		}else{
			sf.setIsMainStation(false);
		}
		sf.setName(name);
		sf.setStation1(s.getSegmentsForStation1Id());
		sf.setStation2(s.getSegmentsForStation2Id());
		sf.setX(Double.parseDouble(s.getX()));
		sf.setY(Double.parseDouble(s.getY()));
		mv.addObject("station", sf);
		mv.setViewName("station_modify");
		return mv;
	}
	
	/**
	 * 修改站点(管理员)
	 * 只能修改x,y,name
	 * @param station
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping("admin/modify_station")
	public ModelAndView modeifyStation(StationForm station, BindingResult result, HttpServletRequest request){
		Integer route_id = (Integer) request.getSession().getAttribute("current_route_id");
		if(result.hasErrors() || route_id == null){
			String error = "表单提交错误<^@^>:"+result.getAllErrors();
			if(route_id == null) error = "修改站点失败,线路id=null";
			ModelAndView mv = new ModelAndView();
			mv.addObject("error", error);
			mv.setViewName("error");
			return mv;
		}
		Station s = (Station) stationService.get(station.getId());
		s.setName(station.getName());
		s.setX(station.getX()+"");
		s.setY(station.getY()+"");
		stationService.update(s);
		return new ModelAndView(new RedirectView("../main.do?route_id="+route_id));
	}
	
	
	/**
	 * 获取线路所有站点
	 * @param route_id
	 * @return
	 */
	@RequestMapping("route_station")
	public ModelAndView getStationsByRoute(HttpServletRequest request){
		int route_id = Integer.parseInt(request.getParameter("route_id"));
		request.getSession().setAttribute("current_route_id", route_id);
		ModelAndView mv = new ModelAndView();
		List<Station> stations = stationService.getAllStationByRoute(route_id);
		//单独站点
		List<Station> lastStations = stationService.getStationsNotInSegment();
		if(lastStations != null){
			stations.addAll(lastStations);
		}
		List<Route> routes = routeService.getAll();

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("routes", routes);
		model.put("stations", stations);
		mv.addAllObjects(model);
		mv.setViewName("station_add");
		return mv;
	}
	
	/**
	 * 显示增加页面
	 * @param request
	 * @return
	 */
	@RequestMapping("show_add_station")
	public ModelAndView showAddStation(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		List<Route> routes = routeService.getAll();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("routes", routes);
		mv.addAllObjects(model);
		mv.setViewName("station_add");
		return mv;
	}
	
	/**
	 * 获取指定线路上的所有车站，主站点放在首位(TDCS)
	 * @param route_id
	 * @return
	 */
	private List<StationForm> getStationFromRoute(int route_id){
		LinkedList<StationForm> stationFroms = new LinkedList<StationForm>();
		//主站点
//		List<Station> mstation = stationService.getMainStationByRoute(route_id);
		//在线路上的站点
		List<Station> stations = stationService.getAllStationByRoute(route_id);
		//单独站点
		List<Station> lastStations = stationService.getStationsNotInSegment();
		if(stations != null){
			if(lastStations != null){
				stations.addAll(lastStations);
			}
			for(int i=0; i<stations.size(); i++){
				StationForm sf = new StationForm();
				sf.setId(stations.get(i).getId());
				sf.setName(stations.get(i).getName());
				sf.setX(Double.parseDouble(stations.get(i).getX()));
				sf.setY(Double.parseDouble(stations.get(i).getY()));
				stationFroms.add(sf);
			}
		}
		return stationFroms;
	}
	
	/**
	 * 获取所有的线段信息
	 * @param route_id
	 * @return
	 */
	private List<SegmentForm> getAllSegmentByRoute(int route_id){
		List<Segment> segments = segmentService.getAllSegmentByRoute(route_id);
		List<SegmentForm> segmentForms = new ArrayList<SegmentForm>();
		if(segments != null){
			for(Segment s:segments){
				SegmentForm sf = new SegmentForm();
				sf.setId(s.getId());
				Station station1 = s.getStationByStation1Id();
				Station station2 = s.getStationByStation2Id();
				if(station1 != null){
					sf.setStartX(Double.parseDouble(station1.getX()));
					sf.setStartY(Double.parseDouble(station1.getY()));
				}
				if(station2 != null){
					sf.setEndX(Double.parseDouble(station2.getX()));
					sf.setEndY(Double.parseDouble(station2.getY()));
				}
				segmentForms.add(sf);
			}
		}
		return segmentForms;
	}
}
