package com.hao;

import java.io.Serializable;

public class FiscalYearPeriodPK implements Serializable {
	
	//∫ÀÀ„ƒÍ
	private int fiscalYear;
	
	//∫ÀÀ„‘¬
	private int fiscalPeriod;

	public int getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(int fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public int getFiscalPeriod() {
		return fiscalPeriod;
	}

	public void setFiscalPeriod(int fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fiscalPeriod;
		result = prime * result + fiscalYear;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FiscalYearPeriodPK other = (FiscalYearPeriodPK) obj;
		if (fiscalPeriod != other.fiscalPeriod)
			return false;
		if (fiscalYear != other.fiscalYear)
			return false;
		return true;
	}

}
