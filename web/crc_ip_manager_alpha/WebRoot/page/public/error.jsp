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
if (aa) {
history.go(-1);
}
else history.go(-1);
}



</script>
</head>
<body onload="a()">
<input type="hidden" id="fb" value="${error}"/>
<font color="#FF0000">${error}</font>
</body>
</html>