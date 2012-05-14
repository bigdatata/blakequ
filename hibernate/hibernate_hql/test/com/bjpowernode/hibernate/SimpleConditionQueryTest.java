package com.bjpowernode.hibernate;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hao.HibernateUtils;

import junit.framework.TestCase;

/**
 * 条件查询
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
			//可以拼串
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
			//可以使用？方式传递参数
			//索引从0开始，不同于jdbc从1开始
			//值能使用单引号引起来
			//List students = session.createQuery("select s.id, s.name from Student s where s.name like ?").list();
//			Query query  = session.createQuery("select s.id, s.name from Student s where s.name like ?");
//			query.setParameter(0, "%0%");
//			List students = query.list();
			
			//方法链编程，建议采用此种方式
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
			//可以采用 :参数名 的方式传递参数
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
			//采用 ？方式，查询学号为1,2,3,4,5的学生
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
			//采用 :参数名 方式，查询学号为1,2,3,4,5的学生
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
			//查询2009-08的学生，可以调用mysql的日期格式化函数
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这一它的大小写是固定的
			//查询2009-08-01 到2009-08-20的学生，可以调用mysql的日期格式化函数
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
