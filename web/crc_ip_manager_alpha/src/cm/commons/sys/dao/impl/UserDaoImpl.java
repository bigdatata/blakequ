package cm.commons.sys.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDao;
import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.User;
import cm.commons.sys.dao.UserDao;

public class UserDaoImpl extends BasicDaoImpl<Integer, User> implements UserDao<Integer, User> {

	private static Log log = LogFactory.getLog(UserDaoImpl.class);
	
	public UserDaoImpl(){
		super(User.class);
	}
	
	public List<User> getUserByAuthority(String authority) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get user by authority from UserDaoImpl");
		String auth = authority.toLowerCase();
		if(auth.equals("user") || auth.equals("admin")){
			try {
				return getSession().createQuery("from User u where u.authority= ?")
					.setParameter(0, auth)
					.list();
			} catch (Exception e) {
				// TODO: handle exception
				log.error("get user by authrity="+authority+" fail!", e);
				throw new AppException("获取系统用户失败，authority="+authority);
			}
		}else{
			log.error("get user by authrity="+auth+" fail! notice the authority should be admin or user");
			throw new AppException("获取系统用户失败，权限不能为空或非admin,user"); 
		}
	}

	public void updateByName(User entity) throws AppException{
		// TODO Auto-generated method stub
		log.debug("update user by username from UserDaoImpl");
		try {
			getSession().createQuery("update User u set u.password = ?, u.authority=? where u.username=?")
				.setParameter(0, entity.getPassword())
				.setParameter(1, entity.getAuthority())
				.setParameter(2, entity.getUsername())
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update user by username fail!", e);
			throw new AppException("更新用户失败！");
		}
	}


	public void deleteByName(String username) throws AppException{
		// TODO Auto-generated method stub
		log.debug("delete user by username from UserDaoImpl");
		try {
			getSession().createQuery("delete from User u where u.username=?")
				.setParameter(0, username)
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete from user by username="+username+"fail!", e);
			throw new AppException("删除用户失败！");
		}
	}

	public User getByName(String username) throws AppException{
		// TODO Auto-generated method stub
		log.debug("get user by username from UserDaoImpl");
		try {
			return (User) getSession().createQuery("from User u where u.username=?")
				.setParameter(0, username)
				.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get user by username="+username+" fail!", e);
			throw new AppException("获取用户失败!");
		}
	}

}
