package com.hao;

import org.hibernate.Session;

import junit.framework.TestCase;

public class ComponentMappingTest extends TestCase {

	public void testSave1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//����Userʵ����
			User user = new User();
			user.setName("����");
			
			//����Contactֵ�ֵ࣬��ͨ��������ʵ����
			Contact userContact = new Contact();
			userContact.setEmail("email");
			userContact.setAddress("address");
			userContact.setZipCode("zipCode");
			userContact.setContactTel("contactTel");
			user.setUserContact(userContact);
			
			session.save(user);
			
			Employee em = new Employee();
			em.setName("����");
			em.setEmployeeContact(userContact);
			session.save(em);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}	
	
	public void testLoad1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			User user = (User)session.load(User.class, 1);
			System.out.println(user.getName());
			System.out.println(user.getUserContact().getAddress());
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
}
