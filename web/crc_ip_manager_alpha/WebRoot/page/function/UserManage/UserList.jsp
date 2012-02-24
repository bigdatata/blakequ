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
	</head>
	<body>

		<div id="layout">
			<div class="title">
				用户列表
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
								<form action="<%=path%>/input/jcd/jcdList.do" method="post">
									<div class="search_input">
										请输入查询条件 ：
										<input style="width: 200px; height: 12px;" type="text" name="condition"/>
									</div>
									<div class="search_input">
										<input type="hidden" name="type" value="find_like">
										<input type="submit" style="width: 50px; height: 20px;" value="查询" />
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
									标记
								</th>
								<th width="100">
									用户名
								</th>
								<th width="200">
									用户了类型
								</th>
								<th width="92">
									操作
								</th>
								
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${user_list}" var="user">
								<tr>
									<td>
										<input type="checkbox" value="${user.id}">
									</td>
									<td >
										${user.username}
									</td>
									<td>
										${user.authority}
									</td>
								
									<td>
									
									<c:if test="${current_user.authority == 'admin'}">
		    				
		    			
									&nbsp;
										<a
											href="<%=path%>/fhq/fhqgzInfo.action?type=delete&ids="+${user.id}>删除</a>&nbsp;&nbsp;
										&nbsp;
										
										</c:if>									
									</td>
									
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
				</form>
			</div>
			<div class="bottom">
				<div class="pageup"
					onclick="window.location.href='<%=basePath%>attackLogList.action?type=find_all_desc&currentPage=${previousPage}'"></div>
				<div id="pages">
					${currentPage} of ${totalPages}
				</div>
				<div class="pagedown"
					onclick="window.location.href='<%=basePath%>attackLogList.action?type=find_all_desc&currentPage=${nextPage}'"></div>
				<div id="records">
					共有 ${totalRows} 条记录
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</body>

</html>
