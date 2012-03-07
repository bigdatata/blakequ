
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
			<script src="<%=path%>/pattern/cm/js/date.js"
			language="javascript" type="text/javascript"></script>
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
	
	function deleteComputerItem() {
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
				action='<%=basePath%>computer_log/admin/delete_computer_log_by_ids.do?computerLogIds='+ids;
				method="post";
				submit();
			}
		}
	}	
	function sortByComputerId(){
		with(document.forms[0]) {
				action="<%=basePath %>computer_log/get_by_station_name.do?pageNo=1&queryString=";
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
		window.location = "<%=basePath %>computer_log/get_log_by_page.do?pageNo=1&stationName=${stationName!=null?stationName:'' }&beginDate=${beginDate!=null?beginDate:'' }&endDate=${endDate!=null?endDate:''}";
	}
	
	function previousPage() {
		if(${pageModel.pageNo<=1}){
			alert("已经到达第一页!");
		}else{
			window.location = "<%=basePath %>computer_log/get_log_by_page.do?pageNo=${pageModel.pageNo-1}&stationName=${stationName!=null?stationName:'' }&beginDate=${beginDate!=null?beginDate:'' }&endDate=${endDate!=null?endDate:''}";
		}
	}
	
	function nextPage() {
		if(${pageModel.pageNo>=pageModel.totalPages}){
			alert("已经到达最后一页!");
		}else{
			window.location = "<%=basePath %>computer_log/get_log_by_page.do?pageNo=${pageModel.pageNo+1}&stationName=${stationName!=null?stationName:'' }&beginDate=${beginDate!=null?beginDate:'' }&endDate=${endDate!=null?endDate:''}";
		}
	}
	
	function bottomPage() {
		window.location = "<%=basePath %>computer_log/get_log_by_page.do?pageNo=${pageModel.buttomPageNo}&stationName=${stationName!=null?stationName:'' }&beginDate=${beginDate!=null?beginDate:'' }&endDate=${endDate!=null?endDate:''}";
	}

	</script>
	</head>
	<body>

		<div id="layout">
			<div class="title">
				站点电脑日志列表
			</div>
					<div class="handle" id="search" >
				<table width="98%" border="0" cellspacing="0" cellpadding="0"
					align="center">
					<tr>
						<td width="86%">
							<div id="search1" style="display: ">
								<form action="<%=basePath%>computer_log/get_log_by_page.do?pageNo=1" method="post">
									<div class="search_input">
										站点名称：
										<input style="width:100px" type="text" name="stationName" value="${stationName!=null?stationName:'' }"/>
									</div>
									<div class="search_input">
										起始时间：
										<input style="width:100px" type="text" name="beginDate"  id="beginDate" value="${beginDate!=null?beginDate:'' }" onclick="MyCalendar.SetDate(this)"/>
									</div>
									<div class="search_input">
										结束时间：
										<input style="width:100px" type="text" name="endDate" id="endDate" value="${endDate!=null?endDate:'' }" onclick="MyCalendar.SetDate(this)"/>
									</div>
									<div class="search_input">
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
									<input type="checkbox" name="ifAll" id="ifAll"
										onClick="AllQuery()">
								</th>
								<th width="200" >
									所属站点
								</th>
								<th width="200" >
									站点ID
								</th>

								<th width="200" >
									CUP占有率
								</th>
								<th width="200" >
									内存占有率
								</th>
								<th width="200" >
									日志记录时间
								</th>
								
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${computerLogs}" var="item">
								<tr>
									<td >
										<input type="checkbox" name="selectFlag1" class="checkbox1"
											value="${item.id}">
									</td>
									<td>
										${item.computer.station.name!=null?item.computer.station.name:''}
									</td>
									<td>
									${item.id}
									</td>
									<td >
										${item.cupRate}
									</td>
									<td >
										${item.memRate}
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
				<div id="records" style="float:left">
					<input type="button" style="backgroundd-color:red;float:left" onclick="deleteComputerItem()" value="删除选中项目"/>
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
<!-- 				<div id="records">
					按电脑ID排序
				</div>
				<div class="computer"
					onclick="sortByComputerId()"></div> -->

				<div class="blank"></div>
				<div class="blank"></div>
				<div class="blank"></div>

				
				
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
