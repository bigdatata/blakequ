package cm.commons.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.controller.form.UserForm;
import cm.commons.dao.basic.BasicDao;
import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.User;
import cm.commons.sys.dao.UserDao;
import cm.commons.util.NullUtil;
import cm.commons.util.ObjectUtil;
import cm.commons.util.PageModel;

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

	/**
	 * 可以根据用户名或者权限查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageModel<User> getAll(String queryString, int pageNo, int pageSize)
			throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all data by page");
		try {
			List<User> list = new ArrayList<User>();
			if (queryString != null && !"".equals(queryString)) {
				list = getSession().createQuery("from User u " +
						"where u.username like ? " +
						"or u.authority like ? order by u.authority")
						.setParameter(0, "%"+queryString+"%")
						.setParameter(1, "%"+queryString+"%")
						.setFirstResult((pageNo-1) * pageSize)
						.setMaxResults(pageSize)
						.list();
			}else{
				list = getSession().createQuery("from User u order by u.authority")
									.setFirstResult((pageNo-1) * pageSize)
									.setMaxResults(pageSize)
									.list();
			}
			
			PageModel pageModel = new PageModel();
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setList(list);
			pageModel.setTotalRecords(getTotalRecords(queryString));
			return pageModel;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data by page fail!", e);
			throw new AppException("通过分页获取数据失败");
		}
	}

	private int getTotalRecords(String queryString) {
		// TODO Auto-generated method stub
		int count = 0;
		if (queryString != null && !"".equals(queryString)) {
			count = ((Long)getSession().createQuery("select count(*) from User u " +
					"where u.username like ? " +
					"or u.authority like ?")
					.setParameter(0, "%"+queryString+"%")
					.setParameter(1, "%"+queryString+"%")
					.uniqueResult()).intValue();
		}else{
			count = ((Long)getSession().createQuery("select count(*) from User")
					.uniqueResult()).intValue();
		}
		return count;
	}

	public PageModel<User> getPagedUserByUserCondition(User user, int pageNo, int pageSize)
			throws AppException {
		System.out.println(user);
		StringBuilder hql=new StringBuilder("from User ");
		String[] conditions={"id","username","authority"};
		boolean isFirstCondition=true;
		for(String condition:conditions){
			Object value=ObjectUtil.getFieldValueByName(condition, user);
			
			if(NullUtil.notNull(value)){
				if(isFirstCondition){
					isFirstCondition=false;
					hql.append(" where ").append(condition).append(" = '").append(value).append("'");
				}else{
					hql.append(" and ").append(condition).append(" = '").append(value).append("'");
				}
			}
		}
		log.debug(hql.toString());
		List<User> list=(List<User>) getSession().createQuery(hql.toString()).setFirstResult((pageNo-1) * pageSize)
		.setMaxResults(pageSize).list();
		PageModel pageModel = new PageModel();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		pageModel.setList(list);
		pageModel.setTotalRecords(getCounts(hql.toString()));
		return pageModel;
	}

	
	
}
