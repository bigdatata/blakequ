/**
 * 
 */
package cm.commons.stat.dao;

import java.io.Serializable;
import java.util.List;

import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;
import cm.commons.util.PageModel;

/**
 * @author Administrator
 *
 */
public interface PortDao<K extends Serializable, E> extends BasicDao<K, E> {

	/**
	 * 获取路由器的所有端口
	 * @param routerId
	 * @return
	 * @throws AppException
	 */
	List<E> getPortsByRouter(K routerId)  throws AppException;
	
	/**
	 * 获取所有端口信息按路由id，分页
	 * @return
	 */
	PageModel<E> getPortsByRouter(K routerId, int pageNo,
			int pageSize) throws AppException;
}
