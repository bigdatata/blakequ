package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;

import junit.framework.TestCase;

/**
 * ����Ĭ��
 * @author Administrator
 *
 */
public class CollectionLazyTest1 extends TestCase {

	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//���ᷢ��sql
			Classes classes = (Classes)session.load(Classes.class, 1);
			
			//�ᷢ��sql
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

	public void testLoad2() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			//���ᷢ��sql
			Classes classes = (Classes)session.load(Classes.class, 1);
			
			//�ᷢ��sql
			System.out.println("classes.name=" + classes.getName());
			
			//���ᷢ��sql,��Ϊ����һ������
			Set students = classes.getStudents();
			
			//�ᷢ����ѯ�ð༶ȫ��ѧ����sql���,����Ч������
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