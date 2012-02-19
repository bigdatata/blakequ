<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'show_user.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
        用户列表. <br>
    <table width="95%" border="1" cellspacing="0" cellpadding="0"
				align="center" class="table1">
				<tr>
					<td width="155" class="rd6">
						id
					</td>
					<td width="155" class="rd6">
						名字
					</td>
					<td width="155" class="rd6">
						密码
					</td>
					<td width="155" class="rd6">
						权限
					</td>
				</tr>
				<c:forEach items="${user_list}" var="item">
					<tr>
						<td class="rd8">
							${item.id}
						</td>
						<td class="rd8">
							${item.username}
						</td>
						<td class="rd8">
							${item.password}
						</td>
						<td class="rd8">
							${item.authority}
						</td>
					</tr>
				</c:forEach>
			</table>
  </body>
</html>
