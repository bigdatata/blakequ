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
				alert("�û�������Ϊ�գ�");
				document.form.username.focus();
				return false;
			}
			if(document.form.password.value.length == 0){
				alert("���벻��Ϊ��");
				document.form.password.focus();
				return false;
			}
			if(document.form.password.value != document.form.password1.value){
				alert("�����������벻һ�£����������룡");
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
								<font color="#FF0000">*</font>�û���:&nbsp;
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
								<font color="#FF0000">*</font>����:&nbsp;
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
								<font color="#FF0000">*</font>�ٴ���������:&nbsp;
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
								<font color="#FF0000">*</font>Ȩ��:&nbsp;
							</div>
						</td>
						<td width="78%">
							<select name="authority">
					    		<option value="user">��ͨ�û�</option>
					    		<option value="admin">����Ա</option>
					    	</select>
						</td>
						
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnAdd" class="button1" type="submit" id="btnAdd"
						value="ע��">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="����" onClick="javascript:history.go(-1);">
				</div>
    </form>
  </body>
</html>
