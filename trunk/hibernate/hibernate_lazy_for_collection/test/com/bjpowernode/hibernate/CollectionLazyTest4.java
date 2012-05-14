package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * ��<class>�ϵ�lazy����Ϊfalse������Ĭ��
 * @author Administrator
 *
 */
public class CollectionLazyTest4 extends TestCase {

	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//�ᷢ��sql,�����ϲ���ѯ(class��lazy��Ӱ�켯��)
			Classes classes = (Classes)session.load(Classes.class, 1);
			
			//���ᷢ��sql
			System.out.println("classes.name=" + classes.getName());
			
			//���ᷢ��sql
			Set students = classes.getStudents();
			
			//�ᷢ��sql
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
