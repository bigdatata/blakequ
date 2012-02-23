<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>acceptance_data</title>
		<link href="<%=path%>/pattern/cm/css/add.css" type="text/css"
			rel="stylesheet" />
			
	</head>
	<body>
		<form action="" method="post">
			<div class="layout">
				<div class="title">
					采集频率设置
				</div>
				
				<div id="content">
					<div class="four_columns">
						<div class="four_columns_text">
							频率选择:
						</div>
						<div class="four_columns_input">
							<select name="entity.frequency">
								<option value="30000">30秒</option>
								<option value="60000">1分钟</option>
								<option value="120000">2分钟</option>
								<option value="180000">3分钟</option>
								<option value="240000">4分钟</option>
								<option value="300000">5分钟</option>
							</select>
						</div>
						<div class="four_columns_input">
							<input type="submit" class="but" value="提交" />
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
		</form>
	</body>

</html>


