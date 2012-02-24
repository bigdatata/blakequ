package cm.commons.stat.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.exception.AppException;
import cm.commons.pojos.Route;
import cm.commons.pojos.Segment;
import cm.commons.pojos.Station;
import cm.commons.stat.dao.RouteDao;
import cm.commons.stat.dao.SegmentDao;
import cm.commons.stat.dao.StationDao;
import cm.commons.stat.service.StationService;
import cm.commons.util.PageModel;

public class StationServiceImpl implements StationService<Integer, Station> {

	private static Log log = LogFactory.getLog(StationServiceImpl.class);
	private StationDao stationDao;
	private SegmentDao segmentDao;
	private RouteDao routeDao;
	public void setStationDao(StationDao stationDao) {
		this.stationDao = stationDao;
	}

	public void setSegmentDao(SegmentDao segmentDao) {
		this.segmentDao = segmentDao;
	}


	public void setRouteDao(RouteDao routeDao) {
		this.routeDao = routeDao;
	}

	public int getErrorStationCount() {
		// TODO Auto-generated method stub
		log.debug("get error station count "+this.getClass().getName());
		try {
			return stationDao.getErrorStationCount();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get error station count fail! "+this.getClass().getName(), e);
			throw new AppException("获取错误站点个数失败");
		}
	}

	public List<Station> getMainStaion() {
		// TODO Auto-generated method stub
		log.debug("get main station"+this.getClass().getName());
		try {
			return stationDao.getMainStaion();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get main station fail! "+this.getClass().getName(), e);
			throw new AppException("获取主站点失败");
		}
	}

	public int getStationCount() {
		// TODO Auto-generated method stub
		log.debug("get station count "+this.getClass().getName());
		try {
			return stationDao.getStationCount();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get station count fail! "+this.getClass().getName(), e);
			throw new AppException("获取站点数量失败");
		}
	}

	public int getUnknowStationCount() {
		// TODO Auto-generated method stub
		log.debug("get unknow station count "+this.getClass().getName());
		try {
			return stationDao.getUnknowStationCount();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get unknow station count fail! "+this.getClass().getName(), e);
			throw new AppException("获取未知状态站点个数失败");
		}
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			stationDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除数据失败");
		}
	}

	public Station get(Integer id) {
		// TODO Auto-generated method stub
		log.debug("get data "+this.getClass().getName());
		try {
			return (Station) stationDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get data fail! "+this.getClass().getName(), e);
			throw new AppException("获取数据失败");
		}
	}

	public List<Station> getAll() {
		// TODO Auto-generated method stub
		log.debug("get all data "+this.getClass().getName());
		try {
			return stationDao.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data fail! "+this.getClass().getName(), e);
			throw new AppException("获取所有数据失败");
		}
	}

	public void save(Station entity) {
		// TODO Auto-generated method stub
		log.debug("save data "+this.getClass().getName());
		try {
			stationDao.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save data fail!　"+this.getClass().getName(), e);
			throw new AppException("存储数据失败");
		}
	}

	public void saveOrUpdate(Station entity) {
		// TODO Auto-generated method stub
		log.debug("save or update data "+this.getClass().getName());
		try {
			stationDao.saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update data fail!　"+this.getClass().getName(), e);
			throw new AppException("保存或更新数据失败");
		}
	}

	public void update(Station entity) {
		// TODO Auto-generated method stub
		log.debug("update data "+this.getClass().getName());
		try {
			stationDao.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update data fail!　"+this.getClass().getName(), e);
			throw new AppException("更新数据失败");
		}
	}


	public void delete(Station entity) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			stationDao.delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除数据失败");
		}
	}

	public List<Station> getMainStationByRoute(Integer routeId) {
		// TODO Auto-generated method stub
		log.debug("get main station by  route id");
		try {
			return stationDao.getMainStationByRoute(routeId);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get main station by  route id fail! ", e);
			throw new AppException("获取线路id="+routeId+"的主站点失败");
		}
	}

	public void saveStationAndSegment(Station station1,
			Station station2, Station station3, Integer route_id) {
		// TODO Auto-generated method stub
		log.debug("save station and segment");
		try {
			if(station1 != null && station2 != null){
				Segment segment = new Segment();
				segment.setRouteId(route_id);
				segment.setState(0);
				segment.setStationByStation1Id(station1);
				segment.setStationByStation2Id(station3);
				segmentDao.save(segment);
				
				Segment segment2 = new Segment();
				segment2.setRouteId(route_id);
				segment2.setState(0);
				segment2.setStationByStation1Id(station3);
				segment2.setStationByStation2Id(station2);
				segmentDao.save(segment2);
				
			}else if(station1 != null && station2 == null){
				Segment segment = new Segment();
				segment.setRouteId(route_id);
				segment.setState(0);
				segment.setStationByStation1Id(station1);
				segment.setStationByStation2Id(station3);
				segmentDao.save(segment);
			}else if(station1 == null && station2 != null){
				Segment segment = new Segment();
				segment.setRouteId(route_id);
				segment.setState(0);
				segment.setStationByStation1Id(station2);
				segment.setStationByStation2Id(station3);
				segmentDao.save(segment);
			}
			//线路站点数量加1
			Route r = (Route) routeDao.get(route_id);
			System.out.println("****r="+r);
			r.setStationNum(r.getStationNum()+1);
			System.out.println("****r="+r);
			routeDao.update(r);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save station and segment fail! ", e);
			throw new AppException("插入站点失败");
		}
	}

	public Station getStationByName(String name) {
		// TODO Auto-generated method stub
		log.debug("save station and segment");
		try {
			return (Station) stationDao.getStaionByName(name);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save station and segment fail!", e);
			throw new AppException("获取站点为"+name+"的站点失败");
		}
	}

	public List<Station> getStationsNotInSegment() {
		// TODO Auto-generated method stub
		log.debug("get last station not in segment");
		try {
			return stationDao.getStationsNotInSegment();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get last station not in segment fail!", e);
			throw new AppException("获取不在线段上的其他站点失败");
		}
	}

	public List<Station> getAllStationByRoute(int routeId) {
		log.debug("get all station from route "+this.getClass().getName());
		List<Station> list = null;
		try {
			list = stationDao.getAllStationByRoute(routeId);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all station from route fail! "+this.getClass().getName(), e);
			throw new AppException("获取线路id="+routeId+"的所有站点失败");
		}
	}

	public PageModel<Station> getAll(String queryString, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
