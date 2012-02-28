package cm.commons.stat.service;

import java.util.List;

import cm.commons.exception.AppException;
import cm.commons.service.basic.BasicService;

public interface PortService<K, E> extends BasicService<K, E> {

	/**
	 * 获取路由器的所有端口
	 * @param routerId
	 * @return
	 * @throws AppException
	 */
	List<E> getPortsByRouter(K routerId);
}
