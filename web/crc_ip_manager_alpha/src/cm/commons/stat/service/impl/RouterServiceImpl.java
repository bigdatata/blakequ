package cm.commons.stat.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cm.commons.exception.AppException;
import cm.commons.pojos.Router;
import cm.commons.stat.dao.RouterDao;
import cm.commons.stat.service.RouterService;
import cm.commons.util.PageModel;

public class RouterServiceImpl implements RouterService<Integer, Router>{

	private static Log log = LogFactory.getLog(RouterServiceImpl.class);
	@Autowired
	private RouterDao routerDao;

	public void deleteRouterByIp(String ip) {
		// TODO Auto-generated method stub
		log.debug("delete router by ip "+this.getClass().getName());
		try {
			routerDao.deleteRouterByIp(ip);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete router by ip fail! "+this.getClass().getName(), e);
			throw new AppException("删除IP="+ip+"的路由失败");
		}
	}

	public Router getRouterByIp(String ip) {
		// TODO Auto-generated method stub
		log.debug("get router by ip "+this.getClass().getName());
		try {
			return routerDao.getRouterByIp(ip);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get router by ip fail! "+this.getClass().getName(), e);
			throw new AppException("获取IP="+ip+"的路由失败");
		}
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			routerDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除路由失败");
		}
	}

	public Router get(Integer id) {
		// TODO Auto-generated method stub
		log.debug("get data "+this.getClass().getName());
		try {
			return (Router) routerDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get data fail! "+this.getClass().getName(), e);
			throw new AppException("获取路由失败");
		}
	}

	public List<Router> getAll() {
		// TODO Auto-generated method stub
		log.debug("get all data "+this.getClass().getName());
		try {
			return routerDao.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data fail! "+this.getClass().getName(), e);
			throw new AppException("获取所有路由失败");
		}
	}

	public void save(Router entity) {
		// TODO Auto-generated method stub
		log.debug("save data "+this.getClass().getName());
		try {
			routerDao.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save data fail! "+this.getClass().getName(), e);
			throw new AppException("保存路由信息失败");
		}
	}

	public void saveOrUpdate(Router entity) {
		// TODO Auto-generated method stub
		log.debug("save or update data "+this.getClass().getName());
		try {
			routerDao.saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update data fail! "+this.getClass().getName(), e);
			throw new AppException("保存或更新路由信息失败");
		}
	}

	public void update(Router entity) {
		// TODO Auto-generated method stub
		log.debug("update data "+this.getClass().getName());
		try {
			routerDao.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update data fail! "+this.getClass().getName(), e);
			throw new AppException("更新路由信息失败");
		}
	}

	public void delete(Router entity) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			routerDao.delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除路由失败");
		}
	}

	public Router getRouterByStationId(Integer stationId) {
		// TODO Auto-generated method stub
		log.debug("get router by station id "+this.getClass().getName());
		try {
			return (Router) routerDao.getRouterByStationId(stationId);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get router by station id fail! "+this.getClass().getName(), e);
			throw new AppException("通过车站id:"+stationId+"获取路由失败");
		}
	}

	public PageModel<Router> getAll(String queryString, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteItem(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

}
