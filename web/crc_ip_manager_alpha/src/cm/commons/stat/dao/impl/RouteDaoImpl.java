package cm.commons.stat.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.NonUniqueResultException;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.Route;
import cm.commons.stat.dao.RouteDao;

public class RouteDaoImpl extends BasicDaoImpl<Integer, Route> implements RouteDao<Integer, Route> {

	private static Log log = LogFactory.getLog(RouteDaoImpl.class);
	public RouteDaoImpl(){
		super(Route.class);
	}

	public Route getRouteByName(String name) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get route by name");
		try {
			Route r = (Route) getSession().createQuery("from Route r where r.name = ?")
							.setParameter(0, name)
							.uniqueResult();
			return r;
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
}
