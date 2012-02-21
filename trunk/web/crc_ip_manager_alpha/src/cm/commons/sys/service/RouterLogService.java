package cm.commons.sys.service;

import java.util.List;

import cm.commons.exception.AppException;
import cm.commons.service.basic.BasicService;

public interface RouterLogService<K, E> extends BasicService<K, E>{
	/**
	 * 获取所有日志按照时间排序
	 * @return
	 */
	public List<E> getAllSortByTime();
	
	/**
	 * 获取所有日志按所属路由排序
	 * @return
	 */
	public List<E> getAllSortByRouter();
	
	/**
	 * 获取单个路由的日志
	 * @param router_id
	 * @return
	 */
	public List<E> getRouterLog(K router_id);
	
	/**
	 * 根据站点的名字或id获取日志
	 * @return
	 */
	public List<E> getRouterLogByStationNameOrId(String key);
}
