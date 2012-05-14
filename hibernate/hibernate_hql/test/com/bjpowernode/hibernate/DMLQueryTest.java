package com.bjpowernode.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.hao.HibernateUtils;
import com.hao.Student;

import junit.framework.TestCase;

/**
 * DML(���ݲ�������)���Ĳ���
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
			
			//���ݿ���и����ˣ������治����
			//����һ�㲻����ʹ�ã������б��룬��������������
			/*
			 * Ӧ ���������ã���Ϊ�ͻ��治ͬ����Ҳ����˵��������ִ����������֮ǰ��
			 * �Ѿ���student��װ��һ��list�������˳�������ִ����������� student�еı�������ݸ��£�
			 * Ȼ����list��Student�����ʱ��list�Ǵӻ�����ȡ�����ݣ������Ǵӱ����ҵ������ݣ�Ҳ���� ˵��
			 * list�õ�����updateǰ�����ݣ�������������ֲ�ͬ�����������ַ�������á�
			 * ���������Ҳ���Կ��ó�hibernate���������ھۼ��ԣ�ͳ�ƣ����������ĸ��£�ɾ���Ȳ���
			 */
			session.createQuery("update Student s set s.name=? where s.id<?")
					.setParameter(0, "����")
					.setParameter(1, 5)
					.executeUpdate();
			
			//student = (Student)session.load(Student.class, 1);
			//���ڿ�ʼ�Ѿ������ݿ�ȡ�������ˣ����ڻ����У���ȡ��ʱ��ʹӻ���ȡ�������沢û���£��Ӷ����ȡ�����ݴ���
			//���������load��û�д����������Ϊ�ô�����δȡ���ݣ����Եȵ�ȡ��ʱ�������ֵ
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
