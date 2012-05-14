package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.hao.HibernateUtils;

import junit.framework.TestCase;

/**
 * ͳ�Ʋ�ѯ
 * @author Administrator
 *
 */
public class StatQueryTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
//			List list = session.createQuery("select count(*) from Student").list();
//			Long count = (Long)list.get(0);
			
			//uniqueResult�����÷���ֵֻ��һ����������Ͳ�����list���ܷ��㣬������ض����ǲ��е�
			Long count = (Long)session.createQuery("select count(*) from Student").uniqueResult();
			//uniqueResult����ʹ��
//			Long count = (Long)session.createQuery("select count(*) from Student")
//					.setMaxResults(1)
//					.uniqueResult();
			System.out.println("count=" + count);
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
			//ÿ���༶�ж���ѧ��
			//join c.students�����ϲ�ѯѧ��������group by c.name��name����
			String hql = "select c.name, count(s) from Classes c join c.students s group by c.name order by c.name";
			List students = session.createQuery(hql).list();
			for (int i=0; i<students.size(); i++) {
				Object[] obj = (Object[])students.get(i);
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
