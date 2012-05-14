package com.bjpowernode.hibernate;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * 设置<many-to-one>上的lazy为false，其他默认
 * @author Administrator
 *
 */
public class SingleEndLazyTest2 extends TestCase {

	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//不会发出sql
			User user = (User)session.load(User.class, 1);
			
			//发出了两条sql语句，分别加载用户和组
			System.out.println("user.name=" + user.getName());
			
			//不会发出sql
			Group group = user.getGroup();
			
			//不会发出sql
			System.out.println("user.group.name=" + group.getName());
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
	
}
