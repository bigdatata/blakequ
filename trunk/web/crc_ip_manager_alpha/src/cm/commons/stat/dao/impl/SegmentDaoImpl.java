package cm.commons.stat.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.Segment;
import cm.commons.stat.dao.SegmentDao;

public class SegmentDaoImpl extends BasicDaoImpl<Integer, Segment> implements
		SegmentDao<Integer, Segment> {
	private static Log log = LogFactory.getLog(SegmentDaoImpl.class);
	public SegmentDaoImpl(){
		super(Segment.class);
	}
	
	public List<Segment> getAllSegmentByRoute(Integer routeId)  throws AppException{
		// TODO Auto-generated method stub
		log.debug("get segment by route id"+routeId);
		try {
			return getSession().createQuery("from Segment s where s.routeId =?")
						.setParameter(0, routeId)
						.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get segment by route id="+routeId+" fail!", e);
			throw new AppException("通过线路id="+routeId+"获取所有线段");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Segment> getSegmentByStation(Integer stationId)
			throws AppException {
		// TODO Auto-generated method stub
		log.debug("get segment by station id");
		try {
			List<Segment> list = getSession().createQuery("from Segment s where s.stationByStation1Id.id=? or s.stationByStation2Id.id=?")
										.setParameter(0, stationId)
										.setParameter(1, stationId)
										.list();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get segment by station id fail!", e);
			throw new AppException("通过车站id="+stationId+"获取所有线段");
		}
	}

}
