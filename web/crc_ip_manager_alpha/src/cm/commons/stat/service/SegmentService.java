package cm.commons.stat.service;

import java.util.List;

import cm.commons.service.basic.BasicService;

public interface SegmentService<K, E> extends BasicService<K, E> {
	
	/**
	 * 获取指定线路的所有线段
	 * @param routeId
	 * @return
	 */
	public List<E> getAllSegmentByRoute(K routeId);
	
}
