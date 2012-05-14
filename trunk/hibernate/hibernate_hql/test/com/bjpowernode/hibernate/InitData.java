package com.bjpowernode.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;

import com.hao.Classes;
import com.hao.HibernateUtils;
import com.hao.Student;

public class InitData {

	public static void main(String[] args) {
			Session session = HibernateUtils.getSession();

			try {
				session.beginTransaction();

				for(int i=0; i<10; i++){
				
					Classes classes = new Classes();
					classes.setName("�༶"+i);
					session.save(classes);
					
					for(int j=0; j<10; j++){
						Student student = new Student();
						student.setName("�༶"+i+"��ѧ��"+j);
						student.setCreateTime(randomDate("2009-07-01","2009-09-01"));
						
						//���ڴ��н�����studentָ��classes������
						student.setClasses(classes);
						session.save(student);
					}
				}
				
				for(int i=0; i<5; i++){
					Classes classes = new Classes();
					classes.setName("��ѧ���༶"+i);
					session.save(classes);
				}
				
				for(int i=0; i<10; i++){
					Student student = new Student();
					student.setName("��ҵ����"+i);
					session.save(student);
				}
				
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
			} finally{
				HibernateUtils.closeSession(session);
			}
		}	
		
		/**
		 * ��ȡ�������
		 * @param beginDate ��ʼ���ڣ���ʽΪ��yyyy-MM-dd
		 * @param endDate �������ڣ���ʽΪ��yyyy-MM-dd
		 * @return
		 */
		private static Date randomDate(String beginDate,String endDate){
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date start = format.parse(beginDate);
				Date end = format.parse(endDate);
				
				if(start.getTime() >= end.getTime()){
					return null;
				}
				
				long date = random(start.getTime(),end.getTime());
				
				return new Date(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		private static long random(long begin,long end){
			long rtn = begin + (long)(Math.random() * (end - begin));
			if(rtn == begin || rtn == end){
				return random(begin,end);
			}
			return rtn;
		}
}
