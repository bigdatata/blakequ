package cm.commons.stat.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cm.commons.exception.AppException;
import cm.commons.pojos.Segment;
import cm.commons.pojos.Station;
import cm.commons.stat.dao.SegmentDao;
import cm.commons.stat.service.SegmentService;
import cm.commons.util.PageModel;

public class SegmentServiceImpl implements SegmentService<Integer, Segment> {
	private static Log log = LogFactory.getLog(SegmentServiceImpl.class);
	@Autowired
	private SegmentDao segmentDao;


	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			segmentDao.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除数据失败");
		}
	}

	public Segment get(Integer id) {
		// TODO Auto-generated method stub
		log.debug("get data "+this.getClass().getName());
		try {
			return (Segment) segmentDao.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get data fail! "+this.getClass().getName(), e);
			throw new AppException("获取数据失败");
		}
	}

	public List<Segment> getAll() {
		// TODO Auto-generated method stub
		log.debug("get all data "+this.getClass().getName());
		try {
			return segmentDao.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data fail! "+this.getClass().getName(), e);
			throw new AppException("获取所有数据失败");
		}
	}

	public void save(Segment entity) {
		// TODO Auto-generated method stub
		log.debug("save data "+this.getClass().getName());
		try {
			segmentDao.save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save data fail! "+this.getClass().getName(), e);
			throw new AppException("保存数据失败");
		}
	}

	public void saveOrUpdate(Segment entity) {
		// TODO Auto-generated method stub
		log.debug("save or update data "+this.getClass().getName());
		try {
			segmentDao.saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update data fail! "+this.getClass().getName(), e);
			throw new AppException("保存或更新数据失败");
		}
	}

	public void update(Segment entity) {
		// TODO Auto-generated method stub
		log.debug("update data "+this.getClass().getName());
		try {
			segmentDao.update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update data fail! "+this.getClass().getName(), e);
			throw new AppException("更新数据失败");
		}
	}

	public void delete(Segment entity) {
		// TODO Auto-generated method stub
		log.debug("delete data "+this.getClass().getName());
		try {
			segmentDao.delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete data fail! "+this.getClass().getName(), e);
			throw new AppException("删除数据失败");
		}
	}

	public List<Segment> getAllSegmentByRoute(Integer routeId) {
		// TODO Auto-generated method stub
		log.debug("get all segment from route "+this.getClass().getName());
		try {
			List<Segment> segments = segmentDao.getAllSegmentByRoute(routeId);
			return segments;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all segment from route fail! "+this.getClass().getName(), e);
			throw new AppException("获取线路"+routeId+"的所有线段失败");
		}
	}

	public PageModel<Segment> getAll(String queryString, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
}
