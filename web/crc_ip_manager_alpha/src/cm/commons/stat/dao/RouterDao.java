package cm.commons.stat.dao;

import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;
import cm.commons.pojos.Router;

public interface RouterDao<K, E> extends BasicDao<K, E> {

	/**
	 * 通过ip获取路由器
	 * @param ip
	 * @return
	 */
	Router getRouterByIp(String ip) throws AppException;
	
	/**
	 * 通过ip删除路由器
	 * @param ip
	 */
	void deleteRouterByIp(String ip)  throws AppException;
	
}
