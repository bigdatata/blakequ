package com.bjpowernode.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DelUserAction implements Action {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		//其他删除条件.....
		
		//调用业务逻辑
		UserManager userManager = new UserManager();
		try {
			userManager.del(username);
		}catch(Exception e) {
			return "del_error.jsp"; //转向路径可以通过配置文件读取
		}
		return "/del_success.jsp"; //转向路径可以通过配置文件读取
	}

}
