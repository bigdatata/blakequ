package cm.commons.stat.service;

import java.util.List;

import cm.commons.service.basic.BasicService;

public interface StationService<K, E> extends BasicService<K, E> {
	/**
	 * 获取站点个数
	 * @return
	 * @throws AppException
	 */
	int getStationCount();
	
	/**
	 * 获取异常站点个数state=1
	 * @return
	 * @throws AppException
	 */
	int getErrorStationCount();
	
	/**
	 * 获取未知站点个数 state=2
	 * @return
	 * @throws AppException
	 */
	int getUnknowStationCount();
	
	/**
	 * 获取主站点(含有TDCS的站点)
	 * @return
	 */
	List<E> getMainStaion();
	
	
	/**
	 * 获取指定线路的所有站点
	 * @param routeId
	 * @return
	 */
	List<E> getAllStationFromRoute(K routeId);
	
}
