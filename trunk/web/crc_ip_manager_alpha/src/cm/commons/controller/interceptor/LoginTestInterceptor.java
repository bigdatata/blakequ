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
		ModelAndView mav = new ModelAndView("WEB-INF/index.jsp");
		System.out.println("检查用户是否登录");
		if(loginUser == null){
			System.out.println("当前用户还没有登录!");
			mav.addObject("error", "你还没有登录!");
			throw new ModelAndViewDefiningException(mav);
		}
		return true;
	}

	

}
