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
    
    <title>My JSP 'show_user.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function AllQuery()
	{
		var checkBox=document.getElementById("ifAll");
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
	
	function deleteItem() {
		var selectFlags = document.getElementsByName("selectFlag1");
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
		if (window.confirm("确认删除当前用户？注：目前只能删除第一个选择的数据")) {
			with(document.forms[0]) {
				action="<%=basePath %>admin/delete_user.do?user_id="+selectFlags[index].value;
				method="post";
				submit();
			}
		}
	}	
	</script>
  </head>
  
  <body>
  	<form action="" method="post" >
        用户列表. <br>
    <table width="95%" border="1" cellspacing="0" cellpadding="0"
				align="center" class="table1">
				<tr>
					<td width="35" class="rd6">
						<input type="checkbox" name="ifAll" id="ifAll" onClick="AllQuery()">
					</td>
					<td width="155" class="rd6">
						id
					</td>
					<td width="155" class="rd6">
						名字
					</td>
					<td width="155" class="rd6">
						密码
					</td>
					<td width="155" class="rd6">
						权限
					</td>
				</tr>
				<c:forEach items="${user_list}" var="item">
					<tr>
						<td class="rd8">
							<input type="checkbox" name="selectFlag1" class="checkbox1" value="${item.id}">
						</td>
						<td class="rd8">
							${item.id}
						</td>
						<td class="rd8">
							${item.username}
						</td>
						<td class="rd8">
							${item.password}
						</td>
						<td class="rd8">
							${item.authority}
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
							<input name="btnBack" class="button1" type="button"
								id="btnBack" value="返回" onClick="javascript:history.go(-1);">
						</div>
			</td>
		</form>
  </body>
</html>
