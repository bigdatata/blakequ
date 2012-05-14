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
public class SimpleObjectQueryTest2 extends TestCase {
	
	@SuppressWarnings("unchecked")
	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			/**
			 * ����list��ѯʵ�����ᷢ��һ����ѯ��䣬ȡ��ʵ���������
			 * 
			 * Hibernate: select student0_.id as id0_, student0_.name as name0_, 
			 * student0_.createTime as createTime0_, student0_.classesid as classesid0_ 
			 * from t_student student0_
			 */
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
			/**
			 * �����N+1���⣬��ν��N+1ָ���Ƿ�����N+1��sql���
			 * 
			 * 1:����һ����ѯid�б�����
			 * Hibernate: select student0_.id as col_0_0_ from t_student student0_
			 * 
			 * N:����id����N��sql��䣬������صĶ���
			 * Hibernate: select student0_.id as id0_0_, student0_.name as name0_0_, 
			 * student0_.createTime as createTime0_0_, student0_.classesid as classesid0_0_ 
			 * from t_student student0_ where student0_.id=?
			 * 
			 */
			Iterator iter = session.createQuery("from Student").iterate();
			while (iter.hasNext()) {
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
			
			List students = session.createQuery("from Student").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
			}
			System.out.println("-----------------------------------------------------");
			/**
			 * ������N+1����
			 * 
			 * ��Ϊִ��list������Ὣ���ݷŵ�session�Ļ����У�һ�����棩�����Բ���iterate��ʱ��
			 * ���Ȼᷢ��һ����ѯid�б����䣬�ٸ���id�������м�����Ӧ�����ݣ���������д�����֮ƥ�������
			 * ���ٷ�������id��ѯ��sql��䣬ֱ��ʹ�û����е�����
			 * 
			 * Iterate������������д������ݣ�������������ܣ��������N+1����
			 * 
			 */
			Iterator iter = session.createQuery("from Student").iterate();
			while (iter.hasNext()) {
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
			
			List students = session.createQuery("from Student").list();
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println(student.getName());
			}
			System.out.println("-----------------------------------------------------");
			
			/**
			 * �ٴ˷�����ѯ���
			 * 
			 * ��Ĭ������£�ÿ��ִ��list��ѯʵ����󶼻ᷢ����ѯ��䣬���������˲�ѯ����
			 * ��Ȼһ�������д���Student���ݣ���list���ã�������Ȼ������ѯ��䣬
			 * 
			 * ��ʵlist����ֻ�򻺴��з������ݣ��������û����е�����
			 */
			students = session.createQuery("from Student").list();
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
