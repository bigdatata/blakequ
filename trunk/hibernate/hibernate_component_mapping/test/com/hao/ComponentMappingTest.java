package com.hao;

import org.hibernate.Session;

import junit.framework.TestCase;

public class ComponentMappingTest extends TestCase {

	public void testSave1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			//建立User实体类
			User user = new User();
			user.setName("张三");
			
			//建立Contact值类，值类通常从属于实体类
			Contact userContact = new Contact();
			userContact.setEmail("email");
			userContact.setAddress("address");
			userContact.setZipCode("zipCode");
			userContact.setContactTel("contactTel");
			user.setUserContact(userContact);
			
			session.save(user);
			
			Employee em = new Employee();
			em.setName("李云");
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
