package cm.commons.service.basic;

import java.util.List;

import cm.commons.exception.AppException;

public interface BasicService<K, E> {
	/**
	 * 保存实体对象
	 * @param entity
	 */
	void save(E entity);
	
	/**
	 * 更新或者保存对象
	 * @param entity
	 * @throws AppException
	 */
	void saveOrUpdate(E entity);
	
	/**
	 * 更新实体
	 * @param entity
	 */
	void update(E entity);
	
	/**
	 * 获取实体对象
	 * @param id
	 * @return
	 */
	E get(K id);
	
	/**
	 * 获取所有实体对象
	 * @return
	 * @throws AppException
	 */
	List<E> getAll();
	
	/**
	 * 删除实体对象
	 * @param id
	 */
	void delete(K id);
}
