<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>     
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<html>
	<head>
		<base href="<%=basePath %>">
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>�޸�վ��</title>
		<script type="text/javascript">
			function validateForm() {
				if (document.form.name.value.length == 0) {
					alert("��·���ֲ���Ϊ�գ�");
					document.form.name.focus();
					return false;
				}
				if (document.form.x.value.length == 0) {
					alert("��·���겻��Ϊ�գ�");
					document.form.x.focus();
					return false;
				}
				if(document.form.y.value.length == 0){
					alert("��·���겻��Ϊ�գ�");
					document.form.y.focus();
					return false;
				}
				if(document.form.main.value != "${station.isMainStation}"){
					alert("�㲻���޸��Ƿ�����վ����Ϣ��");
					document.form.main.focus();
					return false;
				}
				return true;	
			}
			
			function backMain(){
				with(document.forms[0]) {
					action="<%=basePath %>main.do";
					method="post";
					submit();
				}
			}
			
			function selectStation(){
				with(document.forms[0]) {
					alert("�㲻���޸�����վ�㣬����޸������µ��������ļ�");
				}
			}
		</script>
	</head>

	<body class="body1">
		<form action="<%=basePath%>admin/modify_station.do" method="post" name="form" onsubmit="return validateForm()">
			<input type="hidden" name="command" value="add">
			<div align="center">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				
		
				<input name="id" type="text" class="text1" id="id" style= "visibility:hidden"
								size="10" maxlength="20" value=${station.id}>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								<font color="#FF0000">*</font>վ������:&nbsp;
							</div>
						</td>
						<td width="78%">
							<input name="name" type="text" class="text1" id="name"
								size="10" maxlength="20" value="${station.name}">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>��·X����:&nbsp;
							</div>
						</td>
						<td>
							<input name="x" type="text" class="text1" id="x"
								size="20" maxlength="20" value="${station.x}">
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>��·Y����:&nbsp;
							</div>
						</td>
						<td>
							<label>
								<input name="y" type="text" class="text1" id="y"
									size="20" maxlength="20" value="${station.y}">
							</label>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>�Ƿ���վ��:&nbsp;
							</div>
						</td>
						<td>
							<label>
								<input name="main" type="text" class="text1" id="main"
									size="20" maxlength="20" value="${station.isMainStation}">
							</label>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>����վ��:&nbsp;
							</div>
						</td>
						<td>
							<label>
								<input name="station1" type="text" class="text1" id="station1"
									size="20" maxlength="20" value="${station.station1}">
							</label>
							<label>
								<input name="station1_select" type="button" class="text1" id="station1_select"
									value="ѡ��" onclick="selectStation()">
							</label>
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								<font color="#FF0000">*</font>����վ��:&nbsp;
							</div>
						</td>
						<td>
							<label>
								<input name="station2" type="text" class="text1" id="station2"
									size="20" maxlength="20" value="${station.station2}">
							</label>
							<label>
								<input name="station2_select" type="button" class="text1" id="station2_select"
									value="ѡ��" onclick="selectStation()">
							</label>
						</td>
					</tr>
					
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnAdd" class="button1" type="submit" id="btnAdd"
						value="�޸�">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="btnBack" class="button1" type="button" id="btnBack"
						value="����" onClick="backMain()">
				</div>
			</div>
		</form>
	</body>
</html>
