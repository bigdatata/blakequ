package cm.commons.sys.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.Warn;
import cm.commons.sys.dao.WarnDao;
import cm.commons.util.PageModel;

public class WarnDaoImpl extends BasicDaoImpl<Integer, Warn> implements WarnDao<Integer, Warn> {

	private static Log log = LogFactory.getLog(WarnDaoImpl.class);
	
	public WarnDaoImpl(){
		super(Warn.class);
	}
	
	public List<Warn> getWarnByDate(Date beginTime, Date endTime) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get warn by date from beginTime="+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(beginTime)+
				" endTime="+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(endTime));
		if(beginTime.after(endTime)){
			log.error("the begin time can't after end time");
			throw new AppException("查询告警日志错误！");
		}
		try {
			return getSession().createQuery("from Warn w where w.warntime between ? and ? order by w.warntime desc")
				.setParameter(0, beginTime)
				.setParameter(1, endTime)
				.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get warn by time error", e);
			throw new AppException("查询告警日志错误！");
		}
	}

	public List<Warn> getWarnByState(int warnState) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get warn by state");
		try {
			return getSession().createQuery("from Warn w where w.warnstate=? order by w.warntime desc")
				.setParameter(0, warnState)
				.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get warn by state error state="+warnState, e);
			throw new AppException("查询告警日志错误，告警状态state="+warnState);
		}
	}

	public List<Warn> getWarnByStation(int stationId) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get warn by station "+stationId);
		try {
			return getSession().createQuery("from Warn w where w.stationId=? order by w.warntime desc")
				.setParameter(0, stationId)
				.list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get warn by station id="+stationId+" error!", e);
			throw new AppException("查询告警日志错误，查询站点id="+stationId);
		}
	}

	public void deleteByState(int warnState) throws AppException {
		// TODO Auto-generated method stub
		log.debug("delete warn by state="+warnState);
		try {
			getSession().createQuery("delete from Warn w where w.warnstate=?")
				.setParameter(0, warnState)
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete warn by state="+warnState+" fail!", e);
			throw new AppException("删除告警失败！");
		}
	}

	public void deleteByStation(int stationId) throws AppException {
		// TODO Auto-generated method stub
		log.debug("");
		try {
			getSession().createQuery("delete from Warn w where w.stationId=?")
				.setParameter(0, stationId)
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete warn by station="+stationId+" fail!", e);
			throw new AppException("删除告警失败！");
		}
	}

	public void deleteByTime(Date beginTime, Date endTime) throws AppException {
		// TODO Auto-generated method stub
		log.debug("");
		if(beginTime.after(endTime)){
			log.error("the begin time can't after end time");
			throw new AppException("删除告警日志错误！");
		}
		try {
			getSession().createQuery("delete from Warn w where w.warntime between ? and ?")
				.setParameter(0, beginTime)
				.setParameter(1, endTime)
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete warn by time ("+beginTime+", "+endTime+") fail!", e);
			throw new AppException("删除告警失败！");
		}
	}

	/**
	 * 可以通过告警的内容获取告警
	 * @param queryString 如果为空，则查询所有告警
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageModel<Warn> getAll(String queryString, int pageNo, int pageSize)
			throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all data by page");
		try {
			List<Warn> list = new ArrayList<Warn>();
			if (queryString != null && !"".equals(queryString)) {
				list = getSession().createQuery("from Warn w " +
						"where w.warncontent like ? " +
						"order by w.warntime desc")
						.setParameter(0, "%"+queryString+"%")
						.setFirstResult((pageNo-1) * pageSize)
						.setMaxResults(pageSize)
						.list();
			}else{
				list = getSession().createQuery("from Warn w order by w.warntime desc")
									.setFirstResult((pageNo-1) * pageSize)
									.setMaxResults(pageSize)
									.list();
			}
			
			PageModel pageModel = new PageModel();
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setList(list);
			pageModel.setTotalRecords(getTotalRecords(queryString));
			return pageModel;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all data by page fail!", e);
			throw new AppException("通过分页获取数据失败");
		}
	}

	private int getTotalRecords(String queryString) {
		// TODO Auto-generated method stub
		int count = 0;
		if (queryString != null && !"".equals(queryString)) {
			count = ((Long)getSession().createQuery("select count(*) from Warn w " +
					"where w.warncontent like ? ")
					.setParameter(0, "%"+queryString+"%")
					.uniqueResult()).intValue();
		}else{
			count = ((Long)getSession().createQuery("select count(*) from Warn")
					.uniqueResult()).intValue();
		}
		return count;
	}
	
	
}
