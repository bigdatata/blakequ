package cm.commons.stat.service;

import cm.commons.service.basic.BasicService;

public interface RouteService<K, E> extends BasicService<K, E> {

	/**
	 * 通过名字获取线路
	 * @param name
	 * @return
	 */
	E getRouteByName(String name);
}
