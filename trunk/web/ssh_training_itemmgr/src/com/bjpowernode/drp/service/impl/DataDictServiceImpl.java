package com.bjpowernode.drp.service.impl;

import java.util.List;

import com.bjpowernode.drp.AppException;
import com.bjpowernode.drp.dao.DataDictDao;
import com.bjpowernode.drp.domain.ItemCategory;
import com.bjpowernode.drp.domain.ItemUnit;
import com.bjpowernode.drp.service.DataDictService;


/**
 * 管理数据字典
 * @author Administrator
 *
 */
public class DataDictServiceImpl implements DataDictService{

	private DataDictDao dataDictDao; 
	
	/**
	 * 取得物料类别代码列表
	 * @return
	 */
	public List<ItemCategory> getItemCategoryList() {
		try {
			return (List)dataDictDao.getItemCategoryList();
		}catch(Exception e) {
			e.printStackTrace();
			throw new AppException("查询物料类别失败！");
		}
	}
	
	/**
	 * 取得物单位列表
	 * @return
	 */
	public List<ItemUnit> getItemUnitList() {
		try {
			return (List)dataDictDao.getItemUnitList();
		}catch(Exception e) {
			e.printStackTrace();
			throw new AppException("查询物料单位失败！");
		}
	}

	public void setDataDictDao(DataDictDao dataDictDao) {
		this.dataDictDao = dataDictDao;
	}
	
}
