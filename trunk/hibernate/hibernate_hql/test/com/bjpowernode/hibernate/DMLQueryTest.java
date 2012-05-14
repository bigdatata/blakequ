package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.hao.HibernateUtils;
import com.hao.Student;

import junit.framework.TestCase;

/**
 * DML(数据操作语言)风格的操作
 * @author Administrator
 *
 */
public class DMLQueryTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testQuery1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			@SuppressWarnings("unused")
			//Student student = (Student)session.load(Student.class, 1);
			Student student = (Student)session.get(Student.class, 1);
			
			//数据库表中更新了，但缓存不更新
			//所以一般不建议使用，除非有必须，如遇到性能问题
			/*
			 * 应 当尽量少用，因为和缓存不同步，也就是说，假如在执行下面的语句之前，
			 * 已经把student封装成一个list曾经拿了出来，再执行下面的语句对 student中的表进行数据更新，
			 * 然后再list　Student表，则此时的list是从缓存中取的数据，而不是从表中找到的数据，也就是 说，
			 * list拿到的是update前的数据，所以造成了这种不同步，所以这种风格尽量少用。
			 * 从这个方面也可以看得出hibernate并不适用于聚集性，统计，大量批量的更新，删除等操作
			 */
			session.createQuery("update Student s set s.name=? where s.id<?")
					.setParameter(0, "王五")
					.setParameter(1, 5)
					.executeUpdate();
			
			//student = (Student)session.load(Student.class, 1);
			//由于开始已经从数据库取出数据了，放在缓存中，在取的时候就从缓存取，而缓存并没更新，从而造成取出数据错误
			//但是上面的load并没有此种情况，因为用代理，并未取数据，所以等到取得时候是真的值
			student = (Student)session.get(Student.class, 1);
			System.out.println("student.name=" + student.getName());
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}
		
}
