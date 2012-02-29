<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="java.text.SimpleDateFormat"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>环形冗余IP网络管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="pattern/cm/theme/style/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/pattern/cm/js/svg/jquery-1.2.6.js"></script>
<script type="text/javascript" src="<%=path%>/pattern/cm/js/svg/jquery.funkyUI.js"></script>
</head>
<body>
<div id="header">
  <div style="background:url(pattern/cm/theme/images/logo000.jpg) no-repeat;">
    <div id="innerHeader">
      <div id="innerHeaders"><!--备用--></div>
      <div id="topguide">
        <ul id="topguide-entry">
         
        
       
           <li id="StationMonitor" onMouseOver="showguide(this.id);"><a href="javascript:"
           onClick="return initguide('StationMonitor');">线路监控</a></li>
             <li id="AlarmManage" onMouseOver="showguide(this.id);"><a href="javascript:"
           onClick="return initguide('AlarmManage');">告警信息管理</a></li>
           <li id="ShowComputer" onMouseOver="showguide(this.id);"><a href="javascript:"
           onClick="return initguide('ShowComputer');">站点电脑信息</a></li>
             <li id="StationModify" onMouseOver="showguide(this.id);"><a href="javascript:"
           onClick="return initguide('StationModify');">线路信息管理</a></li>
            <li id="FrequencySetting" onMouseOver="showguide(this.id);"><a href="javascript:"
           onClick="return initguide('FrequencySetting');">采集频率设置</a></li>
           <li id="UserManage" onMouseOver="showguide(this.id);"><a href="javascript:"
           onClick="return initguide('UserManage');">用户管理</a></li>          
        </ul>
      </div>
    </div>
    <div class="c"></div>
    <div id="guide">
    	<span class="fr s1" style="margin:0 16px">
    		当前日期-<%=new SimpleDateFormat("yyyy年MM月dd日").format(new Date()) %> | 用户: ${user.username}　级别: ${user.authority}　|　
    		<a class="s0" href="/crc_ip_manager_alpha/login_out.do">退出</a>
    	</span>
    	<div id="guidenavigate"></div>
    </div>
  </div>
</div>
<div id="selector">
  <div id="left" class="inner"></div>
</div>
<div id="gird">
  <div class="inner">
    <iframe name="main" frameborder="0" scrolling=yes style="height:85%;visibility:inherit;width:100%;z-index:1" src="index.jsp"></iframe>
  </div>
</div>
<div id="footer">
  <div id="innerFooter"></div>
  <input id="userame" type="hidden" value="${user.username}"/>
</div>
<iframe name="notice" frameborder="0" style="height:0;width:0;"></iframe>
<div id="menu" style="display:none"></div>
<div id="showmenu" style="display:none"></div>
<script language="JavaScript" src="pattern/cm/theme/js/menumisc.js"></script> 
<script language="JavaScript" src="pattern/cm/theme/js/deployinit.js"></script>
</body>
</html>

