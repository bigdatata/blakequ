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
	
	
	function deleteComputerItem() {
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
		if (window.confirm("确认删除当前数据？注：目前只能删除第一个选择的数据")) {
			with(document.forms[0]) {
				action="<%=basePath %>computer_log/admin/delete_log.do?computer_id="+selectFlags[index].value;
				method="post";
				submit();
			}
		}
	}	
	
	
	
	function sortByComputerId(){
		with(document.forms[0]) {
				action="<%=basePath %>computer_log/get_log_by_id.do";
				method="post";
				submit();
			}
	}
	
	function searchComputerItem(){
		with(document.forms[0]) {
				action="<%=basePath %>computer_log/get_single_log.do";
				method="post";
				submit();
			}
	}

	</script>
  </head>
  
  <body>
  	<form action="" method="post" >
    <div align="center">
    	<font color="#FF0000">站点电脑日志列表</font>
    	<hr width="97%" align="center" size=0>
    			<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="17%" height="29">
							<div align="left">
								站点名称/ID:
							</div>
						</td>
						<td width="57%">
							<input name="searchStr" type="text" class="text1"
								id="searchStr" size="50" maxlength="50">
						</td>
						<td width="26%">
							<div align="left">
								<input name="btnQuery" type="button" class="button1"
									id="btnQuery" value="查询" onclick="searchComputerItem()">
							</div>
						</td>
					</tr>
					<tr>
						<td height="16">
							<div align="right"></div>
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							<div align="right"></div>
						</td>
					</tr>
				</table>
    	<table width="95%" border="1" cellspacing="0" cellpadding="0"
				align="center" class="table1">
				<tr>
					<td width="35" class="rd6">
						<input type="checkbox" name="ifAll" id="ifAll" onClick="AllQuery()">
					</td>
					<td width="155" class="rd6">
						computer_ID
					</td>
					<td width="155" class="rd6">
						ID
					</td>
					<td width="155" class="rd6">
						CUP占有率
					</td>
					<td width="155" class="rd6">
						内存占有率
					</td>
					<td width="155" class="rd6">
						日志记录时间
					</td>
				</tr>
				<c:forEach items="${computerLogs}" var="item">
					<tr>
						<td class="rd8">
							<input type="checkbox" name="selectFlag1" class="checkbox1" value="${item.id}">
						</td>
						<td class="rd8">
							<a href="#"
								onClick="window.open('computer_log/detail_computer.do?computer_id=${item.computer_id}', '站点详细信息', 'width=400, height=400, scrollbars=no');return false;">${item.computer_id}</a>
						</td>
						<td class="rd8">
							${item.id}
						</td>
						<td class="rd8">
							${item.cupRate}
						</td>
						<td class="rd8">
							${item.memRate}
						</td>
						<td class="rd8">
							${item.currTime}
						</td>
					</tr>
				</c:forEach>
			</table>
			</div>
			<td nowrap class="rd19" width="10%">
						<div align="center">
							<input name="btnDelete" class="button1" type="button"
								id="btnDelete" value="删除" onClick="deleteComputerItem()">
							<input name="btnBack" class="button1" type="button"
								id="btnSort" value="按电脑ID排序" onClick="sortByComputerId()">
							<input name="btnBack" class="button1" type="button"
								id="btnBack" value="返回" onClick="javascript:history.go(-1);">
						</div>
			</td>
		</form>
  </body>
</html>
