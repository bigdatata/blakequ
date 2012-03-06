package cm.commons.stat.service;

import java.util.List;

import cm.commons.exception.AppException;
import cm.commons.service.basic.BasicService;
import cm.commons.util.PageModel;

public interface PortService<K, E> extends BasicService<K, E> {

	/**
	 * 获取路由器的所有端口
	 * @param routerId
	 * @return
	 * @throws AppException
	 */
	List<E> getPortsByRouter(K routerId);
	
	/**
	 * 获取所有端口信息按路由id，分页
	 * @return
	 */
	PageModel<E> getPortsByRouter(Integer routerId, int pageNo,
			int pageSize) throws AppException;
}
