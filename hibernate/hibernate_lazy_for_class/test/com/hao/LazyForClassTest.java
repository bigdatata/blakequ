package com.hao;

import org.hibernate.Session;

import junit.framework.TestCase;

public class LazyForClassTest extends TestCase {

	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//���ᷢ��sql
			User user = (User)session.load(User.class, 1);
			
			//���ᷢ��sql
			System.out.println("user.id=" + user.getId());
			
			//�ᷢ��sql
			System.out.println("user.name=" + user.getName());
			
			//���ᷢ��sql
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
		//������ȷ������׳���LazyInitializationException�쳣
		//ԭ������session�Ѿ��ر�
		//hibernate��lazy���Ա�����session��״̬����Ч
		System.out.println("user.name=" + user.getName());
	}	
	
}
