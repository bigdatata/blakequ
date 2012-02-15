package cm.commons.stat.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.Station;
import cm.commons.stat.dao.StationDao;

public class StationDaoImpl extends BasicDaoImpl<Integer, Station> implements
		StationDao<Integer, Station> {

	private static Log log = LogFactory.getLog(StationDaoImpl.class);
	public StationDaoImpl(){
		super(Station.class);
	}
	
	public int getStationCount() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get station total number");
		try {
			return ((Long)getSession().createQuery("select count(*) from Station s").uniqueResult()).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get station total number fail!", e);
			throw new AppException("获取站点个数失败");
		}
	}

	public int getErrorStationCount() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get error station total number");
		try {
			return ((Long)getSession().createQuery("select count(*) from Station s where s.state = 1").uniqueResult()).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get error station total number fail!", e);
			throw new AppException("获取异常站点个数失败");
		}
	}

	public int getUnknowStationCount() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get unknow station total number");
		try {
			return ((Long)getSession().createQuery("select count(*) from Station s where s.state=2").uniqueResult()).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get unknow station total number fail!", e);
			throw new AppException("获取未知状态站点个数失败");
		}
	}

	public List<Station> getMainStaion() {
		// TODO Auto-generated method stub
		log.debug("get main station contain TDCS");
		try {
			return getSession().createQuery("from Station s where s.name like ?")
						.setParameter(0, "%"+"TDCS"+"%")
						.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get main station contain TDCS fail!", e);
			throw new AppException("获取主站点失败");
		}
	}
	
	

}
