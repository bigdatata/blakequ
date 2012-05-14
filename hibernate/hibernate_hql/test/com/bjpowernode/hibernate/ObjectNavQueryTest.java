package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.hao.HibernateUtils;
import com.hao.Student;

import junit.framework.TestCase;

/**
 * 对象导航查询
 * 在HQL中使用s.classes.name之类的
 * @author Administrator
 *
 */
public class ObjectNavQueryTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			List students = session.createQuery("from Student s where s.classes.name like '%2%'")
						.list();
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
