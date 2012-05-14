package com.bjpowernode.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryUserAction implements Action {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		//������ѯ��ѯ����
		//................
		
		//����ҵ���߼�
		UserManager userManager = new UserManager();
		List userList = userManager.query(username);
		request.setAttribute("userList", userList);
		return "/query_success.jsp"; //ת��·������ͨ�������ļ���ȡ
	}

}
