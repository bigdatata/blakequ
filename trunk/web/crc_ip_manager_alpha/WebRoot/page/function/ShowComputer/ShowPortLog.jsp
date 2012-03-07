
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
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
	function deletePortItem(){
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
				action='<%=basePath%>port/admin/delete_port_log_by_ids.do?portLogIds='+ids;
				method="post";
				submit();
			}
		}	
	}
	
	
	
	function searchPortItem(){
		with(document.forms[0]) {
				action="<%=basePath %>port_log/get_single_log.do";
				method="post";
				submit();
			}
	}
	
	function topPage() {
		//var searchStr = document.getElementsByName("searchStr");
		window.location = "<%=basePath %>port/get_port_by_page.do?pageNo=1&routerId=";
	}
	
	function previousPage() {
		if(${pageModel.pageNo==1}){
			alert("已经到达第一页!");
		}else{
			window.location = "<%=basePath %>port/get_port_by_page.do?pageNo=${pageModel.pageNo-1}&routerId=";
		}
	}
	
	function nextPage() {
		if(${pageModel.pageNo==pageModel.totalPages}){
			alert("已经到达最后一页!");
		}else{
			window.location = "<%=basePath %>port/get_port_by_page.do?pageNo=${pageModel.pageNo+1}&routerId=";
		}
	}
	
	function bottomPage() {
		window.location = "<%=basePath %>port/get_port_by_page.do?pageNo=${pageModel.buttomPageNo}&routerId=";
	}
	
	</script>
	</head>
	<body>

		<div id="layout">
			<div class="title">
				站点路由日志列表
			</div>
			<div class="handle" id="search" >
				<table width="98%" border="0" cellspacing="0" cellpadding="0"
					align="center">
					<tr>
						<td width="86%">
							<div id="search1" style="display: ">
								<form action="<%=basePath%>admin/query_port_by_page.do?pageNo=1" method="post">
									<div class="search_input">
										站点名称：
										<input style="width:100px" type="text" name="id" value="${condition.stationName!=null?condition.stationName:'' }"/>
									</div>
									<div class="search_input">
										起始时间：
										<input style="width:100px" type="text" id="beginDate" name="beginDate" onclick="MyCalendar.SetDate(this)"/>
									</div>
									<div class="search_input">
										结束时间：
										<input style="width:100px" type="text" id="endDate" name="endDate" onclick="MyCalendar.SetDate(this)"/>
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
									<input type="checkbox" name="ifAll" id="ifAll"
										onClick="AllQuery()">
								</th>
								<th width="60" >
									所属站点
								</th>
								<th width="60" >
									端口名称
								</th>
								<th width="60" >
									端口状态
								</th>
								<th width="100" >
									流入流量(Bytes)
								</th>
								<th width="100" >
									流出流量(Bytes)
								</th>
								<th width="110" >
									流入速率(bits/second)
								</th>
								<th width="110" >
									流出速率(bits/second)
								</th>
								<th width="80" >
									循环冗余错误包
								</th>
								<th width="100" >
									端口IP
								</th>
								<th width="150" >
									时间
								</th>
								
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ports}" var="item">
						<tr>
						<td >
							<input type="checkbox" name="selectFlag1" class="checkbox1" value="${item.id}">
						</td>
						<td>
						${item.stationName }
						</td>
						<td>
							${item.ifDescr}
						</td>
						
						<td >
						${item.ifOperStatus }
						</td>

						<td >
							${item.ifInOctets}
						</td>
						<td >
							${item.ifOutOctets}
						</td>
						<td >
							${item.locIfInBitsSec}
						</td>
						<td >
							${item.locIfOutBitsSec}
						</td>
						<td>
						${item.locIfInCrc }
						</td>
						<td>
						${item.portIp }
						</td>
						<td>
						${item.getTime }
						</td>
						
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
				</form>
			</div>
			<div class="bottom">
			<div id="records" style="float:left">
					<input type="button" style="backgroundd-color:red;float:left" onclick="deletePortItem()" value="删除选中项目"/>
				</div>
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
