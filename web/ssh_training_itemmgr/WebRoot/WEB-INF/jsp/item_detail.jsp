<%@ page language="java" contentType="text/html; charset=GB18030"
pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    

<html>
	<head>
		<base href="<%=basePath %>">
		<title>������ϸ��Ϣ</title>
		<link rel="stylesheet" href="style/drp.css">
		<script src="script/client_validate.js"></script>
		<script src="script/windows.js"></script>
		<script language="javascript">

</script>
	</head>

	<body class="body1">
		<form name="itemForm" target="_self" id="itemForm">
			<div align="center">
				<table width="95%" border="0" cellspacing="2" cellpadding="2">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
				<table width="95%" border="0" cellspacing="0" cellpadding="0"
					height="8">
					<tr>
						<td width="522" class="p1" height="2" nowrap>
							<img src="images/mark_arrow_03.gif" width="14" height="14">
							&nbsp;
							<b>�������ݹ���&gt;&gt;����ά��&gt;&gt;������ϸ��Ϣ</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="22%" height="29">
							<div align="right">
								���ϴ���:&nbsp;
							</div>
						</td>
						<td width="78%">
							${item.itemNo }
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								��������:&nbsp;
							</div>
						</td>
						<td>
							${item.itemName} 
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								���Ϲ��:&nbsp;
							</div>
						</td>
						<td>
							${item.spec }
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								�����ͺ�:&nbsp;
							</div>
						</td>
						<td>
							${item.pattern }
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								���:&nbsp;
							</div>
						</td>
						<td>
							${item.itemCategory.name}
						</td>
					</tr>
					<tr>
						<td height="26">
							<div align="right">
								������λ:&nbsp;
							</div>
						</td>
						<td>
							${item.itemUnit.name}
						</td>
					</tr>
					<tr>
						<td height="74">
							<div align="right">
								ͼƬ:&nbsp;
							</div>
						</td>
						<td>
							<img src="images/${item.uploadFileName}" width="85" height="49">
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<div align="center">
					<input name="btnClose" class="button1" type="button" id="btnClose"
						value="�ر�" onClick="window.close()">
				</div>
			</div>
		</form>
	</body>
</html>
