package com.bjpowernode.drp.service;

import java.util.List;

import com.bjpowernode.drp.domain.ItemCategory;
import com.bjpowernode.drp.domain.ItemUnit;

public interface DataDictService {
	/**
	 * ȡ�������������б�
	 * @return
	 */
	public List<ItemCategory> getItemCategoryList();

	/**
	 * ȡ���ﵥλ�б�
	 * @return
	 */
	public List<ItemUnit> getItemUnitList();
}
