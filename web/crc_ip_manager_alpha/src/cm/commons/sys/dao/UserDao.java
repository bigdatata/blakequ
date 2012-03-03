package cm.commons.sys.dao;

import java.io.Serializable;
import java.util.List;

import cm.commons.controller.form.UserForm;
import cm.commons.dao.basic.BasicDao;
import cm.commons.exception.AppException;
import cm.commons.pojos.User;
import cm.commons.util.PageModel;

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
	
	/**
	 * 查询和user对应字段相同的user列表，并且分页显示如果userForm的某一字段为空则这个字段没有限制
	 * @param user
	 * @return
	 * @throws AppException
	 */
	PageModel<User> getPagedUserByUserCondition(User user,int pageNo, int pageSize) throws AppException;
}
