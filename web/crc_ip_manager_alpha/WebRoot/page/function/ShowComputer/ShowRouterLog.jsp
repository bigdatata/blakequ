
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
		<title>无标题文档</title>
		<script type="text/javascript">
	function AllQuery()
	{
		var checkBox=document.getElementById("ifAll2");
		var checkBoxAll=document.getElementsByTagName('input');
		for(var i = 0 ; i < checkBoxAll.length ; i++)
		{
			if(checkBoxAll[i].type == "checkbox")
			{
				checkBoxAll[i].checked = checkBox.checked;
			}
		}
	}
	
	function deleteRouterItem(){
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
		if (window.confirm("确认删除当前数据？注：目前只能删除第一个选择的数据")) {
			with(document.forms[0]) {
				action="<%=basePath %>router_log/admin/delete_log.do?router_id="+selectFlags[index].value;
				method="post";
				submit();
			}
		}
	}
	
	function sortByRouterId(){
		with(document.forms[0]) {
				action="<%=basePath %>router_log/get_log_by_id.do";
				method="post";
				submit();
			}
	}
	
	
	function searchRouterItem(){
		with(document.forms[0]) {
				action="<%=basePath %>router_log/get_single_log.do";
				method="post";
				submit();
			}
	}
	</script>
	</head>
	<body>

		<div id="layout">
			<div class="title">
				站点路由日志列表
			</div>
					<div class="handle">

				<div class="line"></div>

				<div class="search_btn" onclick="display_search()"></div>
				<div class="clear"></div>
			</div>
			<div class="handle" id="search" style="display: none">
				<table width="98%" border="0" cellspacing="0" cellpadding="0"
					align="center">
					<tr>
						<td width="86%">
							<div id="search1" style="display: ">
								<form>
									<div class="search_input">
										站点名称/ID:
										<input name="searchStr" type="text" class="text1"
										id="searchStr" size="50" maxlength="50" value="${queryStr}">
									</div>
									<div class="search_input">
										<input type="hidden" name="type" value="find_like">
										<input name="btnQuery" type="button" class="button1"
											id="btnQuery" value="查询" onclick="searchRouterItem()">
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
									<input type="checkbox" name="ifAll" id="ifAll"
										onClick="AllQuery()">
								</th>
								<th width="155" >
									router_ID
								</th>
								<th width="155" >
									ID
								</th>
								<th width="155" >
									CUP占有率
								</th>
								<th width="155" >
									内存占有率
								</th>
								<th width="155" >
									路由信息
								</th>
								<th width="155" >
									日志记录时间
								</th>
								
								
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${routerLogs}" var="item">
								<tr>
									<td >
							<input type="checkbox" name="selectFlag2" class="checkbox1" value="${item.id}">
						</td>
						<td >
							<a href="#"
								onClick="window.open('router_log/detail_router.do?router_id=${item.routerId}', '站点详细信息', 'width=400, height=400, scrollbars=no');return false;">${item.routerId}</a>
						</td>
						<td >
							${item.id}
						</td>
						<td >
							${item.cpuRate}
						</td>
						<td >
							${item.memRate}
						</td>
						<td >
							${item.routerInfo}
						</td>
						<td >
							${item.currTime}
						</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
				</form>
			</div>
			<div class="bottom">
				<div class="pagefirst"
					onclick="topPage()"></div>
				<div class="pageup"
					onclick="previousPage()"></div>
				<div id="pages">
					${pageModel.pageNo } of ${pageModel.totalPages}
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
				<div id="records">
					按路由ID排序
				</div>
				<div class="computer"
					onclick="sortByRouterId()"></div>
				<div class="blank"></div>
				<div class="blank"></div>
				<div class="blank"></div>
				<div id="records">
					删除选中项目
				</div>
				<div class="delete"
					onclick="deleteRouterItem()"></div>
				
				<div class="return"
					onclick="javascript:history.go(-1);"></div>
				<div id="records" style="float:right">
					返回
				</div>
				<div class="clear"></div>
				
			</div>
		</div>
	</body>

</html>
