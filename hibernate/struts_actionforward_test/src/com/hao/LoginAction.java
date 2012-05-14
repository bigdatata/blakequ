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
			//�����¼������session���õ�username����Ϊ�գ������Ϊ��
			//���Ҫ���ʱ���ҳ�棬����ת����¼ҳ�棬��¼���ſ��Է��ʱ���ҳ��
//			request.setAttribute("username", username);//ע���������ڵ����⣬�������Ự�����ﶼ���ã���reuquest����
			request.getSession().setAttribute("username", username);
			
			request.setAttribute("msg", username+",��¼�ɹ���");
			return mapping.findForward("success");
		}catch(UserNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("msg", "�û������ҵ����û�����=��" + username + "��");
		}catch(PasswordErrorException e) {
			e.printStackTrace();
			request.setAttribute("msg", "�������");
		}
		return mapping.findForward("error");
	}
	
}
