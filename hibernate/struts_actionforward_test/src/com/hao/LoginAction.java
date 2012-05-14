package com.hao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class LoginAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		LoginActionForm laf = (LoginActionForm)form;
		String username = laf.getUsername();
		String password = laf.getPassword();
		
		UserManager userManager = new UserManager();
		try {
			userManager.login(username, password);
			//如果登录过，这session设置的username将不为空，否则就为空
			//如果要访问保护页面，就跳转到登录页面，登录过才可以访问保护页面
//			request.setAttribute("username", username);//注意生命周期的问题，在整个会话周期里都有用，而reuquest则不是
			request.getSession().setAttribute("username", username);
			
			request.setAttribute("msg", username+",登录成功！");
			return mapping.findForward("success");
		}catch(UserNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("msg", "用户不能找到，用户名称=【" + username + "】");
		}catch(PasswordErrorException e) {
			e.printStackTrace();
			request.setAttribute("msg", "密码错误");
		}
		return mapping.findForward("error");
	}
	
}
