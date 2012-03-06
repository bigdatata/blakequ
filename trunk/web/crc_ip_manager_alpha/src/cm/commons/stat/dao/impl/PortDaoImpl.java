package cm.commons.stat.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.Port;
import cm.commons.stat.dao.PortDao;
import cm.commons.util.PageModel;

public class PortDaoImpl extends BasicDaoImpl<Integer, Port> implements PortDao<Integer, Port> {

	private static Log log = LogFactory.getLog(PortDaoImpl.class);
	public PortDaoImpl(){
		super(Port.class);
	}
	@SuppressWarnings("unchecked")
	public List<Port> getPortsByRouter(Integer routerId) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get port by router id:"+routerId);
		try {
			List<Port> list = null;
			list = getSession().createQuery("from Port p where p.router.id = ? order by p.getTime desc")
						.setParameter(0, routerId)
						.setFirstResult(0)
						.setMaxResults(4)
						.list();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get port by router id:"+routerId+" fail!", e);
			throw new AppException("获取指定路由端口失败");
		}
	}
	@SuppressWarnings("unchecked")
	public PageModel<Port> getPortsByRouter(Integer routerId, int pageNo,
			int pageSize) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get ports by router id");
		try {
			List<Port> itemList = new ArrayList<Port>();
			if (routerId != null) {
				itemList = getSession().createQuery("from Port p " +
						"where p.router.id = ? " +
						"order by p.getTime desc")
						.setParameter(0, routerId)
						.setFirstResult((pageNo-1) * pageSize)
						.setMaxResults(pageSize)
						.list();
			}else{
				itemList = getSession().createQuery("from Port p order by p.getTime desc")
									.setFirstResult((pageNo-1) * pageSize)
									.setMaxResults(pageSize)
									.list();
			}
			
			PageModel pageModel = new PageModel();
			pageModel.setPageNo(pageNo);
			pageModel.setPageSize(pageSize);
			pageModel.setList(itemList);
			pageModel.setTotalRecords(getTotalRecords(routerId));
			return pageModel;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get ports by router id fail!", e);
			throw new AppException("获取路由端口失败");
		}
	}
	
	private int getTotalRecords(Integer routerId) {
		// TODO Auto-generated method stub
		int count = 0;
		if (routerId != null) {
			count = ((Long)getSession().createQuery("select count(*) from Port p " +
					"where p.router.id = ? ")
					.setParameter(0, routerId)
					.uniqueResult()).intValue();
		}else{
			count = ((Long)getSession().createQuery("select count(*) from Port p")
					.uniqueResult()).intValue();
		}
		return count;
	}
	
}
