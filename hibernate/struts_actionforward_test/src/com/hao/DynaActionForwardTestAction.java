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
		//下面是静态的配置
		/*
		//注意这里是getParameter而不是getAttribute
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
		af.setRedirect(false);//这是不能设置的，因为它修改的是配置文件中的Action，
		//而struts-config.xml文件不能在运行期修改,故而出错
		return af;
		*/
		//下面进行动态配置
		int page = Integer.parseInt(request.getParameter("page"));
		System.out.println(page);
		//这是自己的ActionForward，而不是从配置文件中读取的
		ActionForward af = new ActionForward();
		af.setPath("/page"+page+".jsp");
		//如果没有设置重定向，则不管是page1,2,3它的请求连接都是http://.../dyna_page.do
		//设置重定向后，即af.setRedirect(true)此时请求页面就是具体的页面(http://.../page1.jsp)
		af.setRedirect(true);//这儿为什么可以修改呢，因为它不是从配置文件中读取的，他是自己生成的转向
		return af;
	}

}
