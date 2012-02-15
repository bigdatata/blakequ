package cm.commons.sys.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.exception.AppException;
import cm.commons.pojos.System;
import cm.commons.sys.dao.SystemDao;

public class SystemDaoImpl extends BasicDaoImpl<Integer, System> implements
		SystemDao<Integer, System> {
	private static Log log = LogFactory.getLog(SystemDaoImpl.class);
	
	public SystemDaoImpl(){
		super(System.class);
	}
	
	public cm.commons.pojos.System getSystemConfigByKey(String configKey) throws AppException{
		// TODO Auto-generated method stub
		log.debug("get system config by key from class SystemDaoImpl");
		try {
			return (cm.commons.pojos.System) getSession().createQuery("from System s where s.configKey = ?")
				.setParameter(0, configKey)
				.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get system config key="+configKey+" fail!", e);
			throw new AppException("获取系统配置失败key="+configKey);
		}
	}

	public void saveOrUpdateSystemConfigKey(System s)
			throws AppException {
		// TODO Auto-generated method stub
		log.debug("save or update system config by key from class SystemDaoImpl");
		try {
			if(getSystemConfigByKey(s.getConfigKey()) == null){
				save(s);
			}else{
				updateSystemConfigKey(s);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save or update system config key="+s.getConfigKey()+" fail!", e);
			throw new AppException("更新系统配置失败key="+s.getConfigKey());
		}
	}

	public void updateSystemConfigKey(System s) throws AppException {
		// TODO Auto-generated method stub
		log.debug("update system config by key from class SystemDaoImpl");
		try {
			getSession().createQuery("update System s set s.configValue = ? where s.configKey= ?")
				.setParameter(0, s.getConfigValue())
				.setParameter(1, s.getConfigKey())
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update system config key="+s.getConfigKey()+" fail!", e);
			throw new AppException("更新系统配置失败key="+s.getConfigKey());
		}
	}

	public void deleteByConfigKey(String configKey) throws AppException{
		// TODO Auto-generated method stub
		log.debug("delete config by key="+configKey);
		try {
			getSession().createQuery("delete from System s where s.configKey=?")
				.setParameter(0, configKey)
				.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete system config key="+configKey+" fail!", e);
			throw new AppException("删除系统配置失败key="+configKey);
		}
	}
	

}
