package com.bjpowernode.drp.service.impl;

import java.util.List;

import com.bjpowernode.drp.AppException;
import com.bjpowernode.drp.dao.DataDictDao;
import com.bjpowernode.drp.domain.ItemCategory;
import com.bjpowernode.drp.domain.ItemUnit;
import com.bjpowernode.drp.service.DataDictService;


/**
 * ���������ֵ�
 * @author Administrator
 *
 */
public class DataDictServiceImpl implements DataDictService{

	private DataDictDao dataDictDao; 
	
	/**
	 * ȡ�������������б�
	 * @return
	 */
	public List<ItemCategory> getItemCategoryList() {
		try {
			return (List)dataDictDao.getItemCategoryList();
		}catch(Exception e) {
			e.printStackTrace();
			throw new AppException("��ѯ�������ʧ�ܣ�");
		}
	}
	
	/**
	 * ȡ���ﵥλ�б�
	 * @return
	 */
	public List<ItemUnit> getItemUnitList() {
		try {
			return (List)dataDictDao.getItemUnitList();
		}catch(Exception e) {
			e.printStackTrace();
			throw new AppException("��ѯ���ϵ�λʧ�ܣ�");
		}
	}

	public void setDataDictDao(DataDictDao dataDictDao) {
		this.dataDictDao = dataDictDao;
	}
	
}
