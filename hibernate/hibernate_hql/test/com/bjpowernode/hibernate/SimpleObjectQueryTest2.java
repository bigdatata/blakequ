package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.hao.HibernateUtils;
import com.hao.Student;

import junit.framework.TestCase;

/**
 * 实体对象查询
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
			 * 采用list查询实体对象会发出一条查询语句，取得实体对象数据
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
			 * 会出现N+1问题，所谓的N+1指的是发出了N+1条sql语句
			 * 
			 * 1:发出一条查询id列表的语句
			 * Hibernate: select student0_.id as col_0_0_ from t_student student0_
			 * 
			 * N:根据id发出N条sql语句，加载相关的对象
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
			 * 避免了N+1问题
			 * 
			 * 因为执行list操作后会将数据放到session的缓存中（一级缓存），所以采用iterate的时候
			 * 首先会发出一条查询id列表的语句，再根据id到缓存中加载相应的数据，如果缓存中存在与之匹配的数据
			 * 则不再发出根据id查询的sql语句，直接使用缓存中的数据
			 * 
			 * Iterate方法如果缓存中存在数据，它可以提高性能，否则出现N+1问题
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
			 * 再此发出查询语句
			 * 
			 * 在默认情况下，每次执行list查询实体对象都会发出查询语句，除非配置了查询缓存
			 * 虽然一级缓存中存在Student数据，但list不用，所以仍然发出查询语句，
			 * 
			 * 其实list就是只向缓存中放入数据，而不利用缓存中的数据
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
