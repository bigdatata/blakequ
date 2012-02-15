package cm.commons.stat.dao.impl;

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
	
}
