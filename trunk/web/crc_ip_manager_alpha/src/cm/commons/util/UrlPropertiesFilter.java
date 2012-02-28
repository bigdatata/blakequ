package cm.commons.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 该类主要在项目该法过程中测试用
 * @author 苏玮
 *
 * 2010-12-1
 *
 */
public class UrlPropertiesFilter implements Filter{
	
	private static Log log=LogFactory.getLog(UrlPropertiesFilter.class);
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		
		/**
		 * 获得url请求
		 */
		String uri=request.getRequestURI();
		System.out.println("-------------------------------------");
		System.out.println("请求url为："+uri);
		
		/**
		 * 获得传入参数
		 */
		Map<?,?> parameterMap=arg0.getParameterMap();
		for(Map.Entry<?, ?> entry:parameterMap.entrySet()){
			System.out.print(entry.getKey()+"=");
			String[] values=(String[])entry.getValue();
			for(String value:values){
				System.out.print(value+",");
			}
			System.out.println();
		}
		System.out.println("-------------------------------------");
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
