package com.hao;

import java.util.Date;

import org.hibernate.Session;

public class InitData {
	public static void main(String[] args){
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			User user = new User();
			user.setName("ภ๏หน");
			user.setPassword("1234");
			user.setCreateTime(new Date());
			user.setExpireTime(new Date());
			session.save(user);
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}

}
