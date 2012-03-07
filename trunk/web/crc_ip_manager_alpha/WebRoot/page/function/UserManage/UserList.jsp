<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link href="<%=path%>/pattern/cm/css/list_public.css"
			type="text/css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/pattern/flexigrid/css/flexigrid/flexigrid.css">
		<script type="text/javascript"
			src="<%=path%>/pattern/jquery-ui-1.7.1.custom/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript"
			src="<%=path%>/pattern/flexigrid/flexigrid.js"></script>
	  	<script src="<%=path%>/pattern/cm/js/list_public.js"
			language="javascript" type="text/javascript"></script>
		<title>用户列表</title>
	<script type="text/javascript">
	
		function AllQuery()
	{
		var checkBox=document.getElementById("ifAll");
		if(!checkBox.checked){
			checkBox.checked=true;
		}else{
			checkBox.checked=false;
		}

		var checkBoxAll=document.getElementsByTagName('input');
		for(var i = 0 ; i < checkBoxAll.length ; i++)
		{
			if(checkBoxAll[i].type == "checkbox")
			{
				checkBoxAll[i].checked = checkBox.checked;
			}
		}
	}
	
	
	function addItem() {
		//window.self.location = "item_add.html";
		window.location="<%=basePath %>show_add_user.do?flags=admin";
	}
	
	function modifyItem() {
		var selectFlags = document.getElementsByName("selectFlag1");
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
			alert("一次只能修改一个用户！");
			return;
		}
		window.self.location = "<%=basePath %>show_modify_user.do?id=" + selectFlags[index].value;
	}
	
	function deleteUserItem(){
		var selectFlags = document.getElementsByName("selectFlag1");
		var flag = false;
		var index = 0;
		var ids='';
		for (var i=0; i<selectFlags.length; i++) {
			if (selectFlags[i].checked) {
			    //已经有选中的checkbox
				flag = true;
				index = i;
				ids+=selectFlags[i].value;
				ids+=',';
			}
		}
		if (!flag) {
			alert("请选择需要删除的数据！");
			return;
		}	
		
		//删除提示
		if (window.confirm("确认删除当前数据？")) {
			with(document.forms[0]) {
				action='<%=basePath%>admin/delete_user_by_ids.do?userIds='+ids;
				method="post";
				submit();
			}
		}	
	}	
	
	function topPage() {
		//var searchStr = document.getElementsByName("searchStr");
		window.location = "<%=basePath%>admin/query_user_by_page.do?pageNo=1&id=${condition.id}&username=${condition.username}&authority=${condition.authority}";
	}
	
	function previousPage() {
		if(${pageModel.pageNo<=1}){
			alert("已经到达第一页!");
		}else{
			window.location = "<%=basePath%>admin/query_user_by_page.do?pageNo=${pageModel.pageNo-1}&id=${condition.id}&username=${condition.username}&authority=${condition.authority}";
		}
	}
	
	function nextPage() {
		if(${pageModel.pageNo>=pageModel.totalPages}){
			alert("已经到达最后一页!");
		}else{
			window.location = "<%=basePath%>admin/query_user_by_page.do?pageNo=${pageModel.pageNo+1}&id=${condition.id}&username=${condition.username}&authority=${condition.authority}";
		}
	}
	
	function bottomPage() {
		window.location = "<%=basePath%>admin/query_user_by_page.do?pageNo=${pageModel.buttomPageNo}&id=${condition.id}&username=${condition.username}&authority=${condition.authority}";
	}

	</script>
	</head>
	<body>

		<div id="layout">
			<div class="title">
				用户列表
			</div>
			<div class="handle" id="search" >
				<table width="98%" border="0" cellspacing="0" cellpadding="0"
					align="center">
					<tr>
						<td width="86%">
							<div id="search1" style="display: ">
								<form action="<%=basePath%>admin/query_user_by_page.do?pageNo=1" method="post">
									<div class="search_input">
										用户名：
										<input style="width:100px" type="text" name="username" value="${condition.username!=null?condition.username:'' }"/>
									</div>
									<div class="search_input">
										权限：
										<select style="width:100px"" name="authority">
											<option value=""  selected >全部</option>
											<option value="admin"
											<c:if test = "${condition.authority=='admin'}" >
											     selected
											</c:if>
											>管理员</option>
											<option value="user"
											<c:if test = "${condition.authority=='user'}" >
											     selected
											</c:if>
											>普通用户</option>
										</select>
									</div>
									<div class="search_input">
										<input type="hidden" name="type" value="find_like">
										<input type="submit"  value="查询" />
									</div>
								</form>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="list" id="list">
				<form name="data" method="post">
					<table class="flexme1">
					<thead>
							<tr>
								<th width="40">
									<input type="checkbox" id="ifAll" onClick="AllQuery()"/>
								</th>
								<th width="100">
									ID
								</th>
								<th width="100">
									用户名
								</th>
								<th width="820">
									权限
								</th>								
							</tr>
							</thead>
							<c:forEach items="${user_list}" var="user">
								<tr>
									<td >
							<input type="checkbox" name="selectFlag1" class="checkbox1" value="${user.id}"/>
									</td>
									<td>
										${user.id}
									</td>
									<td>
										${user.username}
									</td>
									<td>
										${user.authority}
									</td>
								</tr>
							</c:forEach>
					</table>
				</form>
			</div>
			
			<div class="bottom">
				<!--  <div class="search_btn" style="float:left" onclick="display_search()"></div>-->
				<div id="records" style="float:left">
					<input type="button" style="backgroundd-color:red;float:left" onclick="deleteUserItem()" value="删除选中用户"/>
				</div>
				<div class="pagefirst"
					onclick="topPage()"></div>
				<div class="pageup"
					onclick="previousPage()"></div>
				<div id="pages">
					${pageModel.totalPages==0?0:pageModel.pageNo} / ${pageModel.totalPages}
				</div>
				<div class="pagedown"
					onclick="nextPage()"></div>
				<div class="pagelast"
					onclick="bottomPage()"></div>
				<div id="records">
					共有 ${pageModel.totalPages} 页
				</div>

				<div class="blank"></div>
				<div class="blank"></div>
				<div class="blank"></div>				

				<div class="return"
					onclick="javascript:history.go(-1);"></div>
				<div id="records" style="float:right">
					返回
				</div>
				<!--  --><div class="clear"></div>
				
			</div>
		</div>
		
	</body>

</html>
