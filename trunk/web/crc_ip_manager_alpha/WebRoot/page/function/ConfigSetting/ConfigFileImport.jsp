<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  page language="java" import="java.util.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<String> frequencyOptions=Arrays.asList("60000","300000","600000","900000","1800000","3600000");
	String defaultFrequency=(String)request.getAttribute("frequency");
	System.out.println("defaultFrequency"+defaultFrequency);
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
		<form action="<%=basePath %>admin/load_config.do?path=" encType=multipart/form-data method="post">
			<div class="layout">
				<div class="title"> 
					配置文件导入 
				</div>
				
				<div id="content">
					<div class="four_columns">
						<div class="four_columns_text">
							填写路径:
						</div>
						<div class="four_columns_input">
							<input name="path" type=text value="C:\">


						</div>
						<div class="four_columns_input">
							<input type="submit" value="加载配置文件">
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
		</form>
	</body>

</html>


