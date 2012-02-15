package cm.commons.sys.dao;

import java.util.Date;
import java.util.List;

import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;
import cm.commons.pojos.Warn;;

public interface WarnDao<K, E> extends BasicDao<K, E> {

	/**
	 * 根据站点获得告警信息
	 * @param stationId
	 * @return
	 */
	List<Warn> getWarnByStation(int stationId) throws AppException;
	
	/**
	 * 根据告警级别获取信息
	 * @param warnState
	 * @return
	 */
	List<Warn> getWarnByState(int warnState) throws AppException;
	
	/**
	 * 根据时间获取告警信息
	 * @param warnTime
	 * @return
	 */
	List<Warn> getWarnByDate(Date beginTime, Date endTime) throws AppException;
	
	void deleteByState(int warnState) throws AppException;
	
	void deleteByTime(Date beginTime, Date endTime) throws AppException;
	
	void deleteByStation(int stationId) throws AppException;
}
