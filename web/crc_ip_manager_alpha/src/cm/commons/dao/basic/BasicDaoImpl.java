package cm.commons.dao.basic;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import cm.commons.exception.AppException;
import cm.commons.util.PageModel;

public abstract class BasicDaoImpl<K extends Serializable, E>  extends HibernateDaoSupport implements BasicDao<K,E>{

	private static Log log = LogFactory.getLog(BasicDaoImpl.class);
	private Class<? extends E> entityClass;
	
	public BasicDaoImpl(){}
	
	public BasicDaoImpl(Class<? extends E> entityClass) {
		this.entityClass = entityClass;
	}
	
	@SuppressWarnings("unchecked")
	public BasicDaoImpl(String className){
		try {
			this.entityClass = (Class<? extends E>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.error("class for name "+className+" not found!", e);
		}
	}

	public void deleteById(K id) throws AppException {
		// TODO Auto-generated method stub
		log.debug("delete from class "+entityClass.getName());
		try {
			getHibernateTemplate().delete(getHibernateTemplate().get(entityClass, id));
		} catch (RuntimeException e) {
			// TODO: handle exception
			log.error("delete id="+id+ " from class "+entityClass.getName()+" fail!", e);
			throw new AppException("删除记录" + id + "失败");
		}
		
	}

	@SuppressWarnings("unchecked")
	public E get(K id) throws AppException {
		// TODO Auto-generated method stub
		log.debug("get from class "+entityClass.getName());
		try {
			return (E) getHibernateTemplate().get(entityClass, id);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("find id="+id+" from class "+entityClass.getName()+" fail!", e);
			throw new AppException("查找记录"+id+"失败");
		}
	}

	public void save(E entity) throws AppException {
		// TODO Auto-generated method stub
		log.debug("save from class "+entityClass.getName());
		try {
			getHibernateTemplate().save(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("save entity "+entityClass.getName()+" fail!", e);
			throw new AppException("保存记录失败");
		}
	}

	public void saveOrUpdate(E entity) throws AppException {
		// TODO Auto-generated method stub
		log.debug("saveOrUpdate from class "+entityClass.getName());
		try {
			getHibernateTemplate().saveOrUpdate(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("saveOrUpdate entity "+entityClass.getName()+" fail!", e);
			throw new AppException("保存或更新实体失败");
		}
	}

	public void update(E entity) throws AppException {
		// TODO Auto-generated method stub
		log.debug("update from class "+entityClass.getName());
		try {
			getHibernateTemplate().update(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("update entity "+entityClass.getName()+" fail!", e);
			throw new AppException("更新实体失败");
		}
	}

	@SuppressWarnings("unchecked")
	public List<E> getAll() throws AppException {
		// TODO Auto-generated method stub
		log.debug("get all entity from class "+entityClass.getName());
		try {
			return getSession().createQuery("from "+entityClass.getName()).list();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("get all entity "+entityClass.getName()+" fail!", e);
			throw new AppException("获取所有实体失败");
		}
	}

	public void delete(E entity) throws AppException {
		// TODO Auto-generated method stub
		log.debug("delete from class "+entityClass.getName());
		try {
			getHibernateTemplate().delete(entity);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete entity "+entityClass.getName()+" fail!", e);
			throw new AppException("删除实体失败");
		}
	}

	
	public void deleteItem(K[] ids) throws AppException {
		// TODO Auto-generated method stub
		log.debug("delete from class "+entityClass.getName());
		try {
			for(int i=0; i<ids.length; i++){
				getHibernateTemplate().delete(getHibernateTemplate().load(entityClass, ids[i]));
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("delete entity "+entityClass.getName()+" fail!", e);
			throw new AppException("删除多个实体失败");
		}
	}

	public PageModel<E> getAll(String queryString, int pageNo, int pageSize)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}
}
