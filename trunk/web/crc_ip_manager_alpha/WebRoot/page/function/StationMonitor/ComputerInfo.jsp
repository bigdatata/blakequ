<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,java.sql.*"%>
<%@ page import="cm.commons.controller.form.ComputerForm"%>
<%@ page import="cm.commons.controller.form.ComputerLogForm"%>
<%
	ComputerForm cf = (ComputerForm) request.getAttribute("computer");
	ComputerLogForm clf = (ComputerLogForm) request
			.getAttribute("computerlog");
	;
	String[] infos = new String[3];
	String os = cf.getOs();
	if (os == null) {
		out.print("不存在台账信息");
	} 
	else if(clf == null)
	{
		out.print("电脑日志信息不存在");
	}
	else {
		System.out.println(os);
		infos = os.split(",");
		out.print("站点电脑IP : " 
					+ cf.getIp() 
					+ "<br/>" 
					+ "站点电脑操作系统类型  : "
					+ infos[0] 
					+ "<br/>" 
					+ "站点电脑操作系统版本  : " 
					+ infos[1]
					+ "<br/>" 
					+ "站点电脑处理器型号  : " 
					+ infos[2]
					+ "<br/>" 
					+ "CPU利用率  : "
					+ clf.getCupRate()
					+ "<br/>" 
					+ "内存利用率  : "
					+ clf.getMemRate());
	}
%>