package cm.commons.sys.dao;

import java.io.Serializable;
import java.util.List;

import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;

public interface RouterLogDao<K extends Serializable, E> extends BasicDao<K, E> {
	
	/**
	 * 获取所有日志按照时间排序
	 * @return
	 */
	public List<E> getAllSortByTime() throws AppException;
	
	/**
	 * 获取所有日志按所属路由排序
	 * @return
	 */
	public List<E> getAllSortByRouter() throws AppException;
	
	/**
	 * 获取单个路由的日志
	 * @param router_id
	 * @return
	 */
	public List<E> getRouterLog(K router_id) throws AppException;
	
	/**
	 * 根据站点的名字或id获取日志
	 * @return
	 */
	public List<E> getRouterLogByStationNameOrId(String key) throws AppException;
}
