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
		<form action="<%=basePath %>/admin/config/FrequencySetting.do" method="post">
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
							<select name="frequency">
							<%
							for(int i=0;i<frequencyOptions.size();i++){
								String option=frequencyOptions.get(i);
							 %>
							 <option value="<%= option%>"  
							 <%if(defaultFrequency.equals(option)){
							  %>
							  selected
							  <%
							  } %>
							 ><%=Integer.valueOf(option)/(60*1000) %>分钟</option>
							 <%
							 }
							  %>
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


