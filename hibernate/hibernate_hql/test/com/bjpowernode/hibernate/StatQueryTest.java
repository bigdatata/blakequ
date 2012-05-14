package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.hao.HibernateUtils;

import junit.framework.TestCase;

/**
 * 统计查询
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
			
			//uniqueResult它适用返回值只有一条的情况，就不返回list，很方便，如果返回多条是不行的
			Long count = (Long)session.createQuery("select count(*) from Student").uniqueResult();
			//uniqueResult改造使用
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
			//每个班级有多少学生
			//join c.students是联合查询学生数，以group by c.name及name分组
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
