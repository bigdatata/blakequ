package cm.commons.stat.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.exception.AppException;
import cm.commons.pojos.Port;
import cm.commons.stat.dao.PortDao;
import cm.commons.stat.service.PortService;
import cm.commons.util.PageModel;

public class PortServiceImpl implements PortService<Integer, Port> {
	private static Log log = LogFactory.getLog(PortServiceImpl.class);
	private PortDao portDao;
	

	public void setPortDao(PortDao portDao) {
		this.portDao = portDao;
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			portDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除端口数据失败");
		}
	}

	public Port get(Integer id) {
		// TODO Auto-generated method stub
		log.debug("get data "+this.getClass().getName());
		try {
			return (Port) portDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get data fail! "+this.getClass().getName(), e);
			throw new AppException("获取端口数据失败");
		}
	}

	public List<Port> getAll() {
		// TODO Auto-generated method stub
		log.debug("get all data "+this.getClass().getName());
		try {
			return portDao.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data fail! "+this.getClass().getName(), e);
			throw new AppException("获取所有端口数据失败");
		}
	}

	public void save(Port entity) {
		// TODO Auto-generated method stub
		log.debug("save data "+this.getClass().getName());
		try {
			portDao.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save data fail! "+this.getClass().getName(), e);
			throw new AppException("存储端口数据失败");
		}
	}

	public void saveOrUpdate(Port entity) {
		// TODO Auto-generated method stub
		log.debug("save or update "+this.getClass().getName());
		try {
			portDao.saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update fail! "+this.getClass().getName(), e);
			throw new AppException("存储或更新端口数据失败");
		}
	}

	public void update(Port entity) {
		// TODO Auto-generated method stub
		log.debug("update data "+this.getClass().getName());
		try {
			portDao.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update data fail! "+this.getClass().getName(), e);
			throw new AppException("更新端口数据失败");
		}
	}

	public void delete(Port entity) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			portDao.delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除端口数据失败");
		}
	}

	public PageModel<Port> getAll(String queryString, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
