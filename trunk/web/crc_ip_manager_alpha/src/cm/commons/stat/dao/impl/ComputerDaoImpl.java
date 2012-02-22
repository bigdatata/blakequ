package cm.commons.stat.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.Computer;
import cm.commons.stat.dao.ComputerDao;

public class ComputerDaoImpl extends BasicDaoImpl<Integer, Computer> implements
		ComputerDao<Integer, Computer> {

	private static Log log = LogFactory.getLog(Computer.class);
	public ComputerDaoImpl(){
		super(Computer.class);
	}
	public void deleteComputerByIp(String ip) throws AppException {
		// TODO Auto-generated method stub
		log.debug("delete computer by ip="+ip);
		try {
			getSession().createQuery("delete from Computer c where c.ip=?")
				.setParameter(0, ip)
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete computer by ip="+ip+" fail!", e);
			throw new AppException("删除IP="+ip+"的台账电脑失败");
		}
		
	}
	public Computer getComputerByIp(String ip) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get computer by ip="+ip);
		try {
			return (Computer) getSession().createQuery("from Computer c where c.ip=?")
						.setParameter(0, ip)
						.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get computer by ip="+ip+" fail!", e);
			throw new AppException("获取IP="+ip+"的台账电脑失败");
		}
	}
	public Computer getComputerByStationId(Integer stationId) {
		// TODO Auto-generated method stub
		log.debug("get computer by station id");
		try {
			Computer c = (Computer) getSession().createQuery("from Router r where r.station.id = ?")
							.setParameter(0, stationId)
							.uniqueResult();
			return c;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get computer by station id fail!", e);
			throw new AppException("通过车站id"+stationId+"获取路由失败");
		}
	}
}
