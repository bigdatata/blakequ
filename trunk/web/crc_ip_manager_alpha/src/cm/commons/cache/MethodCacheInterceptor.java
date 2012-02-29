package cm.commons.cache;

import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 自定义缓存结果集，使用拦截器来为要缓存的对象建立缓存并缓存
 * @author quhao
 * 这个方法实现了两个接口，一个是MethodInterceptor（方法拦截），
 * 它主要是在方法的调用前后都可以执行。
 * 另一个InitializingBean （初始化Bean）它主要在方法调用之后做一下简单的检查
 */
public class MethodCacheInterceptor implements MethodInterceptor,
		InitializingBean {

	private static final Log logger = LogFactory.getLog(MethodCacheInterceptor.class);   
	  
    private Cache cache;   
  
    public void setCache(Cache cache) {   
        this.cache = cache;   
    }   
  
    public MethodCacheInterceptor() {   
        super();   
    }   
    
    /**  
     * 拦截Service/DAO的方法，并查找该结果是否存在，如果存在就返回cache中的值，  
     * 否则，返回数据库查询结果，并将查询结果放入cache  
     */  
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		 String targetName = invocation.getThis().getClass().getName();   
	        String methodName = invocation.getMethod().getName();   
	        Object[] arguments = invocation.getArguments();   
	        Object result;   
	       
	        logger.debug("Find object from cache is " + cache.getName());   
	           
	        String cacheKey = getCacheKey(targetName, methodName, arguments);   
	        Element element = cache.get(cacheKey);   
	        long startTime = System.currentTimeMillis();
	        //如果在缓存中没有，则从数据库获取数据并放入cache
	        if (element == null) {   
	            logger.debug("Hold up method , Get method result and create cache........!");   
	            result = invocation.proceed();   
	            element = new Element(cacheKey, (Serializable) result);   
	            cache.put(element);  
	            long endTime = System.currentTimeMillis();
	            logger.info(targetName + "." + methodName + " 方法被首次调用并被缓存，耗时"
	              + (endTime - startTime) + "毫秒" + " cacheKey:"
	              + element.getKey());
	        }   
	        else {
	        	   long endTime = System.currentTimeMillis();
	        	   logger.info(targetName + "." + methodName + " 结果从缓存中直接调用，耗时"
	        	     + (endTime - startTime) + "毫秒" + " cacheKey:"
	        	     + element.getKey());
	        }
	        return element.getValue();   
	}

	/**  
     * 获得cache key的方法，cache key是Cache中一个Element的唯一标识  
     * cache key包括 包名+类名+方法名，如com.co.cache.service.UserServiceImpl.getAllUser  
     */  
    private String getCacheKey(String targetName, String methodName, Object[] arguments) {   
        StringBuffer sb = new StringBuffer();   
        sb.append(targetName).append(".").append(methodName);   
        if ((arguments != null) && (arguments.length != 0)) {   
            for (int i = 0; i < arguments.length; i++) {   
                sb.append(".").append(arguments[i]);   
            }   
        }   
        return sb.toString();   
    }   
       
    /**  
     * implement InitializingBean，检查cache是否为空  
     */  
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it.");  
	}

}
