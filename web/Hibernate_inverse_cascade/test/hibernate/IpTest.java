package hibernate;

import hibernate.o2o.IdCard;
import hibernate.o2o.Person;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import junit.framework.TestCase;

public class IpTest extends TestCase {

	public void testAdd(){
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			
			Person p = new Person();
			p.setName("person1");
			
			IdCard i = new IdCard();
			i.setNumber("1231321");
			
			p.setIdCard(i);
			i.setPerson(p);
			session.save(p);
			session.save(i);
			
			session.beginTransaction().commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			HibernateUtils.closeSession(session);
		}
	}
	
	public void testDelete(){
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			
			//Person是父，IdCard是子
			Person p = (Person) session.load(Person.class, 1);
			session.delete(p);
			
//			IdCard i = (IdCard) session.load(IdCard.class, 3);
//			session.delete(i);
			
			session.beginTransaction().commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			HibernateUtils.closeSession(session);
		}
	}
	
	public void testUpdate(){
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			Person p = (Person) session.load(Person.class, 3);
			p.setIdCard(null);
			session.update(p);
			
			session.beginTransaction().commit();
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally{
			HibernateUtils.closeSession(session);
		}
	}

	
}
