package cm.commons.sys.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.pojos.ComputerLog;
import cm.commons.sys.dao.ComputerLogDao;

public class ComputerLogDaoImpl extends BasicDaoImpl<Integer, ComputerLog> implements
		ComputerLogDao<Integer, ComputerLog> {

	private static Log log = LogFactory.getLog(ComputerLogDaoImpl.class);
	public ComputerLogDaoImpl(){
		super(ComputerLog.class);
	}
}
