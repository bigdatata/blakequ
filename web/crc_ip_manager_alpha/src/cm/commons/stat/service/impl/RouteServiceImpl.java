package cm.commons.stat.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.exception.AppException;
import cm.commons.pojos.Route;
import cm.commons.stat.dao.RouteDao;
import cm.commons.stat.service.RouteService;
import cm.commons.util.PageModel;

public class RouteServiceImpl implements RouteService<Integer, Route> {

	private static Log log = LogFactory.getLog(RouteServiceImpl.class);
	private RouteDao routeDao;
	
	
	public void setRouteDao(RouteDao routeDao) {
		this.routeDao = routeDao;
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			routeDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName());
			throw new AppException("删除线路失败");
		}
	}

	public Route get(Integer id) {
		// TODO Auto-generated method stub
		log.debug("get data "+this.getClass().getName());
		try {
			return (Route) routeDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get data fail! "+this.getClass().getName());
			throw new AppException("获取线路ID="+id+"失败");
		}
	}

	public List<Route> getAll() {
		// TODO Auto-generated method stub
		log.debug("get all data "+this.getClass().getName());
		try {
			return routeDao.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data fail! "+this.getClass().getName());
			throw new AppException("获取所有线路失败");
		}
	}

	public void save(Route entity) {
		// TODO Auto-generated method stub
		log.debug("save data "+this.getClass().getName());
		try {
			routeDao.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save data fail! "+this.getClass().getName());
			throw new AppException("存储线路失败");
		}
	}

	public void saveOrUpdate(Route entity) {
		// TODO Auto-generated method stub
		log.debug("save or update "+this.getClass().getName());
		try {
			routeDao.saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update fail! "+this.getClass().getName());
			throw new AppException("存储或更新线路失败");
		}
	}

	public void update(Route entity) {
		// TODO Auto-generated method stub
		log.debug("update data "+this.getClass().getName());
		try {
			routeDao.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update data fail! "+this.getClass().getName());
			throw new AppException("更新线路失败");
		}
	}

	public void delete(Route entity) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			routeDao.delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName());
			throw new AppException("删除线路失败");
		}
	}

	public PageModel<Route> getAll(String queryString, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
