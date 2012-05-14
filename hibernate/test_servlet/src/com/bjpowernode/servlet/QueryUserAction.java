package com.bjpowernode.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryUserAction implements Action {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		//其他查询查询条件
		//................
		
		//调用业务逻辑
		UserManager userManager = new UserManager();
		List userList = userManager.query(username);
		request.setAttribute("userList", userList);
		return "/query_success.jsp"; //转向路径可以通过配置文件读取
	}

}
