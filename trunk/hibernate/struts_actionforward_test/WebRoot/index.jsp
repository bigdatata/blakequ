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
  ע�⣺����������*.jsp��*.do�ǲ�һ���ģ�jsp��ת��ҳ�棬��do���ǵ�Action,��action����
   -->
  <body>
    <a href="login.jsp">��¼</a> <br>
    <a href="must_login.do">�ܱ�����ҳ��</a>
    <p>
    <h4>��̬��actionforward</h4>
	<form action="dyna_page.do" method="post">
		<input type="text" name="page"></type>
		<input type="submit" value="ת��"></type>
	</form>
  </body>
</html>
