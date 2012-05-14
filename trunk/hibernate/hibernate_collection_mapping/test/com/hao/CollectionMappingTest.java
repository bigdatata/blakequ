package com.hao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;

import junit.framework.TestCase;

public class CollectionMappingTest extends TestCase {

	public void testSave1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			
			CollectionMapping cm = new CollectionMapping();
			cm.setName("Р§зг");
			cm.setArrayValues(new String[]{"good","bad","tird","nice"});
			List<String> listValues = new ArrayList<String>();
			listValues.add("good");
			listValues.add("bad");
			listValues.add("string");
			cm.setListValues(listValues);
			Map<Integer, String> mapValues = new HashMap<Integer, String>();
			mapValues.put(1, "value");
			mapValues.put(2, "valued");
			mapValues.put(3, "valuedd");
			cm.setMapValues(mapValues);
			Set<String> setValues = new HashSet<String>();
			setValues.add("ni");
			setValues.add("hao");
			setValues.add("zixue");
			cm.setSetValues(setValues);
			
			session.save(cm);
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

			CollectionMapping cm = (CollectionMapping) session.get(CollectionMapping.class, 1);
			System.out.println(cm.getArrayValues());
			System.out.println(cm.getListValues());
			System.out.println(cm.getMapValues());
			System.out.println(cm.getSetValues());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
}
