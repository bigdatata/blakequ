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
		if (window.confirm("ȷ��ɾ����ǰ���ݣ�ע��Ŀǰֻ��ɾ����һ��ѡ�������")) {
			with(document.forms[0]) {
				action="<%=basePath %>computer_log/admin/delete_log.do?computer_id="+selectFlags[index].value;
				method="post";
				submit();
			}
		}
	}	
	
	
	
	function sortByComputerId(){
		with(document.forms[0]) {
				action="<%=basePath %>computer_log/get_by_station_name.do";
				method="post";
				submit();
			}
	}
	
	function searchComputerItem(){
		with(document.forms[0]) {
				action="<%=basePath %>computer_log/get_by_time.do?pageNo=1&queryString=";
				method="post";
				submit();
			}
	}
	
	function topPage() {
		//var searchStr = document.getElementsByName("searchStr");
		window.location = "<%=basePath %>computer_log/get_by_time.do?pageNo=1&queryString=";
	}
	
	function previousPage() {
		if(${pageModel.pageNo==1}){
			alert("�Ѿ������һҳ!");
		}else{
			window.location = "<%=basePath %>computer_log/get_by_time.do?pageNo=${pageModel.pageNo-1}&queryString=";
		}
	}
	
	function nextPage() {
		if(${pageModel.pageNo==pageModel.pageSize+1}){
			alert("�Ѿ��������һҳ!");
		}else{
			window.location = "<%=basePath %>computer_log/get_by_time.do?pageNo=${pageModel.pageNo+1}&queryString=";
		}
	}
	
	function bottomPage() {
		window.location = "<%=basePath %>computer_log/get_by_time.do?pageNo=${pageModel.buttomPageNo}&queryString=";
	}

	</script>
  </head>
  
  <body>
  	<form action="" method="post" >
    <div align="center">
    	<font color="#FF0000">վ�������־�б�</font>
    	<hr width="97%" align="center" size=0>
    			<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="17%" height="29">
							<div align="left">
								վ������/ID:
							</div>
						</td>
						<td width="57%">
							<input name="searchStr" type="text" class="text1"
								id="searchStr" size="50" maxlength="50" value="${queryStr}">
						</td>
						<td width="26%">
							<div align="left">
								<input name="btnQuery" type="button" class="button1"
									id="btnQuery" value="��ѯ" onclick="searchComputerItem()">
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
						CUPռ����
					</td>
					<td width="155" class="rd6">
						�ڴ�ռ����
					</td>
					<td width="155" class="rd6">
						��־��¼ʱ��
					</td>
				</tr>
				<c:forEach items="${computerLogs}" var="item">
					<tr>
						<td class="rd8">
							<input type="checkbox" name="selectFlag1" class="checkbox1" value="${item.id}">
						</td>
						<td class="rd8">
							<a href="#"
								onClick="window.open('computer_log/detail_computer.do?computer_id=${item.computer_id}', 'վ����ϸ��Ϣ', 'width=400, height=400, scrollbars=no');return false;">${item.computer_id}</a>
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
			<table width="95%" height="30" border="0" align="center"
				cellpadding="0" cellspacing="0" class="rd1">
				<tr>
					<td nowrap class="rd19" height="2" width="36%">
						<div align="left">
							<font color="#000000">&nbsp;��&nbsp${pageModel.totalPages}&nbspҳ</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="#000000">��ǰ��</font>&nbsp
							<font color="#FF0000">${pageModel.pageNo }</font>&nbsp
							<font color="#000000">ҳ</font>
						</div>
					</td>
					<td nowrap class="rd19" width="64%">
						<div align="center">
							<input name="btnTopPage" class="button1" type="button"
								id="btnTopPage" value="|&lt;&lt; " title="��ҳ"
								onClick="topPage()">
							<input name="btnPreviousPage" class="button1" type="button"
								id="btnPreviousPage" value=" &lt;  " title="��ҳ"
								onClick="previousPage()">
							<input name="btnNextPage" class="button1" type="button"
								id="btnNextPage" value="  &gt; " title="��ҳ" onClick="nextPage()">
							<input name="btnBottomPage" class="button1" type="button"
								id="btnBottomPage" value=" &gt;&gt;|" title="βҳ"
								onClick="bottomPage()">
							<input name="btnDelete" class="button1" type="button"
								id="btnDelete" value="ɾ��" onClick="deleteComputerItem()">
							<input name="btnBack" class="button1" type="button"
								id="btnSort" value="������ID����" onClick="sortByComputerId()">
							<input name="btnBack" class="button1" type="button"
								id="btnBack" value="����" onClick="javascript:history.go(-1);">
						</div>
					</td>
				</tr>
			</table>
		</form>
  </body>
</html>
