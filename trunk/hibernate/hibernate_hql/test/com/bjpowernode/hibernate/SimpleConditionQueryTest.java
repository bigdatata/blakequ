package com.bjpowernode.hibernate;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hao.HibernateUtils;

import junit.framework.TestCase;

/**
 * ������ѯ
 * @author Administrator
 *
 */
public class SimpleConditionQueryTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//����ƴ��
			List students = session.createQuery("select s.id, s.name from Student s where s.name like '%0%'").list();
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
			//����ʹ�ã���ʽ���ݲ���
			//������0��ʼ����ͬ��jdbc��1��ʼ
			//ֵ��ʹ�õ�����������
			//List students = session.createQuery("select s.id, s.name from Student s where s.name like ?").list();
//			Query query  = session.createQuery("select s.id, s.name from Student s where s.name like ?");
//			query.setParameter(0, "%0%");
//			List students = query.list();
			
			//��������̣�������ô��ַ�ʽ
			List students = session.createQuery("select s.id, s.name from Student s where s.name like ?")
					.setParameter(0, "%0%")
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
			//���Բ��� :������ �ķ�ʽ���ݲ���
			List students = session.createQuery("select s.id, s.name from Student s where s.name like :myname")
					.setParameter("myname",  "%0%")
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
	public void testQuery4() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//���� ����ʽ����ѯѧ��Ϊ1,2,3,4,5��ѧ��
			List students = session.createQuery("select s.id, s.name from Student s where s.id in(?, ?, ?, ?, ?)")
					.setParameter(0, 1)
					.setParameter(1, 2)
					.setParameter(2, 3)
					.setParameter(3, 4)
					.setParameter(4, 5)
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
	public void testQuery5() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//���� :������ ��ʽ����ѯѧ��Ϊ1,2,3,4,5��ѧ��
			List students = session.createQuery("select s.id, s.name from Student s where s.id in(:ids)")
					.setParameterList("ids", new Object[]{1, 2, 3, 4, 5})
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
	public void testQuery6() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//��ѯ2009-08��ѧ�������Ե���mysql�����ڸ�ʽ������
			List students = session.createQuery("select s.id, s.name from Student s where date_format(s.createTime, '%Y-%m')=?")
					.setParameter(0,  "2009-08")
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
	public void testQuery7() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//��һ���Ĵ�Сд�ǹ̶���
			//��ѯ2009-08-01 ��2009-08-20��ѧ�������Ե���mysql�����ڸ�ʽ������
			List students = session.createQuery("select s.id, s.name from Student s where s.createTime between ? and ?")
					.setParameter(0, sdf.parse("2009-08-01 00:00:00"))
					.setParameter(1, sdf.parse("2009-08-20 23:59:59"))
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
