package com.hao;

import java.util.Date;
  
/**
 * 核算期间
 * @author wangy
 *
 */
public class FiscalYearPeriod {
	
	private FiscalYearPeriodPK fiscalYearPeriodPK;
	
	//开始日期
	private Date beginDate;
	
	//结束日期
	private Date endDate;
	
	//状态
	private String periodSts;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPeriodSts() {
		return periodSts;
	}

	public void setPeriodSts(String periodSts) {
		this.periodSts = periodSts;
	}

	public FiscalYearPeriodPK getFiscalYearPeriodPK() {
		return fiscalYearPeriodPK;
	}

	public void setFiscalYearPeriodPK(FiscalYearPeriodPK fiscalYearPeriodPK) {
		this.fiscalYearPeriodPK = fiscalYearPeriodPK;
	}
}
