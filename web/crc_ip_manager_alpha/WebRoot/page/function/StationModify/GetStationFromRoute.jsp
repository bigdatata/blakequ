<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:forEach items="${station_list}" var="sif">
<option value="${sif.id}">
${sif.name}
</option>
</c:forEach>	

