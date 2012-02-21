package cm.commons.sys.dao;

import java.io.Serializable;
import java.util.List;

import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;

public interface ComputerLogDao<K extends Serializable, E> extends BasicDao<K, E> {

	/**
	 * 获取所有日志按照时间排序
	 * @return
	 */
	public List<E> getAllSortByTime() throws AppException;
	
	/**
	 * 获取所有日志按所属电脑排序
	 * @return
	 */
	public List<E> getAllSortByComputer() throws AppException;
	
	/**
	 * 获取单个电脑的日志
	 * @param router_id
	 * @return
	 */
	public List<E> getComputerLog(K computer_id) throws AppException;
	
	/**
	 * 根据站点的名字或id获取日志
	 * @return
	 */
	public List<E> getComputerLogByStationNameOrId(String key) throws AppException;
	
	
}
