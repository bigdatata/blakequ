package cm.commons.service.basic;

import java.util.List;

import cm.commons.exception.AppException;
import cm.commons.util.PageModel;

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
	void deleteById(K id);
	
	/**
	 * 删除多个实体对象
	 * @param ids
	 * @throws AppException
	 */
	void deleteItem(K[] ids);
	
	/**
	 * 删除实体对象
	 * @param entity
	 * @throws AppException
	 */
	void delete(E entity);
	
	/**
	 * 分页查询对象
	 * @param queryString 查询条件
	 * @param pageNo 页数
	 * @param pageSize 页面大小
	 * @return
	 */
	PageModel<E> getAll(String queryString, int pageNo, int pageSize);
	
}
