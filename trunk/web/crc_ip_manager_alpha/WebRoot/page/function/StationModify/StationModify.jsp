<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setCharacterEncoding("UTF-8");
%>
<html>
	<head>
		<link href="<%=path%>/pattern/cm/css/add.css" type="text/css"
			rel="stylesheet" />
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改站点</title>
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
				if(document.form.main.value != "${station.isMainStation}"){
					alert("你不能修改是否是主站点信息！");
					document.form.main.focus();
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
					document.getElementById("showStation").innerHTML=xmlhttp.responseText;
					}
				}
				xmlhttp.open("POST", "route_station.do?route_id=" + id, true);
				xmlhttp.send();
			}
			function selectStation(){
				with(document.forms[0]) {
					alert("你不能修改连接站点，如果修改请重新导入配置文件");
				}
			}
			function a()
		{
			var station_id;
			var station_name;
			station_name = document.getElementById("station_name").value;
			station_id = document.getElementById("station_id").value;
			var aa= window.confirm("确定要修改站点的名字？");
			if (aa) {
			window.location = "<%=basePath %>admin/modify_station_name.do?station_id="+station_id+"&station_name="+station_name;
			}
			else window.alert("三思而后行！");
		}
		</script>
	</head>

	<body class="body1">
		<form action="<%=basePath%>admin/modify_station.do" method="post"
			name="form" onsubmit="return validateForm()">
			<div class="layout">
				<div class="title">
					站点修改
				</div>


				<div id="content">
					<div class="four_columns">
						<div class="four_columns_text">
							线路选择:
						</div>
						<div class="four_columns_input">
							<select onchange="showStations(this.value)" name="route_id">
								<option>
										选择线路
									</option>
								<c:forEach items="${routes}" var="sif">
									<option value="${sif.id}">
										${sif.name}
									</option>

								</c:forEach>
							</select>
						</div>
						<div class="four_columns_text">
							站点选择:
						</div>
						<div class="four_columns_input" id="showStation">
						</div>
						<div class="clear"></div>
					</div>
					<div class="four_columns">
						<div class="four_columns_text">
							修改站点名称
						</div>
						<div class="four_columns_input">
							<input type="text" id="station_name" name="station_name" />  
						</div>
						<div class="clear"></div>
					</div>
					<div class="button">
						<input type="button" onclick="a()"value="提交修改">
					</div>
				</div>



			</div>
		</form>
	</body>
</html>
