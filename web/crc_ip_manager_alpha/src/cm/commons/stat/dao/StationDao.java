package cm.commons.stat.dao;

import java.util.List;

import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;

public interface StationDao<K, E> extends BasicDao<K, E> {
	
	/**
	 * 获取站点个数
	 * @return
	 * @throws AppException
	 */
	int getStationCount() throws AppException;
	
	/**
	 * 获取异常站点个数state=1
	 * @return
	 * @throws AppException
	 */
	int getErrorStationCount() throws AppException;
	
	/**
	 * 获取未知站点个数 state=2
	 * @return
	 * @throws AppException
	 */
	int getUnknowStationCount() throws AppException;
	
	/**
	 * 获取主站点(含有TDCS的站点)
	 * @return
	 */
	List<E> getMainStaion();
}
