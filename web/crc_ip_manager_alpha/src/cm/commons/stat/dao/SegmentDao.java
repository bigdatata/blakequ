package cm.commons.stat.dao;

import java.io.Serializable;
import java.util.List;

import cm.commons.dao.basic.BasicDao;

public interface SegmentDao<K extends Serializable, E> extends BasicDao<K, E> {
	
	/**
	 * 获取所有线段根据路线id
	 * @param routeId
	 * @return
	 */
	public List<E> getAllSegmentByRoute(K routeId);
}
