package cm.commons.sys.service;

import cm.commons.pojos.System;
import cm.commons.service.basic.BasicService;

public interface SystemService<K, E> extends BasicService<K, E>{
	/**
	 * 根据键值获得配置信息
	 * @param configKey
	 * @return
	 */
	System getSystemConfigByKey(String configKey);
	
	/**
	 * 根据键值更新配置信息
	 * @param configKey
	 * @return
	 * @throws AppException
	 */
	void updateSystemConfigKey(E entity);
	
	/**
	 * 更新或添加
	 * @param configKey
	 * @return
	 * @throws AppException
	 */
	void saveOrUpdateSystemConfigKey(E entity);
	
	/**
	 * 通过key删除
	 * @param configKey
	 */
	void deleteByConfigKey(String configKey);
}
