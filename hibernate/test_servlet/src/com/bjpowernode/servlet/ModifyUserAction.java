package com.bjpowernode.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModifyUserAction implements Action {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//String userId = request.getParameter("userId");
		String username = request.getParameter("username");
		//int age = Integer.parseInt(request.getParameter("username"));
		//String sex = request.getParameter("sex");
		//...................
		
		//调用业务逻辑
		UserManager userManager = new UserManager();
		userManager.modify(username);
		return "/modify_success.jsp"; //转向路径可以通过配置文件读取
	}

}
