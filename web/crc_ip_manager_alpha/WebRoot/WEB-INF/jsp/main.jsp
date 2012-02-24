<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'main.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function getSelectValue(){
		with(document.forms[0]) {
			var obj = document.getElementsByName("route_id");
			action="main.do?route_id="+obj;
			method="post";
			submit();
		}
	}
	
	function checkAll1() {
		var selectFlags = document.getElementsByName("selectFlag1");
		for (var i=0; i<selectFlags.length; i++) {
			selectFlags[i].checked = document.getElementById("ifAll1").checked;
		}
	}
	
	function checkAll2() {
		var selectFlags = document.getElementsByName("selectFlag2");
		for (var i=0; i<selectFlags.length; i++) {
			selectFlags[i].checked = document.getElementById("ifAll2").checked;
		}
	}
	
	function addItem() {
		//window.self.location = "item_add.html";
		window.location="<%=basePath %>show_add_station.do";
	}
	
	function modifyItem() {
		var selectFlags = document.getElementsByName("selectFlag2");
		//计数器
		var count = 0;
		//记录选中的checkbox索引号
		var index = 0;
		for (var i=0; i<selectFlags.length; i++) {
			if (selectFlags[i].checked) {
			    //记录选中的checkbox
				count++;
				index = i;
			}
		}
		if(count == 0) {
			alert("请选择需要修改的数据！");
			return;
		}
		if (count > 1) {
			alert("一次只能修改一个站点！");
			return;
		}
		window.self.location = "<%=basePath %>show_modify_station.do?station_id=" + selectFlags[index].value;
	}
	
	function deleteItem() {
		var selectFlags = document.getElementsByName("selectFlag2");
		var flag = false;
		var index = 0;
		for (var i=0; i<selectFlags.length; i++) {
			if (selectFlags[i].checked) {
			    //已经有选中的checkbox
				flag = true;
				index = i;
				break;
			}
		}
		if (!flag) {
			alert("请选择需要删除的数据！");
			return;
		}	
		//删除提示
		if (window.confirm("确认删除？")) {
			with(document.forms[0]) {
				alert("请导入该条线路的配置文件");
				action="admin/delete_station.do?station_id="+selectFlags[index].value;
				method="post";
				submit();
			}
		}
	}	
	
	function userManager(){
		with(document.forms[0]) {
			action="<%=basePath%>admin/all_user.do";
			method="post";
			submit();
		}
	}
	
	function systemConfig(){
		with(document.forms[0]) {
			action="<%=basePath%>admin/config/show_config.do";
			method="post";
			submit();
		}
	}
	
	function logComputerManager(){
		with(document.forms[0]) {
			//action="<%=basePath%>computer_log/get_log_by_time.do";
			action="<%=basePath%>computer_log/get_by_time.do?pageNo=1&queryString="
			method="post";
			submit();
		}
	}
	
	function logRouterManager(){
		with(document.forms[0]) {
			action="<%=basePath%>router_log/get_log_by_time.do";
			method="post";
			submit();
		}
	}
	
	function computerManager(){
		with(document.forms[0]) {
			action="<%=basePath%>computer/show_detail.do";
			method="post";
			submit();
		}
	}
	
	function routerManager(){
		with(document.forms[0]) {
			action="<%=basePath%>router/show_detail.do";
			method="post";
			submit();
		}
	}
	</script>
  </head>
  
  <body>
   <form action="<%=basePath%>main.do" method="post" >
   		<td nowrap class="rd19" width="10%">
						<div align="center">
							<input name="btnAdd" type="button" class="button1" id="btnAdd"
								value="用户管理" onClick="userManager()">
							<input name="btnDelete" class="button1" type="button"
								id="btnDelete" value="系统配置" onClick="systemConfig()">
							<input name="btnModify" class="button1" type="button"
								id="btnModify" value="站点电脑日志管理" onClick="logComputerManager()">
							<input name="btnModify" class="button1" type="button"
								id="btnModify" value="站点路由日志管理" onClick="logRouterManager()">
						</div>
		</td>
		</br>
    	 线路选择：<select name="route_id">
    		<c:forEach items="${all_route}" var="sif">
    			<option value="${sif.id}"
    			<c:if test = "${sif.id == current_route_id}" >
		    				selected
		    			</c:if>
    			>${sif.name}</option>
    		</c:forEach>
    	</select>
    	<input type="submit" value="确定"/>
    <br/>
  当前线路信息：${station_info}<br/>
  <br>
    线段列表：<br>
    <table width="95%" border="1" cellspacing="0" cellpadding="0"
				align="center" class="table1">
				<tr>
					<td width="35" class="rd6">
						<input type="checkbox" name="ifAll1" onClick="checkAll1()">
					</td>
					<td width="155" class="rd6">
						id
					</td>
					<td width="155" class="rd6">
						开始坐标
					</td>
					<td width="155" class="rd6">
						结束坐标
					</td>
				</tr>
				<c:forEach items="${segment_list}" var="sif">
					<tr>
						<td class="rd8">
							<input type="checkbox" name="selectFlag1" class="checkbox1" value="${sif.id}">
						</td>
						<td class="rd8">
							<a href="#"
								onClick="window.open('detail_segment.do?segment_id=${sif.id}', '站点详细信息', 'width=400, height=400, scrollbars=no');return false;">${sif.id}</a>
						</td>
						<td class="rd8">
							(${sif.startX}, ${sif.startY})
						</td>
						<td class="rd8">
							(${sif.endX}, ${sif.endY})
						</td>
					</tr>
				</c:forEach>
			</table>
 站点列表：<br>
    <table width="95%" border="1" cellspacing="0" cellpadding="0"
				align="center" class="table1">
				<tr>
					<td width="35" class="rd6">
						<input type="checkbox" name="ifAll2" onClick="checkAll2()">
					</td>
					<td width="155" class="rd6">
						id
					</td>
					<td width="155" class="rd6">
						名字
					</td>
					<td width="155" class="rd6">
						坐标
					</td>
					<td width="155" class="rd6">
						站点路由和电脑管理
					</td>
				</tr>
				<c:forEach items="${station_list}" var="item">
					<tr>
						<td class="rd8">
							<input type="checkbox" name="selectFlag2" class="checkbox2" value="${item.id}">
						</td>
						<td class="rd8">
							<input type="text" name="station_id" style= "visibility:hidden" value="${item.id}"/><br/>
							<a href="#"
								onClick="window.open('detail_station.do?station_id=${item.id}', '站点详细信息', 'width=400, height=400, scrollbars=no');return false;">${item.id}</a>
						</td>
						<td class="rd8">
							${item.name}
						</td>
						<td class="rd8">
							(${item.x}, ${item.y})
						</td>
						<td class="rd8">
							<input name="btnComputer" class="button1" type="button"
								id="btnComputer" value="电脑管理" onClick="computerManager()">
							<input name="btnRouter" class="button1" type="button"
								id="btnRouter" value="路由管理" onClick="routerManager()">
						</td>
					</tr>
				</c:forEach>
			</table>
					<td nowrap class="rd19" width="10%">
						<div align="center">
							<input name="btnAdd" type="button" class="button1" id="btnAdd"
								value="添加" onClick="addItem()">
							<input name="btnDelete" class="button1" type="button"
								id="btnDelete" value="删除" onClick="deleteItem()">
							<input name="btnModify" class="button1" type="button"
								id="btnModify" value="修改" onClick="modifyItem()">
						</div>
					</td>
		</form>
  </body>
</html>
