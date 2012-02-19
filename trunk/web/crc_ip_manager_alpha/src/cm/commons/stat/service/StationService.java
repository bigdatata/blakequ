package cm.commons.stat.service;

import java.util.List;
import java.util.Set;

import cm.commons.service.basic.BasicService;

public interface StationService<K, E> extends BasicService<K, E> {
	
	/**
	 * 根据名字获取站点
	 * @param name
	 * @return
	 */
	E getStationByName(String name);
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
	
	
	/***
	 * 获取指定线路的主站点
	 * @param routeId
	 * @return
	 */
	List<E> getMainStationByRoute(K routeId);
	
	
	/**
	 * 在线段插入一个站点
	 * @param station1 上行站点
	 * @param station2 下行站点
	 * @param station3 插入站点
	 */
	void saveStationAndSegment(E station1, E station2, E station3, K route_id);
	
	/**
	 * 获取不在线段上的其他站点
	 * @return
	 */
	List<E> getStationsNotInSegment();
	
	/**
	 * 获取指定线路上的所有站点，并且主站点排在最前
	 * @param route_id
	 * @return
	 */
	List<E> getAllStationByRoute(int route_id);
}
