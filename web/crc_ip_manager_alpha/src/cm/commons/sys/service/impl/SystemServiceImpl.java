package cm.commons.sys.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cm.commons.sys.dao.SystemDao;
import cm.commons.sys.service.SystemService;
import cm.commons.util.PageModel;
import cm.commons.exception.AppException;
import cm.commons.pojos.System;

public class SystemServiceImpl implements SystemService<Integer, System> {
	private static Log log = LogFactory.getLog(SystemServiceImpl.class);
	@Autowired
	private SystemDao systemDao;
	
	public void deleteByConfigKey(String configKey) {
		// TODO Auto-generated method stub
		log.debug("delete by config key "+this.getClass().getName());
		try {
			systemDao.deleteByConfigKey(configKey);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete by config key fail! "+this.getClass().getName(), e);
			throw new AppException("删除系统配置失败");
		}
	}
	public System getSystemConfigByKey(String configKey) {
		// TODO Auto-generated method stub
		log.debug("get system config by key "+this.getClass().getName());
		try {
			return systemDao.getSystemConfigByKey(configKey);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get system config by key fail! "+this.getClass().getName(), e);
			throw new AppException("获取系统配置失败");
		}
	}
	public void saveOrUpdateSystemConfigKey(System entity) {
		// TODO Auto-generated method stub
		log.debug("save or update system config "+this.getClass().getName());
		try {
			systemDao.saveOrUpdateSystemConfigKey(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update system config fail! "+this.getClass().getName(), e);
			throw new AppException("保存或更新系统配置失败");
		}
	}
	public void updateSystemConfigKey(System entity) {
		// TODO Auto-generated method stub
		log.debug("update system config "+this.getClass().getName());
		try {
			systemDao.updateSystemConfigKey(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update system config fail! "+this.getClass().getName(), e);
			throw new AppException("更新系统配置失败");
		}
	}
	
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete by id "+this.getClass().getName());
		try {
			systemDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete by id fail! "+this.getClass().getName(), e);
			throw new AppException("删除系统配置失败");
		}
	}
	public System get(Integer id) {
		// TODO Auto-generated method stub
		log.debug("get date "+this.getClass().getName());
		try {
			return (System) systemDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get date fail! "+this.getClass().getName(), e);
			throw new AppException("获取系统配置失败");
		}
	}
	public List<System> getAll() {
		// TODO Auto-generated method stub
		log.debug("get all date "+this.getClass().getName());
		try {
			return systemDao.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all date fail! "+this.getClass().getName(), e);
			throw new AppException("获取所有系统配置失败");
		}
	}
	public void save(System entity) {
		// TODO Auto-generated method stub
		log.debug("save date "+this.getClass().getName());
		try {
			systemDao.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save date fail! "+this.getClass().getName(), e);
			throw new AppException("存储系统配置失败");
		}
	}
	public void saveOrUpdate(System entity) {
		// TODO Auto-generated method stub
		log.debug("save or update "+this.getClass().getName());
		try {
			systemDao.saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update fail! "+this.getClass().getName(), e);
			throw new AppException("存储或更新系统配置失败");
		}
	}
	public void update(System entity) {
		// TODO Auto-generated method stub
		log.debug("update date "+this.getClass().getName());
		try {
			systemDao.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update date fail! "+this.getClass().getName(), e);
			throw new AppException("更新系统配置失败");
		}
	}
	public void delete(System entity) {
		// TODO Auto-generated method stub
		log.debug("delete by id "+this.getClass().getName());
		try {
			systemDao.delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete by id fail! "+this.getClass().getName(), e);
			throw new AppException("删除系统配置失败");
		}
	}
	public PageModel<System> getAll(String queryString, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	public void deleteItem(Integer[] ids) {
		// TODO Auto-generated method stub
		log.debug("delete item array"+this.getClass().getName());
		try {
			systemDao.deleteItem(ids);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete item array fail! "+this.getClass().getName(), e);
			throw new AppException("删除多个实体失败");
		}
	}
	
}
