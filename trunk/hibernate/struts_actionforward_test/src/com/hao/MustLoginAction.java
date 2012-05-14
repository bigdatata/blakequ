package com.hao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MustLoginAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		System.out.println(request.getSession().getAttribute("username"));//这个是可以获取admin
//		System.out.println(request.getAttribute("username"));//这个只限于一个request中，故而当再次访问的时候username还是null
		System.out.println("mustlogin");
		//如果username为空说明没登录过，就跳转到登录页面
		 if(request.getSession().getAttribute("username") == null){
			return  mapping.findForward("login");
		 }
		return mapping.findForward("success");
	
	}

}
