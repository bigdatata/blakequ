<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    
<html>
	<head>
		<base href="<%=basePath %>">
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>物料维护</title>
		<link rel="stylesheet" href="style/drp.css">
		<script src="script/windows.js"></script>
		<script type="text/javascript">
	function addItem() {
		//window.self.location = "item_add.html";
		window.location="<%=basePath %>item.do?command=showAdd";
	}
	
	function modifyItem() {
		var selectFlags = document.getElementsByName("selectFlag");
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
			alert("一次只能修改一个物料！");
			return;
		}
		window.self.location = "<%=basePath %>item.do?command=showModify&itemNo=" + selectFlags[index].value;
	}
	
	function deleteItem() {
		var selectFlags = document.getElementsByName("selectFlag");
		var flag = false;
		for (var i=0; i<selectFlags.length; i++) {
			if (selectFlags[i].checked) {
			    //已经有选中的checkbox
				flag = true;
				break;
			}
		}
		if (!flag) {
			alert("请选择需要删除的数据！");
			return;
		}	
		//删除提示
		if (window.confirm("确认删除？")) {
			with(document.getElementById("itemForm")) {
				action="item.do?command=del";
				method="post";
				submit();
			}
		}
	}	
	
	function searchItem() {
		with(document.forms[0]) {
			action="<%=basePath %>item.do";
			method="post";
			submit();
		}
	}
	
	function topPage() {
		window.location = "<%=basePath %>item.do?pageNo=${pageModel.topPageNo}&clientIdOrName=${param.clientIdOrName}";
	}
	
	function previousPage() {
		window.location = "<%=basePath %>item.do?pageNo=${pageModel.previousPageNo}&clientIdOrName=${param.clientIdOrName}";
	}
	
	function nextPage() {
		window.location = "<%=basePath %>item.do?pageNo=${pageModel.nextPageNo}&clientIdOrName=${param.clientIdOrName}";
	}
	
	function bottomPage() {
		window.location = "<%=basePath %>item.do?pageNo=${pageModel.buttomPageNo}&clientIdOrName=${param.clientIdOrName}";
	}
	
	function checkAll() {
		var selectFlags = document.getElementsByName("selectFlag");
		for (var i=0; i<selectFlags.length; i++) {
			selectFlags[i].checked = document.getElementById("ifAll").checked;
		}
	}
	
	function uploadPic4Item() {
		var selectFlags = document.getElementsByName("selectFlag");
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
			alert("请选择需要上传的物料！");
			return;
		}
		if (count > 1) {
			alert("一次只能上传一个物料图片！");
			return;
		}
		window.self.location = "<%=basePath %>item.do?command=showUpload&itemNo=" + selectFlags[index].value;
	}
</script>
	</head>

	<body class="body1">
		<form name="itemForm">
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
							<img src="images/mark_arrow_02.gif" alt="我" width="14"
								height="14">
							&nbsp;
							<b>基础数据管理&gt;&gt;物料维护</b>
						</td>
					</tr>
				</table>
				<hr width="97%" align="center" size=0>
				<table width="95%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="17%" height="29">
							<div align="left">
								物料代码/名称:
							</div>
						</td>
						<td width="57%">
							<input name="clientIdOrName" type="text" class="text1"
								id="clientIdOrName" size="50" maxlength="50" value="${param.clientIdOrName}">
						</td>
						<td width="26%">
							<div align="left">
								<input name="btnQuery" type="button" class="button1"
									id="btnQuery" value="查询" onclick="searchItem()">
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

			</div>
			<table width="95%" border="0" cellspacing="0" cellpadding="0"
				class="rd1" align="center">
				<tr>
					<td nowrap height="10" class="p2">
						物料信息&nbsp;&nbsp;
					</td>
					<td nowrap height="10" class="p3">
						&nbsp;
					</td> 
				</tr>
			</table>
			<table width="95%" border="1" cellspacing="0" cellpadding="0"
				align="center" class="table1">
				<tr>
					<td width="35" class="rd6">
						<input type="checkbox" name="ifAll" onClick="checkAll()">
					</td>
					<td width="155" class="rd6">
						物料代码
					</td>
					<td width="155" class="rd6">
						物料名称
					</td>
					<td width="155" class="rd6">
						物料规格
					</td>
					<td width="155" class="rd6">
						物料型号
					</td>
					<td width="138" class="rd6">
						类别
					</td>
					<td width="101" class="rd6">
						计量单位
					</td>
				</tr>
				<c:forEach items="${pageModel.list}" var="item">
					<tr>
	
						<td class="rd8">
							<input type="checkbox" name="selectFlag" class="checkbox1" value="${item.itemNo }">
						</td>
						<td class="rd8">
							<a href="#"
								onClick="window.open('item.do?command=showDetail&itemNo=${item.itemNo }', '物料详细信息', 'width=400, height=400, scrollbars=no');return false;">${item.itemNo }</a>
						</td>
						<td class="rd8">
							${item.itemName }
						</td>
						<td class="rd8">
							${item.spec }
						</td>
						<td class="rd8">
							${item.pattern }
						</td>
						<td class="rd8">
							${item.itemCategory.name }
						</td>
						<td class="rd8">
							${item.itemUnit.name }
						</td>
					</tr>
				</c:forEach>
			</table>
			<table width="95%" height="30" border="0" align="center"
				cellpadding="0" cellspacing="0" class="rd1">
				<tr>
					<td nowrap class="rd19" height="2" width="36%">
						<div align="left">
							<font color="#FFFFFF">&nbsp;共&nbsp${pageModel.totalPages }&nbsp页</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font color="#FFFFFF">当前第</font>&nbsp
							<font color="#FF0000">${pageModel.pageNo }</font>&nbsp
							<font color="#FFFFFF">页</font>
						</div>
					</td>
					<td nowrap class="rd19" width="64%">
						<div align="right">
							<input name="btnTopPage" class="button1" type="button"
								id="btnTopPage" value="|&lt;&lt; " title="首页"
								onClick="topPage()">
							<input name="btnPreviousPage" class="button1" type="button"
								id="btnPreviousPage" value=" &lt;  " title="上页"
								onClick="previousPage()">
							<input name="btnNextPage" class="button1" type="button"
								id="btnNextPage" value="  &gt; " title="下页" onClick="nextPage()">
							<input name="btnBottomPage" class="button1" type="button"
								id="btnBottomPage" value=" &gt;&gt;|" title="尾页"
								onClick="bottomPage()">
							<input name="btnAdd" type="button" class="button1" id="btnAdd"
								value="添加" onClick="addItem()">
							<input name="btnDelete" class="button1" type="button"
								id="btnDelete" value="删除" onClick="deleteItem()">
							<input name="btnModify" class="button1" type="button"
								id="btnModify" value="修改" onClick="modifyItem()">
							<input name="btnUpload" class="button1" type="button"
								id="btnUpload" value="上传图片" onClick="uploadPic4Item()">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
