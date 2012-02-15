package cm.commons.sys.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.pojos.RouterLog;
import cm.commons.sys.dao.RouterLogDao;

public class RouterLogDaoImpl extends BasicDaoImpl<Integer, RouterLog> implements
		RouterLogDao<Integer, RouterLog> {
	private static Log log = LogFactory.getLog(RouterLogDaoImpl.class);
	public RouterLogDaoImpl(){
		super(RouterLog.class);
	}

}
