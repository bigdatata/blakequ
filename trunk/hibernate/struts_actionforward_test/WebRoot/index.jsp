<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <!-- 
  注意：在这里设置*.jsp和*.do是不一样的，jsp是转向页面，而do则是到Action,由action调度
   -->
  <body>
    <a href="login.jsp">登录</a> <br>
    <a href="must_login.do">受保护的页面</a>
    <p>
    <h4>动态的actionforward</h4>
	<form action="dyna_page.do" method="post">
		<input type="text" name="page"></type>
		<input type="submit" value="转向"></type>
	</form>
  </body>
</html>
