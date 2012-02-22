<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,java.sql.*" %>
<%@ page import="cm.commons.pojos.Station" %> 
<% 
Station station = (Station)request.getAttribute("station");
out.print("站点名称  : "+station.getName()+"<br/>"+"站点状态  :  "+station.getState()+"<br/>"+"上行站点  :  "+station.getSegmentsForStation1Id()+"<br/>"+"下行站点"+station.getSegmentsForStation2Id());
%>