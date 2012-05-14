package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.hao.HibernateUtils;
import com.hao.Student;

import junit.framework.TestCase;

/**
 * �����Բ�ѯ
 * @author Administrator
 *
 */
public class SimplePropertyQueryTest extends TestCase {

	/**
	 * ��һ���Բ�ѯ
	 */
	@SuppressWarnings("unchecked")
	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//���ؽ���������б�Ԫ�����ͺ�ʵ�����е���������һ��
//			List students = session.createQuery("select name from Student").list();
			List students = session.createQuery("select name from Classes").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				String name = (String)iter.next();
				System.out.println(name);
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
	
	/**
	 * ������Բ�ѯ
	 */
	@SuppressWarnings("unchecked")
	public void testQuery2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//��ѯ������ԣ����ض������鼯��
			//����Ԫ�ص��������ѯ����������һ��
			//����ĳ�����select�в�ѯ�����Ը���һ��
			List students = session.createQuery("select id, name from Student").list();
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
	
	/**
	 * ������Բ�ѯ,����Student
	 */
	@SuppressWarnings("unchecked")
	public void testQuery3() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//����ʹ��hql����Student����
			//��Ҫ�ṩ���캯��
			List students = session.createQuery("select new Student(id, name) from Student").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println(student.getId() + ", " + student.getName());
			}
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	/**
	 * ����ʹ�ñ���
	 */
	@SuppressWarnings("unchecked")
	public void testQuery4() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			// ����ʹ�ñ���
			List students = session.createQuery("select s.id, s.name from Student s").list();
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
	
	/**
	 * ����ʹ�ñ���
	 */
	@SuppressWarnings("unchecked")
	public void testQuery5() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			// ���Բ���as��������
			List students = session.createQuery("select s.id, s.name from Student as s").list();
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
