package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.hao.HibernateUtils;

import junit.framework.TestCase;

/**
 * ���Ӳ�ѯ
 * @author Administrator
 *
 */
public class JoinQueryTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			List students = session.createQuery("select c.name, s.name from Student s join s.classes c")
						.list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Object[] obj = (Object[])iter.next();
				System.out.println(obj[0] + ", " + obj[1]);
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	@SuppressWarnings("unchecked")
	public void testQuery2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//�����ӣ�����Ϊ��������ѧ����û�а༶����ѧ����ѯ����
			List students = session.createQuery("select c.name, s.name from Student s left join s.classes c")
						.list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Object[] obj = (Object[])iter.next();
				System.out.println(obj[0] + ", " + obj[1]);
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	@SuppressWarnings("unchecked")
	public void testQuery3() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//�����ӣ����ұ�Ϊ�ص㣬������û��ѧ�����༶����ѯ����
			List students = session.createQuery("select c.name, s.name from Student s right join s.classes c")
						.list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Object[] obj = (Object[])iter.next();
				System.out.println(obj[0] + ", " + obj[1]);
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
}
