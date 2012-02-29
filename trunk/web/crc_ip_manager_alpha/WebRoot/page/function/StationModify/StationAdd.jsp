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
			
			function getStations(){
				with(document.forms[0]) {
					action="<%=basePath%>route_station.do";
					method="post";
					submit();
				}
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
						<select name="route_id">
							<option value="-1" selected="selected">
								请选择线路
							</option>
							<c:forEach items="${routes}" var="route">
								<option value="${route.id}"
									<c:if test = "${route.id == current_route_id}" >
		    				selected
		    			</c:if>>
									${route.name}
								</option>
							</c:forEach>
						</select>
						<input type="button" value="确定" onclick="getStations()" />

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
										线路X坐标:&nbsp;
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
										线路Y坐标:&nbsp;
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
										<option value="-1">
											无
										</option>
										<c:forEach items="${stations}" var="ic">
											<option value="${ic.id}">
												${ic.name }
											</option>
										</c:forEach>
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
										<option value="-1">
											无
										</option>
										<c:forEach items="${stations}" var="iu">
											<option value="${iu.id}">
												${iu.name }
											</option>
										</c:forEach>
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
