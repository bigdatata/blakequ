package cm.commons.stat.dao;

import java.io.Serializable;
import java.util.List;

import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;

public interface StationDao<K extends Serializable, E> extends BasicDao<K, E> {
	
	/**
	 * 获取站点个数
	 * @return
	 * @throws AppException
	 */
	int getStationCount() throws AppException;
	
	/**
	 * 获取异常站点个数state=1
	 * @return
	 * @throws AppException
	 */
	int getErrorStationCount() throws AppException;
	
	/**
	 * 获取未知站点个数 state=2
	 * @return
	 * @throws AppException
	 */
	int getUnknowStationCount() throws AppException;
	
	/**
	 * 获取主站点(含有TDCS的站点)
	 * @return
	 */
	List<E> getMainStaion() throws AppException;
	
	/***
	 * 获取指定线路的主站点
	 * @param routeId
	 * @return
	 */
	List<E> getMainStationByRoute(K routeId) throws AppException;
	
	/**
	 * 通过名字获取站点
	 * @param name
	 * @return
	 */
	E getStaionByName(String name) throws AppException;
	
	/**
	 * 获取不在线段上的其他站点
	 * @return
	 * @throws AppException
	 */
	List<E> getStationsNotInSegment() throws AppException;
	
	/**
	 * 获取指定线路上的所有站点，并且主站点排在最前
	 * @param route_id
	 * @return
	 */
	List<E> getAllStationByRoute(int route_id) throws AppException;
}
