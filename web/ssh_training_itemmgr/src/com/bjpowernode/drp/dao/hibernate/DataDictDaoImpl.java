package com.bjpowernode.drp.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bjpowernode.drp.dao.DataDictDao;
import com.bjpowernode.drp.domain.ItemCategory;
import com.bjpowernode.drp.domain.ItemUnit;

public class DataDictDaoImpl extends HibernateDaoSupport implements DataDictDao {

	public List<ItemCategory> getItemCategoryList() {
		return getHibernateTemplate().find("from ItemCategory");
	}

	public List<ItemUnit> getItemUnitList() {
		return getHibernateTemplate().find("from ItemUnit");
	}

}
