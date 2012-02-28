package cm.commons.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cm.commons.pojos.User;

/**
 * 用户权限检查
 * @author Administrator
 *
 */
public class UserOperatorIntercepter extends HandlerInterceptorAdapter {

	//请求到达Controller之前，handler是下一个拦截器对象，如果没有是控制器，返回true继续进行，否则拒绝，中断。
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("_______preHandle__________");
		ModelAndView mav = new ModelAndView("../public/error");
		mav.addObject("error", "当前用户没有权限执行该操作！");
		User u = (User) request.getSession().getAttribute("user");
		if(u.getAuthority().equals("user")){
			System.out.println("postHandle:检查用户"+u.getUsername()+",权限异常操作");
			throw new ModelAndViewDefiningException(mav);
//			return false;
		}
		return true;
	}

}
