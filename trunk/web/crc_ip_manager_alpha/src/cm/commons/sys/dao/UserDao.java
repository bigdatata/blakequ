package cm.commons.sys.dao;

import java.io.Serializable;
import java.util.List;

import sun.reflect.generics.tree.BaseType;

import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;
import cm.commons.pojos.User;

public interface UserDao<K extends Serializable, E> extends BasicDao<K, E>{

	/**
	 * 按照权限查找用户(管理员admin, 普通用户user)
	 */
	List<User> getUserByAuthority(String authority) throws AppException;
	
	/**
	 * 更新用户
	 * @throws AppException
	 */
	void updateByName(E entity) throws AppException;
	
	/**
	 * 根据用户名删除用户
	 * @param username
	 */
	void deleteByName(String username) throws AppException;
	
	/**
	 * 获取用户名
	 * @param username
	 * @return
	 */
	E getByName(String username) throws AppException;
}
