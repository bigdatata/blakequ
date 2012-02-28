package cm.commons.stat.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.Port;
import cm.commons.stat.dao.PortDao;

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
			list = getSession().createQuery("from Port p where p.router.id = ? order by p.ifIndex")
						.setParameter(0, routerId)
						.list();
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get port by router id:"+routerId+" fail!", e);
			throw new AppException("获取指定路由端口失败");
		}
	}
	
}
