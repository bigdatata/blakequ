package cm.commons.sys.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cm.commons.dao.hiber.util.Element;
import cm.commons.exception.AppException;
import cm.commons.pojos.ComputerLog;
import cm.commons.sys.dao.ComputerLogDao;
import cm.commons.sys.service.ComputerLogService;
import cm.commons.util.PageModel;

public class ComputerLogServiceImpl implements ComputerLogService<Integer, ComputerLog> {
	private static Log log = LogFactory.getLog(ComputerLogServiceImpl.class);
	@Autowired
	private ComputerLogDao computerLogDao;
	
	
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			computerLogDao.deleteById(id);
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
			throw new AppException("获取所有数据");
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

	public void delete(ComputerLog entity) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			computerLogDao.delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除站点电脑日志失败");
		}
	}

	public List<ComputerLog> getAllSortByComputer() {
		// TODO Auto-generated method stub
		log.debug("get all computer log sort by computer id "+this.getClass().getName());
		try {
			return computerLogDao.getAllSortByComputer();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all computer log sort by computer id fail! "+this.getClass().getName(), e);
			throw new AppException("根据电脑排序获取日志失败");
		}
	}

	public List<ComputerLog> getAllSortByTime() {
		// TODO Auto-generated method stub
		log.debug("get all computer log sort by time "+this.getClass().getName());
		try {
			return computerLogDao.getAllSortByTime();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all computer log sort by time fail! "+this.getClass().getName(), e);
			throw new AppException("根据时间排序获取日志失败");
		}
	}

	public List<ComputerLog> getComputerLog(Integer computerId) {
		// TODO Auto-generated method stub
		log.debug("get all computer log by computer id "+this.getClass().getName());
		try {
			return computerLogDao.getComputerLog(computerId);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all computer log by computer id  fail! "+this.getClass().getName(), e);
			throw new AppException("根据电脑id获取日志失败");
		}
	}

	public List<ComputerLog> getComputerLogByStationNameOrId(String key) {
		// TODO Auto-generated method stub
		log.debug("get computer log by station name or id "+this.getClass().getName());
		try {
			return computerLogDao.getComputerLogByStationNameOrId(key);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get computer log by station name or id fail! "+this.getClass().getName(), e);
			throw new AppException("根据站点名字或id："+key+"获取日志失败");
		}
	}

	public PageModel<ComputerLog> getAll(String queryString, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		log.debug("get all data by page "+this.getClass().getName());
		try {
			return computerLogDao.getAll(queryString, pageNo, pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data by page fail! "+this.getClass().getName(), e);
			throw new AppException("按分页获取数据失败(按时间)");
		}
	}

	public PageModel<ComputerLog> getAllSortByComputer(String queryString,
			int pageNo, int pageSize) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all data by page "+this.getClass().getName());
		try {
			return computerLogDao.getAllSortByComputer(queryString, pageNo, pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data by page fail! "+this.getClass().getName(), e);
			throw new AppException("按分页获取数据失败(按站点)");
		}
	}
	public void deleteItem(Integer[] ids) {
		// TODO Auto-generated method stub
		log.debug("delete item array"+this.getClass().getName());
		try {
			computerLogDao.deleteItem(ids);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete item array fail! "+this.getClass().getName(), e);
			throw new AppException("删除多个实体失败");
		}
	}
	public PageModel<ComputerLog> getPagedWithCondition(
			List<Element> conditions, int pageNo, int pageSize) {
		try {
			PageModel pageModel = new PageModel();
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setList(computerLogDao.findPaged((pageNo-1) * pageSize, pageSize, conditions));
			pageModel.setTotalRecords((int)computerLogDao.getCounts(conditions));
			return pageModel;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new AppException("查询满足条件的电脑历史信息出错");
		}
	}
	
}
