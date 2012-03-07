package cm.commons.sys.service;

import java.util.Date;
import java.util.List;

import cm.commons.dao.hiber.util.Element;
import cm.commons.pojos.Warn;
import cm.commons.service.basic.BasicService;
import cm.commons.util.PageModel;

public interface WarnService<K, E>  extends BasicService<K, E>{
	/**
	 * 根据站点获得告警信息
	 * @param stationId
	 * @return
	 */
	List<Warn> getWarnByStation(int stationId);
	
	/**
	 * 根据告警级别获取信息
	 * @param warnState
	 * @return
	 */
	List<Warn> getWarnByState(int warnState);
	
	/**
	 * 根据时间获取告警信息
	 * @param warnTime
	 * @return
	 */
	List<Warn> getWarnByDate(Date beginTime, Date endTime);
	
	/**
	 * 根据状态删除告警
	 * @param warnState
	 */
	void deleteByState(int warnState);
	
	/**
	 * 根据时间删除告警
	 * @param beginTime
	 * @param endTime
	 */
	void deleteByTime(Date beginTime, Date endTime);
	
	/**
	 * 根据站点删除告警
	 * @param stationId
	 */
	void deleteByStation(int stationId);
	
	PageModel<Warn> getPagedUserByCondition(List<Element> conditions, int pageNo, int pageSize);
}
