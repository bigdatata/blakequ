package com.hao;

import org.hibernate.Session;

import junit.framework.TestCase;

public class LazyForClassTest extends TestCase {

	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//不会发出sql
			User user = (User)session.load(User.class, 1);
			
			//不会发出sql
			System.out.println("user.id=" + user.getId());
			
			//会发出sql
			System.out.println("user.name=" + user.getName());
			
			//不会发出sql
			System.out.println("user.password=" + user.getPassword());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	public void testLoad2() {
		Session session = null;
		User user = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			user = (User)session.load(User.class, 1);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
		//不能正确输出，抛出了LazyInitializationException异常
		//原因在于session已经关闭
		//hibernate的lazy策略必须在session打开状态下有效
		System.out.println("user.name=" + user.getName());
	}	
	
}
