package cm.commons.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cm.commons.pojos.User;

public class LoginTestInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 检查是否登录
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		User loginUser = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView("../../index");
		String str = request.getRequestURI();
		int last = str.lastIndexOf("/")+1;
		CharSequence rq = str.subSequence(last, str.length());
		if(rq.equals("commit_data.do") || rq.equals("login.do")){
			return true;
		}
		
		if(!rq.equals("login.do") && loginUser == null){
			mav.addObject("error", "你还没有登录!");
			throw new ModelAndViewDefiningException(mav);
		}
		return true;
	}

}
