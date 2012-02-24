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
		//������
		var count = 0;
		//��¼ѡ�е�checkbox������
		var index = 0;
		for (var i=0; i<selectFlags.length; i++) {
			if (selectFlags[i].checked) {
			    //��¼ѡ�е�checkbox
				count++;
				index = i;
			}
		}
		if(count == 0) {
			alert("��ѡ����Ҫ�޸ĵ����ݣ�");
			return;
		}
		if (count > 1) {
			alert("һ��ֻ���޸�һ��վ�㣡");
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
			    //�Ѿ���ѡ�е�checkbox
				flag = true;
				index = i;
				break;
			}
		}
		if (!flag) {
			alert("��ѡ����Ҫɾ�������ݣ�");
			return;
		}	
		//ɾ����ʾ
		if (window.confirm("ȷ��ɾ����")) {
			with(document.forms[0]) {
				alert("�뵼�������·�������ļ�");
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
								value="�û�����" onClick="userManager()">
							<input name="btnDelete" class="button1" type="button"
								id="btnDelete" value="ϵͳ����" onClick="systemConfig()">
							<input name="btnModify" class="button1" type="button"
								id="btnModify" value="վ�������־����" onClick="logComputerManager()">
							<input name="btnModify" class="button1" type="button"
								id="btnModify" value="վ��·����־����" onClick="logRouterManager()">
						</div>
		</td>
		</br>
    	 ��·ѡ��<select name="route_id">
    		<c:forEach items="${all_route}" var="sif">
    			<option value="${sif.id}"
    			<c:if test = "${sif.id == current_route_id}" >
		    				selected
		    			</c:if>
    			>${sif.name}</option>
    		</c:forEach>
    	</select>
    	<input type="submit" value="ȷ��"/>
    <br/>
  ��ǰ��·��Ϣ��${station_info}<br/>
  <br>
    �߶��б�<br>
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
						��ʼ����
					</td>
					<td width="155" class="rd6">
						��������
					</td>
				</tr>
				<c:forEach items="${segment_list}" var="sif">
					<tr>
						<td class="rd8">
							<input type="checkbox" name="selectFlag1" class="checkbox1" value="${sif.id}">
						</td>
						<td class="rd8">
							<a href="#"
								onClick="window.open('detail_segment.do?segment_id=${sif.id}', 'վ����ϸ��Ϣ', 'width=400, height=400, scrollbars=no');return false;">${sif.id}</a>
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
 վ���б�<br>
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
						����
					</td>
					<td width="155" class="rd6">
						����
					</td>
					<td width="155" class="rd6">
						վ��·�ɺ͵��Թ���
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
								onClick="window.open('detail_station.do?station_id=${item.id}', 'վ����ϸ��Ϣ', 'width=400, height=400, scrollbars=no');return false;">${item.id}</a>
						</td>
						<td class="rd8">
							${item.name}
						</td>
						<td class="rd8">
							(${item.x}, ${item.y})
						</td>
						<td class="rd8">
							<input name="btnComputer" class="button1" type="button"
								id="btnComputer" value="���Թ���" onClick="computerManager()">
							<input name="btnRouter" class="button1" type="button"
								id="btnRouter" value="·�ɹ���" onClick="routerManager()">
						</td>
					</tr>
				</c:forEach>
			</table>
					<td nowrap class="rd19" width="10%">
						<div align="center">
							<input name="btnAdd" type="button" class="button1" id="btnAdd"
								value="���" onClick="addItem()">
							<input name="btnDelete" class="button1" type="button"
								id="btnDelete" value="ɾ��" onClick="deleteItem()">
							<input name="btnModify" class="button1" type="button"
								id="btnModify" value="�޸�" onClick="modifyItem()">
						</div>
					</td>
		</form>
  </body>
</html>
