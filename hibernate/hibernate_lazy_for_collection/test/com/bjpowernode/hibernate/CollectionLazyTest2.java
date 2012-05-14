package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * 将<set>标签上的lazy设置为false,其他默认
 * @author Administrator
 *
 */
public class CollectionLazyTest2 extends TestCase {

	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//不会发出sql
			Classes classes = (Classes)session.load(Classes.class, 1);
			
			//会发出两条查询语句，分别查询班级和学生,集合内容也被查上来了
			System.out.println("classes.name=" + classes.getName());
			
			//不会发出sql
			Set students = classes.getStudents();
			
			//不会发出sql
			for (Iterator iter=students.iterator(); iter.hasNext();) {
				Student student = (Student)iter.next();
				System.out.println("student.name=" +student.getName());
			}
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
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//不会发出sql
			Classes classes = (Classes)session.load(Classes.class, 1);
			
			//会发出两条查询语句，分别查询班级和学生
			System.out.println("classes.name=" + classes.getName());
			
			//不会发出sql
			Set students = classes.getStudents();
			
			//不会发出sql
			System.out.println("count=" + students.size());
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
}
