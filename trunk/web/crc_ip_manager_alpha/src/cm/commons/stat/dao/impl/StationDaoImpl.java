package cm.commons.stat.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.springframework.jdbc.object.SqlQuery;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.Station;
import cm.commons.stat.dao.StationDao;

public class StationDaoImpl extends BasicDaoImpl<Integer, Station> implements
		StationDao<Integer, Station> {

	private static Log log = LogFactory.getLog(StationDaoImpl.class);
	public StationDaoImpl(){
		super(Station.class);
	}
	
	public int getStationCount() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get station total number");
		try {
			return ((Long)getSession().createQuery("select count(*) from Station s").uniqueResult()).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get station total number fail!", e);
			throw new AppException("获取站点个数失败");
		}
	}

	public int getErrorStationCount() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get error station total number");
		try {
			return ((Long)getSession().createQuery("select count(*) from Station s where s.state = 1").uniqueResult()).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get error station total number fail!", e);
			throw new AppException("获取异常站点个数失败");
		}
	}

	public int getUnknowStationCount() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get unknow station total number");
		try {
			return ((Long)getSession().createQuery("select count(*) from Station s where s.state=2").uniqueResult()).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get unknow station total number fail!", e);
			throw new AppException("获取未知状态站点个数失败");
		}
	}

	public List<Station> getMainStaion()  throws AppException{
		// TODO Auto-generated method stub
		log.debug("get main station contain TDCS");
		try {
			return getSession().createQuery("from Station s where s.name like ?")
						.setParameter(0, "%"+"TDCS"+"%")
						.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get main station contain TDCS fail!", e);
			throw new AppException("获取主站点失败");
		}
	}

	public List<Station> getMainStationByRoute(Integer routeId) throws AppException{
		// TODO Auto-generated method stub
		log.debug("get main station by  route id");
		try {
			List<Station> ls = getSession().createQuery("from Station st where st.name like ? and " +
					"				(st.id in(SELECT s.stationByStation1Id FROM Segment s WHERE s.routeId = ?) or " +
					"					st.id in(SELECT s.stationByStation2Id FROM Segment s WHERE s.routeId = ?))")
									.setParameter(0, "%TDCS%")
									.setParameter(1, routeId)
									.setParameter(2, routeId)
									.list();
			return ls;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get main station by  route id fail", e);
			throw new AppException("获取线路id="+routeId+"的主站点失败");
		}
	}

	@SuppressWarnings("unchecked")
	public Station getStaionByName(String name) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get station by name");
		try {
			Station s = null;
			List<Station> stations = getSession().createQuery("from Station s where s.name like ?")
						.setParameter(0, "%"+name)
						.list();
			if(stations != null && stations.size() != 0) s = stations.get(0);
			return s;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get station by name fail!", e);
			throw new AppException("获取名字为"+name+"的站点失败");
		}
	}

	public List<Station> getStationsNotInSegment() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all station not in segment");
		try {
			List<Station> stations = getSession().createQuery("FROM Station s WHERE NOT EXISTS"+
								"(SELECT se.id FROM Segment se WHERE " +
								"se.stationByStation1Id=s.id OR " +
								"se.stationByStation2Id=s.id)")
								.list();
			return stations;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all station not in segment fail!", e);
			throw new AppException("获取不在线段上的其余站点失败");
		}
	}

	public List<Station> getAllStationByRoute(int routeId) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all station by route "+routeId);
		try {
			List<Station> stations = null;
			// TODO Auto-generated method stub
			stations = getSession().createSQLQuery("SELECT {station.*} FROM station WHERE  (" +
						"id IN(SELECT station1_id FROM segment  WHERE route_id = ?) OR " +
						"id IN(SELECT station2_id FROM segment WHERE route_id = ?)) " +
						"ORDER BY NAME LIKE '%TDCS%' DESC;")
						.addEntity("station", Station.class)
						.setParameter(0, routeId)
						.setParameter(1, routeId)
						.list();
			return stations;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all station by route "+routeId+" fail!", e);
			throw new AppException("获取线路"+routeId+"的所有站点失败");
		}
	}
	
	

}
