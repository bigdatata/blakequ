package cm.commons.sys.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.springframework.jdbc.object.SqlQuery;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.ComputerLog;
import cm.commons.pojos.RouterLog;
import cm.commons.sys.dao.RouterLogDao;

public class RouterLogDaoImpl extends BasicDaoImpl<Integer, RouterLog> implements
		RouterLogDao<Integer, RouterLog> {
	private static Log log = LogFactory.getLog(RouterLogDaoImpl.class);
	public RouterLogDaoImpl(){
		super(RouterLog.class);
	}
	@SuppressWarnings("unchecked")
	public List<RouterLog> getAllSortByRouter() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all router log by router "+this.getClass().getName());
		try {
			List<RouterLog> list = getSession().createSQLQuery("select {router_log.*} from router_log ORDER BY router_id;")
											.addEntity("router_log", RouterLog.class)
											.list();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all router log by router fail!"+this.getClass().getName(), e);
			throw new AppException("通过路由排序获取日志失败");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RouterLog> getAllSortByTime() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all router log sort by time "+this.getClass().getName());
		try {
			List<RouterLog> list = getSession().createQuery("from RouterLog r order by r.currTime").list();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all router log sort by time fail! "+this.getClass().getName(), e);
			throw new AppException("通过时间排序获取日志失败");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RouterLog> getRouterLog(Integer routerId) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get router log by id:"+routerId+this.getClass().getName());
		try {
			List<RouterLog> list = getSession().createSQLQuery("select {router_log.*} from router_log where router_id=?;")
									.addEntity("router_log", RouterLog.class)
									.setParameter(0, routerId)
									.list();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get router log by id:"+routerId+" fail! "+this.getClass().getName(), e);
			throw new AppException("获取指定路由:"+routerId+"日志失败");
		}
	}
	@SuppressWarnings("unchecked")
	public List<RouterLog> getRouterLogByStationNameOrId(String key) {
		// TODO Auto-generated method stub
		log.debug("get router log by station name or id "+this.getClass().getName());
		try {
			List<RouterLog> list = null;
			list = getSession().createSQLQuery("SELECT rl.* FROM router_log rl " +
					"JOIN router r ON r.id=rl.router_id " +
					"JOIN station s ON s.id=r.station_id " +
					"where s.name LIKE ? or s.id LIKE ? order by rl.curr_time;")
					.addEntity(RouterLog.class)
					.setParameter(0, "%"+key+"%")
					.setParameter(1, "%"+key+"%")
					.list();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get router log by station name or id fail！"+this.getClass().getName(), e);
			throw new AppException("根据站点的名字或ID："+key+"获取日志失败");
		}
	}

}
