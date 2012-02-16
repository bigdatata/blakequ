package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	private static SessionFactory factory;
	static {
		try{
			Configuration cofig = new Configuration().configure();
			factory = cofig.buildSessionFactory();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @return session factory
	 */
	public static SessionFactory getSessionFactory(){
		return factory;
	}
	
	/**
	 * @return session
	 */
	public static Session getSession(){
		return factory.openSession();
	}
	
	/**
	 * every time you must close session after use
	 * @param session close the session
	 */
	public static void closeSession(Session session){
		if(session != null)
			if(session.isOpen())
				session.close();
	}
}
