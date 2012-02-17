package cm.commons.stat.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.exception.AppException;
import cm.commons.pojos.Station;
import cm.commons.stat.dao.StationDao;
import cm.commons.stat.service.StationService;

public class StationServiceImpl implements StationService<Integer, Station> {

	private static Log log = LogFactory.getLog(StationServiceImpl.class);
	private StationDao stationDao;

	public void setStationDao(StationDao stationDao) {
		this.stationDao = stationDao;
	}


	public int getErrorStationCount() {
		// TODO Auto-generated method stub
		log.debug("get error station count "+this.getClass().getName());
		try {
			return stationDao.getErrorStationCount();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get error station count fail! "+this.getClass().getName(), e);
			throw new AppException("获取错误站点个数失败");
		}
	}

	public List<Station> getMainStaion() {
		// TODO Auto-generated method stub
		log.debug("get main station"+this.getClass().getName());
		try {
			return stationDao.getMainStaion();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get main station fail! "+this.getClass().getName(), e);
			throw new AppException("获取主站点失败");
		}
	}

	public int getStationCount() {
		// TODO Auto-generated method stub
		log.debug("get station count "+this.getClass().getName());
		try {
			return stationDao.getStationCount();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get station count fail! "+this.getClass().getName(), e);
			throw new AppException("获取站点数量失败");
		}
	}

	public int getUnknowStationCount() {
		// TODO Auto-generated method stub
		log.debug("get unknow station count "+this.getClass().getName());
		try {
			return stationDao.getUnknowStationCount();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get unknow station count fail! "+this.getClass().getName(), e);
			throw new AppException("获取未知状态站点个数失败");
		}
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			stationDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除数据失败");
		}
	}

	public Station get(Integer id) {
		// TODO Auto-generated method stub
		log.debug("get data "+this.getClass().getName());
		try {
			return (Station) stationDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get data fail! "+this.getClass().getName(), e);
			throw new AppException("获取数据失败");
		}
	}

	public List<Station> getAll() {
		// TODO Auto-generated method stub
		log.debug("get all data "+this.getClass().getName());
		try {
			return stationDao.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data fail! "+this.getClass().getName(), e);
			throw new AppException("获取所有数据失败");
		}
	}

	public void save(Station entity) {
		// TODO Auto-generated method stub
		log.debug("save data "+this.getClass().getName());
		try {
			stationDao.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save data fail!　"+this.getClass().getName(), e);
			throw new AppException("存储数据失败");
		}
	}

	public void saveOrUpdate(Station entity) {
		// TODO Auto-generated method stub
		log.debug("save or update data "+this.getClass().getName());
		try {
			stationDao.saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update data fail!　"+this.getClass().getName(), e);
			throw new AppException("保存或更新数据失败");
		}
	}

	public void update(Station entity) {
		// TODO Auto-generated method stub
		log.debug("update data "+this.getClass().getName());
		try {
			stationDao.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update data fail!　"+this.getClass().getName(), e);
			throw new AppException("更新数据失败");
		}
	}


	public void delete(Station entity) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			stationDao.delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除数据失败");
		}
	}

	

}
