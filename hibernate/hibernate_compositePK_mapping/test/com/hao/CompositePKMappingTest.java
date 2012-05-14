package com.hao;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Session;

public class CompositePKMappingTest extends TestCase {

	public void testSave1() {
		Session session = null;
		try {
			session = HibernateUtils.getSession();
			session.beginTransaction();
			FiscalYearPeriod fiscalYearPeriod = new FiscalYearPeriod();
			//构造复合主键对象
			FiscalYearPeriodPK fiscalYearPeriodPK = new FiscalYearPeriodPK();
			fiscalYearPeriodPK.setFiscalYear(2009);
			fiscalYearPeriodPK.setFiscalPeriod(12);
			fiscalYearPeriod.setFiscalYearPeriodPK(fiscalYearPeriodPK);
			
			fiscalYearPeriod.setBeginDate(new Date());
			fiscalYearPeriod.setEndDate(new Date());
			fiscalYearPeriod.setPeriodSts("Y");
			
			session.save(fiscalYearPeriod);
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

			//构造复合主键对象
			FiscalYearPeriodPK fiscalYearPeriodPK = new FiscalYearPeriodPK();
			fiscalYearPeriodPK.setFiscalYear(2009);
			fiscalYearPeriodPK.setFiscalPeriod(12);
			
			FiscalYearPeriod fiscalYearPeriod = (FiscalYearPeriod)session.load(FiscalYearPeriod.class, fiscalYearPeriodPK);
			System.out.println(fiscalYearPeriod.getPeriodSts());
			
			session.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateUtils.closeSession(session);
		}
	}		
}
