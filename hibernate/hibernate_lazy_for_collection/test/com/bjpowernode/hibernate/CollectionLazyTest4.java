package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * 将<class>上的lazy设置为false，其他默认
 * @author Administrator
 *
 */
public class CollectionLazyTest4 extends TestCase {

	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//会发出sql,但集合不查询(class的lazy不影响集合)
			Classes classes = (Classes)session.load(Classes.class, 1);
			
			//不会发出sql
			System.out.println("classes.name=" + classes.getName());
			
			//不会发出sql
			Set students = classes.getStudents();
			
			//会发出sql
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
}
