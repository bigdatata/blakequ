package cm.commons.stat.service;

import cm.commons.exception.AppException;
import cm.commons.pojos.Router;
import cm.commons.service.basic.BasicService;

public interface RouterService<K, E> extends BasicService<K, E> {
	/**
	 * 通过ip获取路由器
	 * @param ip
	 * @return
	 */
	Router getRouterByIp(String ip);
	
	/**
	 * 通过ip删除路由器
	 * @param ip
	 */
	void deleteRouterByIp(String ip);
	
	/**
	 * 通过车站id获取路由器
	 * @param station_id
	 * @return
	 */
	E getRouterByStationId(K station_id);
}
