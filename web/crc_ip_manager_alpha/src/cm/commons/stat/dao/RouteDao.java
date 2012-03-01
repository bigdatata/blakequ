package cm.commons.stat.dao;

import java.io.Serializable;

import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;

/**
 * 线路
 * @author Administrator
 *
 * @param <K>
 * @param <E>
 */
public interface RouteDao<K extends Serializable, E> extends BasicDao<K, E> {
	
	/**
	 * 通过名字获取线路
	 * @param name
	 * @return
	 */
	E getRouteByName(String name) throws AppException;
}
