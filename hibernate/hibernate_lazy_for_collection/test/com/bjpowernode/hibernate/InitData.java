package com.bjpowernode.hibernate;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;

public class InitData {

	public static void main(String[] args) {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();

			Classes classes = new Classes();
			classes.setName("�����ڵ�");
			session.save(classes);
			
			Student student1 = new Student();
			student1.setName("����");
			student1.setClasses(classes);
			session.save(student1);
			
			Student student2 = new Student();
			student2.setName("����");
			student2.setClasses(classes);
			session.save(student2);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}

	}

}
