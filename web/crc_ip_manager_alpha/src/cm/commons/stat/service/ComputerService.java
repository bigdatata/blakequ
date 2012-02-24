package cm.commons.stat.service;

import cm.commons.exception.AppException;
import cm.commons.pojos.Computer;
import cm.commons.service.basic.BasicService;

public interface ComputerService<K, E> extends BasicService<K, E>{
	/**
	 * 通过ip获取电脑
	 * @param ip
	 * @return
	 */
	Computer getComputerByIp(String ip);
	
	/**
	 * 通过ip删除电脑
	 * @param ip
	 */
	void deleteComputerByIp(String ip);
	
	/**
	 * 通过车站id获取电脑
	 * @param station_id
	 * @return
	 */
	E getComputerByStationId(K station_id);
}
