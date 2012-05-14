package quhao;

import java.util.List;

import javax.transaction.SystemException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Test {
	public static void main(String[] args) throws IllegalStateException, SystemException{
		Session session = null;
		Transaction trans = null;
		try{
			Configuration cofig = new Configuration().configure();
			SessionFactory sf = cofig.buildSessionFactory();
			session = sf.openSession();
			
			UserId id = new UserId();
			id.setFirstname("Simith");
			id.setLastname("Liyn");
//			User user = (User) session.load(User.class, id);
			User u = new User(id, 23);
			session.save(u);
//			session.flush();
			trans = (Transaction) session.beginTransaction(); 
			List l = session.createQuery("from User where age = 13").list();
			trans.commit();
			for(int i = 0; i<l.size(); i++){
				User u1 = (User) l.get(i);
				System.out.println(u1);
			}
			System.out.println("over");
		}catch(Exception e){
			if(trans != null){
				trans.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
}
