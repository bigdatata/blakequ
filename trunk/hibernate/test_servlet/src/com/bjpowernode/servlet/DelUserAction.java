package com.bjpowernode.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DelUserAction implements Action {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		//����ɾ������.....
		
		//����ҵ���߼�
		UserManager userManager = new UserManager();
		try {
			userManager.del(username);
		}catch(Exception e) {
			return "del_error.jsp"; //ת��·������ͨ�������ļ���ȡ
		}
		return "/del_success.jsp"; //ת��·������ͨ�������ļ���ȡ
	}

}
