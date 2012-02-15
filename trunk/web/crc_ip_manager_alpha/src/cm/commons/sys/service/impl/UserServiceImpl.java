package cm.commons.sys.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.exception.AppException;
import cm.commons.pojos.User;
import cm.commons.service.basic.BasicServiceAbstImpl;
import cm.commons.sys.dao.UserDao;
import cm.commons.sys.service.UserService;

public class UserServiceImpl implements UserService<Integer, User> {
	private static Log log = LogFactory.getLog(UserServiceImpl.class);
	
	private UserDao userDao;
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void deleteByName(String username) {
		// TODO Auto-generated method stub
		log.debug("delete by name:"+this.getClass().getName());
		try {
			userDao.deleteByName(username);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete by name fail! "+this.getClass().getName(), e);
			throw new AppException("删除用户名"+username+"失败");
		}
	}

	public User getByName(String username) {
		// TODO Auto-generated method stub
		log.debug("get user by username"+this.getClass().getName());
		try {
			return (User) userDao.getByName(username);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get user by username fail! "+this.getClass().getName(), e);
			throw new AppException("获取用户"+username+"失败");
		}
	}

	public List<User> getUserByAuthority(String authority) {
		// TODO Auto-generated method stub
		log.debug("get user by authority "+this.getClass().getName());
		try {
			return userDao.getUserByAuthority(authority);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get user by authority fail! "+this.getClass().getName(), e);
			throw new AppException("获取权限为"+authority+"的用户失败");
		}
	}

	public void updateByName(User entity) {
		// TODO Auto-generated method stub
		log.debug("update user by name "+this.getClass().getName());
		try {
			userDao.updateByName(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update user by name fail! "+this.getClass().getName(), e);
			throw new AppException("修改用户密码失败");
		}
	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			userDao.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除用户失败");
		}
	}

	public User get(Integer id) {
		// TODO Auto-generated method stub
		log.debug("get data "+this.getClass().getName());
		try {
			return (User) userDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get data fail! "+this.getClass().getName(), e);
			throw new AppException("");
		}
	}

	public List<User> getAll() {
		// TODO Auto-generated method stub
		log.debug("get all data "+this.getClass().getName());
		try {
			return userDao.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data fail! "+this.getClass().getName(), e);
			throw new AppException("获取所有用户失败");
		}
	}

	public void save(User entity) {
		// TODO Auto-generated method stub
		log.debug("save data "+this.getClass().getName());
		try {
			userDao.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save data fail! "+this.getClass().getName(), e);
			throw new AppException("存储用户失败");
		}
	}

	public void saveOrUpdate(User entity) {
		// TODO Auto-generated method stub
		log.debug("save or update data "+this.getClass().getName());
		try {
			userDao.saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update data fail! "+this.getClass().getName(), e);
			throw new AppException("存储或更新用户失败");
		}
	}

	public void update(User entity) {
		// TODO Auto-generated method stub
		log.debug("update data "+this.getClass().getName());
		try {
			userDao.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update data fail! "+this.getClass().getName(), e);
			throw new AppException("更新用户失败");
		}
	}

}