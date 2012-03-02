package cm.commons.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cm.commons.exception.AppException;
import cm.commons.pojos.Warn;
import cm.commons.sys.dao.WarnDao;
import cm.commons.sys.service.WarnService;
import cm.commons.util.PageModel;

public class WarnServiceImpl implements WarnService<Integer, Warn> {
	private static Log log = LogFactory.getLog(WarnServiceImpl.class);
	@Autowired
	private WarnDao warnDao;
	
	public void deleteByState(int warnState) {
		// TODO Auto-generated method stub
		log.debug("delete by state "+this.getClass().getName());
		try {
			warnDao.deleteByState(warnState);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete by state fail! "+this.getClass().getName(), e);
			throw new AppException("删除状态为"+warnState+"的告警失败");
		}
	}
	public void deleteByStation(int stationId) {
		// TODO Auto-generated method stub
		log.debug("delete by station "+this.getClass().getName());
		try {
			warnDao.deleteByStation(stationId);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete by station fail! "+this.getClass().getName(), e);
			throw new AppException("删除站点"+stationId+"告警失败");
		}
		
	}
	public void deleteByTime(Date beginTime, Date endTime) {
		// TODO Auto-generated method stub
		log.debug("delete by time "+this.getClass().getName());
		try {
			warnDao.deleteByTime(beginTime, endTime);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete by time fail! "+this.getClass().getName(), e);
			throw new AppException("删除告警失败");
		}
	}
	public List<Warn> getWarnByDate(Date beginTime, Date endTime) {
		// TODO Auto-generated method stub
		log.debug("get warn by date "+this.getClass().getName());
		try {
			return warnDao.getWarnByDate(beginTime, endTime);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get warn by date fail! "+this.getClass().getName(), e);
			throw new AppException("获取告警失败");
		}
	}
	public List<Warn> getWarnByState(int warnState) {
		// TODO Auto-generated method stub
		log.debug("get warn by state "+this.getClass().getName());
		try {
			return warnDao.getWarnByState(warnState);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get warn by state fail! "+this.getClass().getName(), e);
			throw new AppException("获取告警失败");
		}
	}
	public List<Warn> getWarnByStation(int stationId) {
		// TODO Auto-generated method stub
		log.debug("get warn by station "+this.getClass().getName());
		try {
			return warnDao.getWarnByStation(stationId);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get warn by station fail! "+this.getClass().getName(), e);
			throw new AppException("获取告警失败");
		}
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			warnDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除告警失败");
		}
	}

	public Warn get(Integer id) {
		// TODO Auto-generated method stub
		log.debug("get data "+this.getClass().getName());
		try {
			return (Warn) warnDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get data fail! "+this.getClass().getName(), e);
			throw new AppException("获取告警失败");
		}
	}

	public List<Warn> getAll() {
		// TODO Auto-generated method stub
		log.debug("get all data "+this.getClass().getName());
		try {
			return warnDao.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data fail! "+this.getClass().getName(), e);
			throw new AppException("获取所有告警失败");
		}
	}

	public void save(Warn entity) {
		// TODO Auto-generated method stub
		log.debug("save data "+this.getClass().getName());
		try {
			warnDao.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save data fail! "+this.getClass().getName(), e);
			throw new AppException("存储告警失败");
		}
	}

	public void saveOrUpdate(Warn entity) {
		// TODO Auto-generated method stub
		log.debug("save data "+this.getClass().getName());
		try {
			warnDao.saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update data fail! "+this.getClass().getName(), e);
			throw new AppException("存储或更新告警失败");
		}
	}

	public void update(Warn entity) {
		// TODO Auto-generated method stub
		log.debug("update data "+this.getClass().getName());
		try {
			warnDao.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update data fail! "+this.getClass().getName(), e);
			throw new AppException("更新告警失败");
		}
	}

	public void delete(Warn entity) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			warnDao.delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除告警失败");
		}
	}

	/**
	 * 分页查询，可以通过告警内容关键字查询告警
	 */
	public PageModel<Warn> getAll(String queryString, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		log.debug("get all data by page "+this.getClass().getName());
		try {
			return warnDao.getAll(queryString, pageNo, pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data by page fail! "+this.getClass().getName(), e);
			throw new AppException("通过分页获取所有数据失败");
		}
	}
	public void deleteItem(Integer[] ids) {
		// TODO Auto-generated method stub
		log.debug("delete item array"+this.getClass().getName());
		try {
			warnDao.deleteItem(ids);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete item array fail! "+this.getClass().getName(), e);
			throw new AppException("删除多个实体失败");
		}
	}
	

}
