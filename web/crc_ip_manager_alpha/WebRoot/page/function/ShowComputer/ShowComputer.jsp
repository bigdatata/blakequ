<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<link href="<%=path%>/pattern/cm/css/add.css" type="text/css"
			rel="stylesheet" />
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
				document.form.key.focus();
				return false;
			}
			if(document.form.pwd.value.length == 0){
				alert("原先的密码不能为空！");
				document.form.pwd.focus();
				return false;
			}
			if(document.form.password.value.length == 0){
				alert("新密码不能为空！");
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
		
		function showLog(){
			with(document.forms[0]) {
			action="<%=basePath%>";
			method="post";
			submit();
		}
		}

		function addComputer(){
			with(document.forms[0]) {
			action="<%=basePath%>computer/add_computer.do";
			method="post";
			submit();
		}
		
		function deleteComputer(){
			with(document.forms[0]) {
			action="<%=basePath%>computer/delete_computer.do";
			method="post";
			submit();
		}
		
		function modifyComputer(){
			with(document.forms[0]) {
			action="<%=basePath%>computer/modify_computer.do";
			method="post";
			submit();
		}
	</script>

	</head>

	<body>
			
		<form action="<%=basePath %>" method="post" name="form" id="form"
			onsubmit="return validateForm()">
			<div class="layout">
				<div class="title">
					站点电脑
				</div>
				
				<div id="content">
			<input type="text" name="id" style="visibility: hidden"
				value="${computer.id}" />
			<br />
			<input type="text" name="stationId" style="visibility: hidden"
				value="${computer.stationId}" />
			<br />
			<hr width="97%" align="center" size=0>
			<table class="flexme1">
				<tr>
					<td width="22%" height="29">
						<div align="right">
							<font color="#FF0000">*</font>IP:&nbsp;
						</div>
					</td>
					<td width="78%">
						<div align="left">
							<input name="ip" type="text" class="text1" id="ip" size="10"
								maxlength="20" value="${computer.ip}" readonly />
						</div>
					</td>
				</tr>
				<tr>
					<td width="22%" height="29">
						<div align="right">
							<font color="#FF0000">*</font>操作系统:&nbsp;
						</div>
					</td>
					<td width="78%">
						<input name="os" type="text" class="text1" id="os" size="10"
							maxlength="20" value="${computer.os}" />
					</td>
				</tr>
				<tr>
					<td width="22%" height="29">
						<div align="right">
							<font color="#FF0000">*</font>当前状态:&nbsp;
						</div>
					</td>
					<td width="78%">
						<input name="state" type="text" class="text1" id="state" size="10"
							maxlength="20" value="${computer.state}" />
					</td>
				</tr>
				<tr>
					<td width="22%" height="29">
						<div align="right">
							<font color="#FF0000">*</font>日志查看:&nbsp;
						</div>
					</td>
					<td width="78%">
						<input name="log" type="button" class="text1" id="log" size="10"
							maxlength="20" value="查看日志" onclick="showLog()" />
					</td>
				</tr>
			</table>
			<hr width="97%" align="center" size=0>
			<div align="center">
				<input name="btnAdd" class="button1" type="submit" id="btnAdd"
					value="增加" onclick="addComputer()">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="btnDel" class="button1" type="submit" id="btnAdd"
					value="删除" onclick="deleteComputer()">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="btnModify" class="button1" type="submit" id="btnAdd"
					value="修改" onclick="modifyComputer()">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="btnBack" class="button1" type="button" id="btnBack"
					value="返回" onClick="javascript:history.go(-1);">
			</div>
			</div>
		</div>
		</form>
		
	</body>
</html>
