package cm.commons.stat.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;

import cm.commons.exception.AppException;
import cm.commons.pojos.Route;
import cm.commons.stat.dao.RouteDao;
import cm.commons.stat.service.RouteService;
import cm.commons.util.PageModel;

public class RouteServiceImpl implements RouteService<Integer, Route> {

	private static Log log = LogFactory.getLog(RouteServiceImpl.class);
	@Autowired
	private RouteDao routeDao;

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

	public Route getRouteByName(String name) {
		// TODO Auto-generated method stub
		log.debug("get route by name");
		try {
			return (Route) routeDao.getRouteByName(name);
		}catch(NonUniqueResultException e){
			log.error("get route by name fail!", e);
			throw new AppException("线路:"+name+"已经存在!配置文件有误");
		}  
		catch (Exception e) {
			// TODO: handle exception
			log.error("get route by name fail!", e);
			throw new AppException("获取线路:"+name+"失败");
		}
	}

	public void deleteItem(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

}
