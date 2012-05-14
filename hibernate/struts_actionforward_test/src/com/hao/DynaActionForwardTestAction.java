package com.hao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DynaActionForwardTestAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//�����Ǿ�̬������
		/*
		//ע��������getParameter������getAttribute
		String page = String.valueOf(request.getParameter("page"));
		System.out.println(page);
		ActionForward af = null;
		if("1".equals(page)){
			af = mapping.findForward("page1");
			}
		else if("2".equals(page)){
			af = mapping.findForward("page2");
		}else{
			af = mapping.findForward("page3");
		}
		af.setRedirect(false);//���ǲ������õģ���Ϊ���޸ĵ��������ļ��е�Action��
		//��struts-config.xml�ļ��������������޸�,�ʶ�����
		return af;
		*/
		//������ж�̬����
		int page = Integer.parseInt(request.getParameter("page"));
		System.out.println(page);
		//�����Լ���ActionForward�������Ǵ������ļ��ж�ȡ��
		ActionForward af = new ActionForward();
		af.setPath("/page"+page+".jsp");
		//���û�������ض����򲻹���page1,2,3�����������Ӷ���http://.../dyna_page.do
		//�����ض���󣬼�af.setRedirect(true)��ʱ����ҳ����Ǿ����ҳ��(http://.../page1.jsp)
		af.setRedirect(true);//���Ϊʲô�����޸��أ���Ϊ�����Ǵ������ļ��ж�ȡ�ģ������Լ����ɵ�ת��
		return af;
	}

}
