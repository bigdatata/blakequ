package cm.commons.sys.service;

import java.util.List;

import cm.commons.exception.AppException;
import cm.commons.service.basic.BasicService;
import cm.commons.util.PageModel;

public interface ComputerLogService<K, E>  extends BasicService<K, E>{

	/**
	 * 获取所有日志按照时间排序
	 * @return
	 */
	public List<E> getAllSortByTime();
	
	/**
	 * 获取所有日志按所属电脑排序
	 * @return
	 */
	public List<E> getAllSortByComputer();
	
	/**
	 * 获取单个电脑的日志
	 * @param router_id
	 * @return
	 */
	public List<E> getComputerLog(K computer_id);
	
	/**
	 * 根据站点的名字或id获取日志
	 * @return
	 */
	public List<E> getComputerLogByStationNameOrId(String key);
	
	/**
	 * 获取所有日志按所属电脑排序，分页
	 * @return
	 */
	public PageModel<E> getAllSortByComputer(String queryString, int pageNo,
			int pageSize) throws AppException;
}
