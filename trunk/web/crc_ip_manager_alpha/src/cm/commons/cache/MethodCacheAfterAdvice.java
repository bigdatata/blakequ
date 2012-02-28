package cm.commons.cache;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.ehcache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 这个方法主要是保证缓存的同步，保持与数据库的数据一致性。
 * @author Administrator
 *
 */
public class MethodCacheAfterAdvice implements AfterReturningAdvice,
		InitializingBean {

	private static final Log logger = LogFactory.getLog(MethodCacheAfterAdvice.class);
	private Cache cache;
	public void setCache(Cache cache) {
	  this.cache = cache;
	}
	public MethodCacheAfterAdvice() {
	  super();
	}
	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
			Object arg3) throws Throwable {
		// TODO Auto-generated method stub
		String className = arg3.getClass().getName();
		List list = cache.getKeys();
		for (int i = 0; i < list.size(); i++) {
		   String cacheKey = String.valueOf(list.get(i));
		   if (cacheKey.startsWith(className)) {
			   cache.remove(cacheKey);//将该类从缓存移除
			   logger.debug("remove cache " + cacheKey);
		   }
		}
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		Assert.notNull(cache,"Need a cache. Please use setCache(Cache) create it.");
	}

}
