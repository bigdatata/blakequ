package cm.commons.sys.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.ComputerLog;
import cm.commons.pojos.RouterLog;
import cm.commons.sys.dao.ComputerLogDao;
import cm.commons.util.PageModel;

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
			List<ComputerLog> list = getSession().createQuery("from ComputerLog c ORDER BY c.currTime desc")
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
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get computer log by station name or id fail！"+this.getClass().getName(), e);
			throw new AppException("根据站点的名字或ID："+key+"获取日志失败");
		}
	}
	
	/**
	 * 可以通过站点名字查询此站点电脑的日志，按时间排序
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageModel<ComputerLog> getAll(String queryString, int pageNo,
			int pageSize) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all data by page");
		try {
			List<ComputerLog> itemList = new ArrayList<ComputerLog>();
			if (queryString != null && !"".equals(queryString)) {
				itemList = getSession().createQuery("from ComputerLog cl " +
						"where cl.computer.station.name like ? " +
						"order by cl.currTime desc")
						.setParameter(0, "%"+queryString+"%")
						.setFirstResult((pageNo-1) * pageSize)
						.setMaxResults(pageSize)
						.list();
			}else{
				itemList = getSession().createQuery("from ComputerLog c order by c.currTime desc")
									.setFirstResult((pageNo-1) * pageSize)
									.setMaxResults(pageSize)
									.list();
			}
			
			PageModel pageModel = new PageModel();
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setList(itemList);
			pageModel.setTotalRecords(getTotalRecords(queryString));
			return pageModel;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data by page fail!", e);
			throw new AppException("通过分页获取数据失败(按时间排序)");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public PageModel<ComputerLog> getAllSortByComputer(String queryString,
			int pageNo, int pageSize) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all data by page");
		try {
			List<ComputerLog> itemList = new ArrayList<ComputerLog>();
			if (queryString != null && !"".equals(queryString)) {
				itemList = getSession().createQuery("from ComputerLog cl " +
						"where cl.computer.station.name like ? " +
						"order by cl.computer.id")
						.setParameter(0, "%"+queryString+"%")
						.setFirstResult((pageNo-1) * pageSize)
						.setMaxResults(pageSize)
						.list();
			}else{
				itemList = getSession().createQuery("from ComputerLog c order by c.computer.id")
									.setFirstResult((pageNo-1) * pageSize)
									.setMaxResults(pageSize)
									.list();
			}
			
			PageModel pageModel = new PageModel();
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setList(itemList);
			pageModel.setTotalRecords(getTotalRecords(queryString));
			return pageModel;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data by page fail!", e);
			throw new AppException("通过分页获取数据失败");
		}
	}
	
	/**
	 * get total records
	 * @param queryString
	 * @return
	 */
	private int getTotalRecords(String queryString) {
		// TODO Auto-generated method stub
		int count = 0;
		if (queryString != null && !"".equals(queryString)) {
			count = ((Long)getSession().createQuery("select count(*) from ComputerLog cl " +
					"where cl.computer.station.name like ? ")
					.setParameter(0, "%"+queryString+"%")
					.uniqueResult()).intValue();
		}else{
			count = ((Long)getSession().createQuery("select count(*) from ComputerLog c")
					.uniqueResult()).intValue();
		}
		return count;
	}
}
