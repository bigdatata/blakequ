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
		System.out.println(request.getSession().getAttribute("username"));//����ǿ��Ի�ȡadmin
//		System.out.println(request.getAttribute("username"));//���ֻ����һ��request�У��ʶ����ٴη��ʵ�ʱ��username����null
		System.out.println("mustlogin");
		//���usernameΪ��˵��û��¼��������ת����¼ҳ��
		 if(request.getSession().getAttribute("username") == null){
			return  mapping.findForward("login");
		 }
		return mapping.findForward("success");
	
	}

}
