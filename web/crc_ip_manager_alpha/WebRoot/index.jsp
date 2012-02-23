<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>环形冗余IP数据网网管系统</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(pattern/cm/image/login/login_01.gif);
	overflow:hidden;
}
.STYLE3 {font-size: 12px; color: #FFFFFF; }
.STYLE4 {
	color: #FFFFFF;
	font-family: "方正大黑简体";
	font-size: 50px;
}
-->
</style>
<!--如果该页面在子框架中显示的时候，需要它回到父级框架显示-->
<script language="javascript">

  if (top.location != self.location) {
     top.location=self.location; 
  } 
</script>
</head>

<body>
<form action="<%=path%>/crc.jsp" method="post">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" background="pattern/cm/image/login/background.gif">
                  <tr height=325>
                  </tr>
                  <tr>
                  <td width=700>
                  </td>
                    <td ><input id="username" name="username" type="text" size="20" /></td>
                  </tr> 
                  <tr>
                     <td>
                  </td>
                    <td ><input id="password" name="password" type="password" size="20" /></td>
                   </tr>
                 
                 <tr>
                 <td>
                  </td>
                    <td align="left"><input type="image"  src="pattern/cm/image/login/dl.gif" width="47" height="16" /></td>
                 </tr>
                 <tr>
                 <td></td>
                </tr>
                  <tr height=28>
                    <td ><input type="hidden"/></td>
                    </tr>
                    <tr>
                    <td></td>
                    <td ><a href="<%=path%>/page/function/yhgl/user_add.jsp" target="_blank"><img src="pattern/cm/image/login/zc.gif"width="47" height="16"></a></td>                 
                  </tr>
                  <tr height=500>
                  </tr>
</table>
</form>
</body>
</html>
