package com.hao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * ��¼Action
 * ����ȡ�ñ����ݡ�����ҵ���߼�������ת����Ϣ
 * 
 * @author Administrator
 *
 */
public class LoginAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		LoginActionForm laf = (LoginActionForm)form;
		String username = laf.getUsername();
		String password = laf.getPassword();
		/*if("admin".equals(username) && "admin".equals(password)){
			return mapping.findForward("sucess");
		}else
			return mapping.findForward("error");
			*/
		UserManager um = new UserManager();
		try{
			um.login(username, password);
			return mapping.findForward("sucess");
		}catch(UserNotFoundException e){
			e.printStackTrace();
			request.setAttribute("msg", "�û������ҵ����û�����=��" + username + "��");
		}catch(PasswordErrorException e) {
			e.printStackTrace();
			request.setAttribute("msg", "�������");
		}
		return mapping.findForward("error");
	}

}
