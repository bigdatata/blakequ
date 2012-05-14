package com.bjpowernode.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserAction implements Action {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		//int age = Integer.parseInt(request.getParameter("username"));
		//String sex = request.getParameter("sex");
		//...................
		
		//调用业务逻辑
		UserManager userManager = new UserManager();
		userManager.add(username);
		return "/add_success.jsp"; //转向路径可以通过配置文件读取
	}

}
