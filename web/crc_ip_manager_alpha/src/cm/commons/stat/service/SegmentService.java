package cm.commons.stat.service;

import java.util.List;

import cm.commons.exception.AppException;
import cm.commons.service.basic.BasicService;

public interface SegmentService<K, E> extends BasicService<K, E> {
	
	/**
	 * 获取指定线路的所有线段
	 * @param routeId
	 * @return
	 */
	List<E> getAllSegmentByRoute(K routeId);

	/**
	 * 获取所有线段根据车站id
	 * @param stationId
	 * @return
	 * @throws AppException
	 */
	public List<E> getSegmentByStation(K stationId);
}
