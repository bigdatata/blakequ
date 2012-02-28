package cm.commons.stat.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cm.commons.exception.AppException;
import cm.commons.pojos.Computer;
import cm.commons.stat.dao.ComputerDao;
import cm.commons.stat.service.ComputerService;
import cm.commons.util.PageModel;

public class ComputerServiceImpl implements ComputerService<Integer, Computer> {
	private static Log log = LogFactory.getLog(ComputerServiceImpl.class);
	@Autowired
	private ComputerDao computerDao;

	public void deleteComputerByIp(String ip) {
		// TODO Auto-generated method stub
		log.debug("delete computer by ip:"+ip+" "+this.getClass().getName());
		try {
			computerDao.deleteComputerByIp(ip);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete computer by ip fail! "+this.getClass().getName(), e);
			throw new AppException("删除IP="+ip+"的站点电脑失败");
		}
	}

	public Computer getComputerByIp(String ip) {
		// TODO Auto-generated method stub
		log.debug("get computer by ip "+this.getClass().getName());
		try {
			return computerDao.getComputerByIp(ip);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get computer by ip fail! "+this.getClass().getName(), e);
			throw new AppException("获取IP="+ip+"的站点电脑失败");
		}
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			computerDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除站点电脑失败");
		}
	}

	public Computer get(Integer id) {
		// TODO Auto-generated method stub
		log.debug("get data "+this.getClass().getName());
		try {
			return (Computer) computerDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get data fail! "+this.getClass().getName(), e);
			throw new AppException("获取站点电脑失败");
		}
	}

	public List<Computer> getAll() {
		// TODO Auto-generated method stub
		log.debug("get all data "+this.getClass().getName());
		try {
			return computerDao.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data fail! "+this.getClass().getName(), e);
			throw new AppException("获取所有站点电脑失败");
		}
	}

	public void save(Computer entity) {
		// TODO Auto-generated method stub
		log.debug("save data "+this.getClass().getName());
		try {
			computerDao.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save data fail! "+this.getClass().getName(), e);
			throw new AppException("保存站点电脑信息失败");
		}
	}

	public void saveOrUpdate(Computer entity) {
		// TODO Auto-generated method stub
		log.debug("save or update data "+this.getClass().getName());
		try {
			computerDao.saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update data fail! "+this.getClass().getName(), e);
			throw new AppException("保存或更新站点电脑失败");
		}
	}

	public void update(Computer entity) {
		// TODO Auto-generated method stub
		log.debug("update data "+this.getClass().getName());
		try {
			computerDao.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update data fail! "+this.getClass().getName(), e);
			throw new AppException("更新站点电脑失败");
		}
	}

	public void delete(Computer entity) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			computerDao.delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除站点电脑失败");
		}
	}

	public Computer getComputerByStationId(Integer stationId) {
		// TODO Auto-generated method stub
		log.debug("get computer by station id"+this.getClass().getName());
		try {
			return (Computer) computerDao.getComputerByStationId(stationId);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get computer by station id fail! "+this.getClass().getName(), e);
			throw new AppException("根据车站id："+stationId+"获取电脑失败");
		}
	}

	public PageModel<Computer> getAll(String queryString, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
