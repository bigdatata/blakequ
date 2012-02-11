package com.bjpowernode.drp.web.actions;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bjpowernode.drp.web.forms.LoginActionForm;

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
		LoginActionForm laf = (LoginActionForm)form;
		String username = laf.getUsername();
		String password = laf.getPassword();
		if ("admin".equals(username) && "admin".equals(password)) {
			String remoteAddr = request.getRemoteAddr();
//			System.out.println("remoteAddr==" + remoteAddr);
			String[] allowIps = request.getSession().getServletContext().getInitParameter("allow_ip").split(",");
			Arrays.sort(allowIps);
//			for (int i=0; i<allowIps.length; i++) {
//				System.out.println("ip=" + allowIps[i]);
//			}
//			System.out.println("192.168.0.14=====" + Arrays.binarySearch(allowIps, "192.168.0.14"));
			if (Arrays.binarySearch(allowIps, remoteAddr) < 0) {
				return mapping.findForward("index");
			} 
			//��¼�ɹ�
			request.getSession().setAttribute("user", username);
			return mapping.findForward("success");
		}else {
			//��¼ʧ��
//			return mapping.findForward("index");
			request.getSession().setAttribute("user", username);
			return mapping.findForward("success");
		}
	}

}
