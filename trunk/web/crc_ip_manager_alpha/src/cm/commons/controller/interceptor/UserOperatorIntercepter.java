package cm.commons.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cm.commons.pojos.User;

/**
 * 用户权限检查
 * @author Administrator
 *
 */
public class UserOperatorIntercepter implements HandlerInterceptor {

	//释放资源,关闭连接等
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("afterCompletion");
	}

	//完成Controller，在生成View之前的动作
	//如果不符合条件可以重定向到其他视图，向模型中添加公共成员
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		User u = (User) request.getSession().getAttribute("user");
		if(u.getAuthority().equals("user")){
			System.out.println("postHandle:检查用户"+u.getUsername()+"权限异常操作");
			modelAndView.setViewName("error");
			modelAndView.addObject("error", "你没有权限进行此操作！");
		}
	}

	//请求到达Controller之前，handler是下一个拦截器对象，如果没有是控制器，返回true继续进行，否则拒绝，中断。
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("preHandle");
		User u = (User) request.getSession().getAttribute("user");
		if(u.getAuthority().equals("user")){
			System.out.println("postHandle:检查用户"+u.getUsername()+"权限异常操作");
			return false;
		}
		return true;
	}

}
