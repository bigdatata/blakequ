package test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bjpowernode.drp.HibernateUtils;
import com.bjpowernode.drp.domain.ItemCategory;
import com.bjpowernode.drp.domain.ItemUnit;

import junit.framework.TestCase;

public class DataDictTest extends TestCase {

	public void testSave1() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtils.getSession();
			tx = session.beginTransaction();
			
			ItemCategory c1 = new ItemCategory();
			c1.setId("A01");
			c1.setName("��ҩ");
			session.save(c1);
			ItemCategory c2 = new ItemCategory();
			c2.setId("A02");
			c2.setName("��ҩ");
			session.save(c2);
			
			ItemUnit u1 = new ItemUnit();
			u1.setId("B01");
			u1.setName("����");
			session.save(u1);
			
			ItemUnit u2 = new ItemUnit();
			u2.setId("B02");
			u2.setName("��");
			session.save(u2);
			
			tx.commit();
		}catch(Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
		
}
