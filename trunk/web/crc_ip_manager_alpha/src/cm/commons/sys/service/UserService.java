package cm.commons.sys.service;

import java.util.List;

import cm.commons.pojos.User;
import cm.commons.service.basic.BasicService;

public interface UserService<K, E>  extends BasicService<K, E>{
	/**
	 * 按照权限查找用户(管理员admin, 普通用户user)
	 */
	List<User> getUserByAuthority(String authority);
	
	/**
	 * 更新用户
	 * @entity
	 */
	void updateByName(E entity);
	
	/**
	 * 根据用户名删除用户
	 * @param username
	 */
	void deleteByName(String username);
	
	/**
	 * 获取用户名
	 * @param username
	 * @return
	 */
	E getByName(String username);
}
