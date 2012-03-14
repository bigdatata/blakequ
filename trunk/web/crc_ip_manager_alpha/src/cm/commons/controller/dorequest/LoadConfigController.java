package cm.commons.controller.dorequest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cm.commons.config.form.RouteParseForm;
import cm.commons.config.form.SegmentParseForm;
import cm.commons.config.form.StationParseForm;
import cm.commons.config.parse.ParseAllData;
import cm.commons.config.parse.RouteParse;
import cm.commons.config.parse.SegmentParse;
import cm.commons.config.parse.StationParse;
import cm.commons.pojos.Route;
import cm.commons.pojos.Segment;
import cm.commons.pojos.Station;
import cm.commons.stat.service.RouteService;
import cm.commons.stat.service.SegmentService;
import cm.commons.stat.service.StationService;
import cm.commons.util.FileUtil;

/**
 * 请求导入配置文件
 * @author Administrator
 *
 */
@Controller
public class LoadConfigController {

	private static final String SEGMENT = "segment.txt";
	private static final String STATION = "station.txt";
	@Autowired
	private StationService stationService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private SegmentService segmentService;
	
	/**
	 * 初始化配置
	 * @param path
	 * @return
	 */
	@RequestMapping("admin/load_config")
	public ModelAndView loadConfig(@RequestParam String path){
		ModelAndView mv = new ModelAndView();
		ParseAllData pd = new ParseAllData(path);
		if(!pd.checkConfigFile(path)){
			return this.checkError();
		}
		try {
			System.out.println("*********save route***********");
			//存储线路
			RouteParse routeParse = pd.parserRouteConfig();
			this.saveRouteToDB(routeParse.getRouteList());
			System.out.println("*********save station***********");
			//存储车站
			Map<String, StationParse> map2 = pd.parserStationConfig();
			Iterator ii = map2.keySet().iterator();
			while(ii.hasNext()){
				String key = (String) ii.next();
				StationParse stationParse = map2.get(key);
				this.saveStationToDB(stationParse);
			}
			System.out.println("*********save segment***********");
			//存储线段
			Map<String, SegmentParse> map1 = pd.parserSegmentConfig();
			Iterator i = map1.keySet().iterator();
			while(i.hasNext()){
				String key = (String) i.next();
				SegmentParse segmentParse = map1.get(key);
				this.saveSegmentToDB(segmentParse);
			}
			//删除冗余站点
			List<Station> listStation = stationService.getStationsNotInSegment();
			if(listStation != null && listStation.size()>0){
				for(Station s:listStation){
					stationService.delete(s);
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return this.checkError();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return this.checkError();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return this.checkError();
		}
		mv.addObject("error", "配置文件导入成功");
		mv.setViewName("../public/error");
		return mv;
	}
	

	/**
	 * 修改线路时，需要重新导入该线路的两个配置文件
	 * station.txt 和  segment.txt
	 * 将这两个文件放入一文件夹
	 * @param path 文件夹路径
	 * @return
	 */
	@RequestMapping("admin/modify_route")
	public ModelAndView modifyRoute(@RequestParam String path){
		ModelAndView mv = new ModelAndView();
		Map<String, String> ssMap = FileUtil.readFileFromDirectory(path, new String[]{SEGMENT, STATION});
		if(ssMap == null || ssMap.size() != 2) return checkError();
		try {
			StationParse stationParse = new StationParse();
			stationParse.parseJson(ssMap.get(STATION));
			Route route = (Route) routeService.getRouteByName(stationParse.getRoute());
			List<Station> stations  = stationService.getAllStationByRoute(route.getId());
			if(stations != null && stations.size()>0){
				for(Station station:stations){
					stationService.delete(station);
				}
			}
			this.saveStationToDB(stationParse);
			
			SegmentParse segmentParse = new SegmentParse();
			segmentParse.parseJson(ssMap.get(SEGMENT));
			this.saveSegmentToDB(segmentParse);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return checkError();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return checkError();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return checkError();
		}
		mv.addObject("error", "修改线路成功");
		mv.setViewName("../public/error");
		return mv;
	}
	
	private ModelAndView checkError(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("error", "配置文件格式或路径有误，请重新检查配置文件！");
		mv.setViewName("../public/error");
		return mv;
	}
	
	/**
	 * 将线路存储到数据库
	 * @param routes
	 */
	private void saveRouteToDB(List<RouteParseForm> routes) {
		// TODO Auto-generated method stub
		if(routes != null){
			for(RouteParseForm rf: routes){
				Route route = (Route) routeService.getRouteByName(rf.getName());
				if(route == null){
					route = new Route();
				}
				route.setName(rf.getName());
				String num = rf.getStationNum();
				if(num != null){
					int n = Integer.parseInt(num);
					route.setStationNum(n);
				}
				routeService.saveOrUpdate(route);
			}
		}
	}
	
	/**
	 * 将车站存储到数据库
	 * @param stationParse
	 */
	private void saveStationToDB(StationParse stationParse){
		List<StationParseForm> list = stationParse.getStationList();
		for(StationParseForm spf:list){
			Station station = (Station) stationService.getStationByName(spf.getName());
			if(station == null){
				station = new Station();
			}
			
			station.setName(spf.getName());
			String num = spf.getSegNum();
			if(num != null){
				int n = Integer.parseInt(num);
				station.setSegmentNum(n);
			}
			station.setState(0);
			station.setX(spf.getX());
			station.setY(spf.getY());
			station.setIsMainStation(Boolean.valueOf(spf.getIsMainStation()));
			stationService.saveOrUpdate(station);
		}
	}
	
	/**
	 * 将线段存储到数据库
	 * @param segmentParse
	 */
	private void saveSegmentToDB(SegmentParse segmentParse){
		List<SegmentParseForm> list = segmentParse.getSegmentList();
		for(SegmentParseForm spf:list){
			Segment segment = null;
			Route route = (Route) routeService.getRouteByName(segmentParse.getRoute());
			//判断是不是有该线段
			if(route != null){
				List<Segment> segments = segmentService.getAllSegmentByRoute(route.getId());
				if(segments != null){
					for(Segment s:segments){
						Station s1 = s.getStationByStation1Id();
						Station s2 = s.getStationByStation2Id();
						String ss1 = null;
						if(s1 != null){
							ss1 = s1.getName();
						}
						String ss2 = null;
						if(s2 != null){
							ss2 = s2.getName();
						}
						if(ss1 != null && ss2 != null){
							if((ss1.equals(spf.getStationDown()) && ss2.equals(spf.getStationUp()))|| (ss1.equals(spf.getStationUp()) && ss2.equals(spf.getStationDown()))){
								segment = s;
							}
						}
					}
				}
			}
			if(segment == null){
				segment = new Segment();
			}
			if(route != null){
				segment.setRouteId(route.getId());
			}
			segment.setState(0);
			Station s1 = (Station) stationService.getStationByName(spf.getStationUp());
			Station s2 = (Station) stationService.getStationByName(spf.getStationDown());
			if(s1 != null){
				segment.setStationByStation1Id(s1);
			}
			if(s2 != null){
				segment.setStationByStation2Id(s2);
			}
			segmentService.saveOrUpdate(segment);
		}
	}
}
