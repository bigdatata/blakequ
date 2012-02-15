package cm.commons.sys.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.exception.AppException;
import cm.commons.pojos.ComputerLog;
import cm.commons.service.basic.BasicServiceAbstImpl;
import cm.commons.sys.dao.ComputerLogDao;
import cm.commons.sys.service.ComputerLogService;

public class ComputerLogServiceImpl implements ComputerLogService<Integer, ComputerLog> {
	private static Log log = LogFactory.getLog(ComputerLogServiceImpl.class);
	private ComputerLogDao computerLogDao;
	public void setComputerLogDao(ComputerLogDao computerLogDao) {
		this.computerLogDao = computerLogDao;
	}
	
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			computerLogDao.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除站点电脑日志失败");
		}
		
	}
	public ComputerLog get(Integer id) {
		// TODO Auto-generated method stub
		log.debug("get data "+this.getClass().getName());
		try {
			return (ComputerLog) computerLogDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get data fail! "+this.getClass().getName(), e);
			throw new AppException("获取站点电脑日志失败");
		}
	}
	public List<ComputerLog> getAll() {
		// TODO Auto-generated method stub
		log.debug("get all data "+this.getClass().getName());
		try {
			return computerLogDao.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data fail! "+this.getClass().getName(), e);
			throw new AppException("");
		}
	}
	public void save(ComputerLog entity) {
		// TODO Auto-generated method stub
		log.debug("save data "+this.getClass().getName());
		try {
			computerLogDao.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save data fail! "+this.getClass().getName(), e);
			throw new AppException("存储站点电脑日志失败");
		}
	}
	public void saveOrUpdate(ComputerLog entity) {
		// TODO Auto-generated method stub
		log.debug("save or update data "+this.getClass().getName());
		try {
			computerLogDao.saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update data fail! "+this.getClass().getName(), e);
			throw new AppException("存储或更新站点电脑失败");
		}
	}
	public void update(ComputerLog entity) {
		// TODO Auto-generated method stub
		log.debug("update data "+this.getClass().getName());
		try {
			computerLogDao.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update data fail! "+this.getClass().getName(), e);
			throw new AppException("更新站点电脑失败");
		}
	}
	
}
