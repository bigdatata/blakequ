<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'start.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function register(){
			with(document.forms[0]) {
				action="<%=basePath %>login/add_user.do";
				method="post";
				submit();
			}
		}
		
		function modify(){
			with(document.forms[0]) {
				action="<%=basePath %>login/modify_user.do";
				method="post";
				submit();
			}
		}
	</script>

  </head>
  
  <body>
    <form action="user/login.do" method="post">
    	用户名：<input type="text" name="username"/><br/>
    	密码：<input type="text" name="password"/><br/>
    	用户类型:<select name="authority">
    		<option value="admin">管理员</option>
    		<option value="user">普通用户</option>
    	</select><br/>
    	<input type="submit" value="登陆"/>
    	<input type="button" value="注册" onclick="register()" />
    	<input type="button" value="修改" onclick="modify()" />
    </form>
  </body>
</html>
