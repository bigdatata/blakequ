package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.hao.HibernateUtils;
import com.hao.Student;

import junit.framework.TestCase;

/**
 * ʵ������ѯ
 * @author Administrator
 *
 */
public class SimpleObjectQueryTest1 extends TestCase {
	
	@SuppressWarnings("unchecked")
	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//����Student����ļ���
			//���Ժ�select�ؼ���
			List students = session.createQuery("from Student").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
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
			//����Student����ļ���
			//���Ժ�select�ؼ��֣����Լ������
			List students = session.createQuery("from Student s").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
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
			//����Student����ļ���
			//���Ժ�select�ؼ��֣����Բ���as��������
			List students = session.createQuery("from Student as s").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
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
	public void testQuery4() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//����Student����ļ���
			//���ʹ��select��ѯʵ����󣬱���ʹ�ñ���
			List students = session.createQuery("select s from Student s").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
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
	public void testQuery5() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//��֧��select * from .....,�����﷨
			List students = session.createQuery("select * from Student s").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
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
