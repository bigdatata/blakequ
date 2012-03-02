package cm.commons.stat.dao;

import java.io.Serializable;
import java.util.List;

import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;

public interface SegmentDao<K extends Serializable, E> extends BasicDao<K, E> {
	
	/**
	 * 获取所有线段根据路线id
	 * @param routeId
	 * @return
	 */
	public List<E> getAllSegmentByRoute(K routeId)  throws AppException;
	
	/**
	 * 获取所有线段根据车站id
	 * @param stationId
	 * @return
	 * @throws AppException
	 */
	public List<E> getSegmentByStation(K stationId)  throws AppException;
}
