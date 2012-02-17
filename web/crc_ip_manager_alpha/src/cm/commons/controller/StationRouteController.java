package cm.commons.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cm.commons.controller.form.StationForm;
import cm.commons.controller.form.StationInfoForm;
import cm.commons.pojos.Route;
import cm.commons.pojos.Station;
import cm.commons.stat.service.RouteService;
import cm.commons.stat.service.SegmentService;
import cm.commons.stat.service.StationService;
import cm.commons.sys.service.WarnService;

@Controller
@RequestMapping("route")
public class StationRouteController {

	@Autowired
	private RouteService routeService;
	@Autowired
	private SegmentService segmentService;
	@Autowired
	private StationService stationService;
	@Autowired
	private WarnService warnService;
	
	/**
	 * 获取线路信息
	 * @return
	 */
	@RequestMapping("route_id")
	public List<Route> getRouteInfo(){
		return segmentService.getAll();
	}
	
	/**
	 * 返回绘制线路相关的信息(List<SegmentForm>, List<StationFrom>, StationInfoForm)
	 * @return
	 */
	@RequestMapping("route/{route_id}")
	public ModelAndView getRoute(@PathVariable("route_id") int id){
		//先获取线路信息
		Route r = (Route) routeService.get(id);
		StationInfoForm sif = new StationInfoForm();
		sif.setRid(id);
//		sif.setSg(sg);//线段数目
		sif.setSt(r.getStationNum());
		
		//车站列表
//		List<StationForm> 
//		StationForm sf = new StationForm();
		
		//获取所有线路上的所有车站，主站点放在首位(TDCS)
		List<Station> routeStations = new ArrayList<Station>();
		Station mstaion = (Station) stationService.getMainStaion();
		if(mstaion != null){
			routeStations.add(mstaion);
		}
		
		
		
		
		ModelAndView mv = new ModelAndView();
		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("station_info", sif);
//		m.put("segment_list", value);
//		m.put("station_list", value);
		mv.addAllObjects(m);
		mv.setViewName("main");
		return mv;
	}
	
	
}
