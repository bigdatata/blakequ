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
		
		function validateForm() {
			if(document.form.username.value.length == 0){
				alert("用户名不能为空！");
				document.form.username.focus();
				return false;
			}
			if(document.form.password.value.length == 0){
				alert("密码不能为空");
				document.form.password.focus();
				return false;
			}
			if(document.form.password.value != document.form.password1.value){
				alert("两次密码输入不一致，请重新输入！");
				document.form.password.focus();
				return false;
			}
			return true;
		}

	</script>

  </head>
  
  <body>
    <form action="<%=basePath %>add_user.do?flags=${flags}" method="post" name="form" id="form" onsubmit="return validateForm()">
    	<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>用户名:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="username" type="text" class="text1" id="username"
								size="10" maxlength="20"/>
						</td>
					</tr>
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>密码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="password" type="password" class="text1" id="password"
								size="10" maxlength="20"/>
						</td>
					</tr>
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>再次输入密码:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="password1" type="password" class="text1" id="password1"
								size="10" maxlength="20"/>
						</td>
						
					</tr>
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>权限:&nbsp;
							</div>
						</td>
						<td width="78%">
							<select name="authority">
					    		<option value="user">普通用户</option>
					    		<option value="admin">管理员</option>
					    	</select>
						</td>
						
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnAdd" class="button1" type="submit" id="btnAdd"
						value="注册">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onClick="javascript:history.go(-1);">
				</div>
    </form>
  </body>
</html>
