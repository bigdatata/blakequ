<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function a(){

var error;
error = document.getElementById("fb").value;
var aa= window.confirm(error);

}
</script>
</head>
<body>
<%
 Exception ex = (Exception)request.getAttribute("exception");
 if(ex != null){ 
 	System.out.println("------------------page error------------------");
 } 
%>
<input type="hidden" id="fb" value="${error} ${exception!=null?exception.getMessage():''}"/>
<font color="#ff0000">${error} ${exception!=null?exception.getMessage():''}</font>
<H2></H2>
<P/>
</body>
</html>