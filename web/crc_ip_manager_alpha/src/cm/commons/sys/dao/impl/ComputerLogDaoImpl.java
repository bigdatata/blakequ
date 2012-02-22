package cm.commons.sys.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.ComputerLog;
import cm.commons.pojos.RouterLog;
import cm.commons.sys.dao.ComputerLogDao;

public class ComputerLogDaoImpl extends BasicDaoImpl<Integer, ComputerLog> implements
		ComputerLogDao<Integer, ComputerLog> {

	private static Log log = LogFactory.getLog(ComputerLogDaoImpl.class);
	public ComputerLogDaoImpl(){
		super(ComputerLog.class);
	}
	@SuppressWarnings("unchecked")
	public List<ComputerLog> getAllSortByComputer() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all computer log sort by computer id "+this.getClass().getName());
		try {
			List<ComputerLog> list = getSession().createSQLQuery("select {computer_log.*} from computer_log ORDER BY computer_id")
										.addEntity("computer_log", ComputerLog.class)
										.list();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all computer log sort by computer id fail! "+this.getClass().getName(), e);
			throw new AppException("根据电脑排序获取所有日志失败");
		}
	}
	@SuppressWarnings("unchecked")
	public List<ComputerLog> getAllSortByTime() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all compter log sort by time "+this.getClass().getName());
		try {
			List<ComputerLog> list = getSession().createQuery("from ComputerLog c ORDER BY c.currTime")
										.list();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all compter log sort by time fail! "+this.getClass().getName(), e);
			throw new AppException("根据时间排序获取所有日志失败");
		}
	}
	@SuppressWarnings("unchecked")
	public List<ComputerLog> getComputerLog(Integer computerId)
			throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all computer log by computer id "+computerId +this.getClass().getName());
		try {
			List<ComputerLog> list = getSession().createSQLQuery("select {computer_log.*} from computer_log where computer_id = ?;")
										.addEntity("computer_log", ComputerLog.class)
										.setParameter(0, computerId)
										.list();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all computer log by computer id "+computerId +" "+this.getClass().getName(), e);
			throw new AppException("根据电脑id"+computerId+"获取所有日志失败");
		}
	}
	@SuppressWarnings("unchecked")
	public List<ComputerLog> getComputerLogByStationNameOrId(String key) {
		// TODO Auto-generated method stub
		log.debug("get computer log by station name or id "+this.getClass().getName());
		try {
			List<ComputerLog> list = null;
			list = getSession().createSQLQuery("SELECT cl.* FROM computer_log cl " +
					"JOIN computer c ON c.id=cl.computer_id " +
					"JOIN station s ON s.id=c.station_id " +
					"where s.name LIKE ? or s.id LIKE ? order by cl.curr_time;")
					.addEntity(ComputerLog.class)
					.setParameter(0, "%"+key+"%")
					.setParameter(1, "%"+key+"%")
					.list();
			for(ComputerLog cl : list){
				System.out.println(cl);
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get computer log by station name or id fail！"+this.getClass().getName(), e);
			throw new AppException("根据站点的名字或ID："+key+"获取日志失败");
		}
	}

}
