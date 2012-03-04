<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<select name="station_id" id="station_id">
<c:forEach items="${station_list}" var="sif">
<option value="${sif.id}">
${sif.name}
</option>
</c:forEach>	
</select>
