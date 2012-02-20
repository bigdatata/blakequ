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
		
		function backMain(){
				with(document.forms[0]) {
					action="<%=basePath %>main.do";
					method="post";
					submit();
				}
			}
		
		function validateForm() {
			if(document.form.key.value.length == 0){
				alert("配置名称不能为空！");
				document.form.key.focus();
				return false;
			}
			if(document.form.value.value.length == 0){
				alert("配置属性不能为空！");
				document.form.value.focus();
				return false;
			}
			return true;
		}

	</script>

  </head>
  
  <body>
    <form action="<%=basePath %>admin/config/add_modify_config.do" method="post" name="form" id="form" onsubmit="return validateForm()">
    	<input type="text" name="id" style= "visibility:hidden" value="${config.id}"/><br/>
    	<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>配置名称:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="key" type="text" class="text1" id="key"
								size="10" maxlength="20" value="${config.key}"/>
						</td>
					</tr>
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>配置属性:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="value" type="text" class="text1" id="value"
								size="10" maxlength="20" value="${config.value}"/>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnAdd" class="button1" type="submit" id="btnAdd"
						value="确定">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="返回" onClick="backMain()">
				</div>
    </form>
  </body>
</html>
