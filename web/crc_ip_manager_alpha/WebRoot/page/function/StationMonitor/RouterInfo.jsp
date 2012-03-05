<%@ page contentType="text/html; charset=UTF-8"
	import="java.util.*,java.sql.*"%>
<%@ page import="cm.commons.controller.form.RouterForm"%>
<%@ page import="cm.commons.controller.form.RouterLogForm"%>
<%@ page import="cm.commons.controller.form.PortForm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
RouterForm rf = (RouterForm) request.getAttribute("router");
RouterLogForm rlf = (RouterLogForm)request.getAttribute("routerLog");
List<PortForm> pf = (List<PortForm>)request.getAttribute("ports");
if(rf == null)
{
	out.print("读取路由信息rf失败！");
}
else
{
	out.print(	  "路由器IP地址 :     "
				+ rf.getRouterIp()
				+ "<br/>"
				+ "路由端口数目 :     "
				+ rf.getPortCount()
				+ "<br/>"
				+ "路由器操作系统 :   "
				+ rf.getRouterInfo()
				+ "<br/>"
				);
}
if(rlf == null)
{
	out.print("读取路由信息rlf失败！");
}
else
{
	out.print(	"路由器CPU利用率 :  "
				+ rlf.getCpuRate()
				+ "<br/>"
				+ "路由器内存利用率 : " 
				+ rlf.getMemRate()
				+ "<br/>"
				+ "<br/>"
				
				);
}
if(pf == null)
{
	out.print("读取端口信息pf失败！");
}
else
{
out.print("路由器端口状态");
%>
<table width="770" border="1" style="BORDER-RIGHT: #EDEDED 10px ridge; BORDER-TOP: #EDEDED 10px ridge; BORDER-LEFT: #EDEDED 10px ridge; BORDER-BOTTOM: #EDEDED 10px ridge; BACKGROUND-COLOR: #E0FFFF" cellSpacing=0>
<tr>
	<td width="50">
	端口名称
	</td>
	<td width="50">
	状态
	</td>
	
	<td width="100">
	流入量
	</td>
	<td width="100">
	流出量
	</td>
	<td width="100">
	流入速率
	</td>
	<td width="100">
	流出速率
	</td>
	<td width="70">
	CRC
	</td>
	<td width="100">
	端口IP
	</td>
	<td width="100">
	时间
	</td>
</tr>
<c:forEach items="${ports}" var="p">
<tr>
	<td>
	${p.ifIndex}
	</td>
	<td>
	${p.ifOperStatus }
	</td>
	<td>
	${p.ifInOctets}
	</td>
	<td>
	${p.ifOutOctets}
	</td>
	<td>
	${p.locIfInBitsSec }
	</td>
	<td>
	${p.locIfOutBitsSec }
	</td>
	<td>
	${p.locIfInCrc }
	</td>
	<td>
	${p.portIp}
	</td>
	<td>
	${p.getTime}
	</td>
</tr>

</c:forEach> 
</table>
	
<%
}
%>