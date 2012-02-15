package cm.commons.sys.dao;

import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;
import cm.commons.pojos.System;;

public interface SystemDao<K, E> extends BasicDao<K, E> {
	
	/**
	 * 根据键值获得配置信息
	 * @param configKey
	 * @return
	 */
	System getSystemConfigByKey(String configKey) throws AppException;
	
	/**
	 * 根据键值更新配置信息
	 * @param configKey
	 * @return
	 * @throws AppException
	 */
	void updateSystemConfigKey(E entity) throws AppException;
	
	/**
	 * 更新或添加
	 * @param configKey
	 * @return
	 * @throws AppException
	 */
	void saveOrUpdateSystemConfigKey(E entity) throws AppException;
	
	/**
	 * 通过key删除
	 * @param configKey
	 */
	void deleteByConfigKey(String configKey) throws AppException;
}
