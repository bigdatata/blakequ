package cm.commons.sys.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.Warn;
import cm.commons.sys.dao.WarnDao;

public class WarnDaoImpl extends BasicDaoImpl<Integer, Warn> implements WarnDao<Integer, Warn> {

	private static Log log = LogFactory.getLog(WarnDaoImpl.class);
	
	public WarnDaoImpl(){
		super(Warn.class);
	}
	
	public List<Warn> getWarnByDate(Date beginTime, Date endTime) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get warn by date from beginTime="+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(beginTime)+
				" endTime="+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(endTime));
		if(beginTime.after(endTime)){
			log.error("the begin time can't after end time");
			throw new AppException("查询告警日志错误！");
		}
		try {
			return getSession().createQuery("from Warn w where w.warntime between ? and ?")
				.setParameter(0, beginTime)
				.setParameter(1, endTime)
				.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get warn by time error", e);
			throw new AppException("查询告警日志错误！");
		}
	}

	public List<Warn> getWarnByState(int warnState) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get warn by state");
		try {
			return getSession().createQuery("from Warn w where w.warnstate=?")
				.setParameter(0, warnState)
				.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get warn by state error state="+warnState, e);
			throw new AppException("查询告警日志错误，告警状态state="+warnState);
		}
	}

	public List<Warn> getWarnByStation(int stationId) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get warn by station "+stationId);
		try {
			return getSession().createQuery("from Warn w where w.stationId=?")
				.setParameter(0, stationId)
				.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get warn by station id="+stationId+" error!", e);
			throw new AppException("查询告警日志错误，查询站点id="+stationId);
		}
	}

	public void deleteByState(int warnState) throws AppException {
		// TODO Auto-generated method stub
		log.debug("delete warn by state="+warnState);
		try {
			getSession().createQuery("delete from Warn w where w.warnstate=?")
				.setParameter(0, warnState)
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete warn by state="+warnState+" fail!", e);
			throw new AppException("删除告警失败！");
		}
	}

	public void deleteByStation(int stationId) throws AppException {
		// TODO Auto-generated method stub
		log.debug("");
		try {
			getSession().createQuery("delete from Warn w where w.stationId=?")
				.setParameter(0, stationId)
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete warn by station="+stationId+" fail!", e);
			throw new AppException("删除告警失败！");
		}
	}

	public void deleteByTime(Date beginTime, Date endTime) throws AppException {
		// TODO Auto-generated method stub
		log.debug("");
		if(beginTime.after(endTime)){
			log.error("the begin time can't after end time");
			throw new AppException("删除告警日志错误！");
		}
		try {
			getSession().createQuery("delete from Warn w where w.warntime between ? and ?")
				.setParameter(0, beginTime)
				.setParameter(1, endTime)
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete warn by time ("+beginTime+", "+endTime+") fail!", e);
			throw new AppException("删除告警失败！");
		}
	}
	
}
