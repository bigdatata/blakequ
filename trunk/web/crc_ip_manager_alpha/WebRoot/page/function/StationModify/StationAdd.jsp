<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<link href="<%=path%>/pattern/cm/css/add.css" type="text/css"
			rel="stylesheet" />
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>添加站点</title>
		<script type="text/javascript">
			function validateForm() {
				if (document.form.name.value.length == 0) {
					alert("线路名字不能为空！");
					document.form.name.focus();
					return false;
				}
				if (document.form.x.value.length == 0) {
					alert("线路坐标不能为空！");
					document.form.x.focus();
					return false;
				}
				if(document.form.y.value.length == 0){
					alert("线路坐标不能为空！");
					document.form.y.focus();
					return false;
				}
				return true;	
			}
			function showStations(id){
				var xmlhttp;
				if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
					xmlhttp = new XMLHttpRequest();
				} else {// code for IE6, IE5
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
				xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					document.getElementById("station1").innerHTML=xmlhttp.responseText;
					document.getElementById("station2").innerHTML=xmlhttp.responseText;
					}
				}
				xmlhttp.open("POST", "route_station.do?route_id=" + id, true);
				xmlhttp.send();
			}
		</script>

	</head>

	<body class="body1">
		<form action="<%=basePath%>admin/add_station.do" method="post"
			name="form" id="form" onsubmit="return validateForm()">
			<div class="layout">
				<div class="title">
					站点增加
				</div>

				<div id="content">
					<input type="hidden" name="command" value="add">
					<div align="center">
						<table width="95%" border="0" cellspacing="2" cellpadding="2">
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>

						插入线路选择：
						<select name="route_id" onchange="showStations(this.value)">
							<c:forEach items="${routes}" var="sif">
								<option value="${sif.id}"
									<c:if test="${sif.id == current_route_id}">
		    				selected
		    					</c:if>>
									${sif.name}
								</option>

							</c:forEach>
						</select>
						<hr width="97%" align="center" size=0>
						<table width="95%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="22%" height="29">
									<div align="right">
										站点名字:&nbsp;
									</div>
								</td>
								<td width="78%">
									<input name="name" type="text" class="text1" id="name"
										size="10" maxlength="20">
								</td>
							</tr>
							<tr>
								<td height="26">
									<div align="right">
										站点X坐标:&nbsp;
									</div>
								</td>
								<td>
									<input name="x" type="text" class="text1" id="x" size="20"
										maxlength="20">
								</td>
							</tr>
							<tr>
								<td height="26">
									<div align="right">
										站点Y坐标:&nbsp;
									</div>
								</td>
								<td>
									<label>
										<input name="y" type="text" class="text1" id="y" size="20"
											maxlength="20">
									</label>
								</td>
							</tr>
							<tr>
								<td height="26">
									<div align="right">
										上行站点:&nbsp;
									</div>
								</td>
								<td>
									<select name="station1" class="select1" id="station1">
									</select>
								</td>
							</tr>
							<tr>
								<td height="26">
									<div align="right">
										下行站点:&nbsp;
									</div>
								</td>
								<td>
									<select name="station2" class="select1" id="station2">
									</select>
								</td>
							</tr>
							<tr>
								<td height="26">
									<div align="right">
										是否是主站点:&nbsp;
									</div>
								</td>
								<td>
									<select name="isMainStation" class="select1" id="isMainStation">
										<option value="false" selected="selected">
											false
										</option>
										<option value="true">
											true
										</option>
									</select>
								</td>
							</tr>
						</table>
						<hr width="97%" align="center" size=0>
						<div align="center">
							<input name="btnAdd" class="button1" type="submit" id="btnAdd"
								value="添加">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input name="btnBack" class="button1" type="button" id="btnBack"
								value="返回" onClick="javascript:history.go(-1);">
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
