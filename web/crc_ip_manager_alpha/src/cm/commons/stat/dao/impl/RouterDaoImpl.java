package cm.commons.stat.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.Router;
import cm.commons.stat.dao.RouterDao;

public class RouterDaoImpl extends BasicDaoImpl<Integer, Router> implements
		RouterDao<Integer, Router> {

	private static Log log = LogFactory.getLog(RouterDaoImpl.class);
	public RouterDaoImpl(){
		super(Router.class);
	}
	
	public void deleteRouterByIp(String ip) throws AppException {
		// TODO Auto-generated method stub
		log.debug("delete router by ip="+ip);
		try {
			getSession().createQuery("delete from Router r where r.routerIp = ?")
				.setParameter(0, ip)
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete router by ip="+ip+" fail!", e);
			throw new AppException("通过ip="+ip+"删除路由失败");
		}
	}

	public Router getRouterByIp(String ip) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get router by ip="+ip);
		try {
			return (Router) getSession().createQuery("from Router r where r.routerIp = ?")
						.setParameter(0, ip)
						.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get router by ip="+ip+" fail!", e);
			throw new AppException("获取ip="+ip+"的路由失败");
		}
	}

	public Router getRouterByStationId(Integer stationId) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get router by station id");
		try {
			Router r = (Router) getSession().createQuery("from Router r where r.station.id = ?")
							.setParameter(0, stationId)
							.uniqueResult();
			return r;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get router by station id fail!", e);
			throw new AppException("通过站点id："+stationId+"获取路由失败");
		}
	}

}
