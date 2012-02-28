<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cm.commons.controller.form.SegmentForm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page import="fb.info.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
		<title>Insert title here</title>

		<link href="<%=path%>/pattern/cm/css/svg.css" rel="stylesheet"
			type="text/css" />
		<script language="JavaScript" src="<%=path%>/pattern/cm/js/svg/svg.js"></script>
		<script type="text/javascript"
			src="<%=path%>/pattern/cm/js/svg/jquery-1.2.6.js"></script>
		<script type="text/javascript"
			src="<%=path%>/pattern/cm/js/svg/jquery.funkyUI.js"></script>
		<script type="text/javascript">
	//function a 测试变色
	function a() {
		var abc;
		abc = document.getElementById("me");
		abc.setAttribute("style", "fill:red");
		alert("asdasd!");
	}
	//**********************************
	//
	//关于动态弹窗的说明
	//右击选择查看台账信息，传入元素id通过AJAX动态获取数据并进行显示
	//
	//**********************************
	function b(o) {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				$.alert(xmlhttp.responseText);
				//document.getElementById("txtHint").innerHTML=xmlhttp.responseText;
			}
		}

		xmlhttp.open("POST", "detail_station.do?station_id=" + o.id, true);
		xmlhttp.send();
	}
	//***********
	//
	//关于告警信息的说明：
	//页面加载时获取告警信息。
	//若是站点告警则使用document.getElementsByName的方法进行查找
	//若是线段告警则使用document.getElementById的方法进行查找
	//
	//***********	
	window.onload = function() {
		setInterval(function() {
			//执行告警信息的请求
			//if(告警信息!=null)
			//{
			//变色&声音告警
			//}
			//ajax请求 
				
			}, 5000);

	};
</script>
	</head>

	<%	
//**********************
//
//获取线段和站点坐标信息
//JSP中嵌套java代码,通过request获取后台传来的数据，保存在建好的类中
//
//***********************
fbStation[] sxy = new fbStation[100];
fbSegment[] sg = new fbSegment[100];
int sxy_num=0;
int sg_num=0;
List<cm.commons.controller.form.SegmentForm> o = (List<cm.commons.controller.form.SegmentForm>)request.getAttribute("segment_list");
for(cm.commons.controller.form.SegmentForm u: o){
	sg[sg_num] = new fbSegment(u.getId(),u.getStartX(),u.getStartY(),u.getEndX(),u.getEndY());
	sg_num++;
}		
List<cm.commons.controller.form.StationForm> t = (List<cm.commons.controller.form.StationForm>)request.getAttribute("station_list");
for(cm.commons.controller.form.StationForm u: t){
  	sxy[sxy_num] = new fbStation(u.getId(),u.getName(),u.getX(),u.getY());
	sxy_num++;
 }
%>
	<body>
		<div class="container">
			<!-- 
		
----------这部分是右侧边栏的代码--------
		
		 -->
			<div class="sidebar">
				<form action="<%=basePath%>main.do" method="post">
					<table width="120px">
						<tr style="font-size: 18px">
							<td>
								当前线路：
							</td>
						</tr>
						<tr style="font-size: 18px; color: red">
							<td>
								<strong> <c:forEach items="${all_route}" var="sif">

										<c:if test="${sif.id == current_route_id}">
		    				${sif.name}
		    			</c:if>

									</c:forEach> </strong>
							</td>
						</tr>
						<tr>
							<td style="color: white">
								========================
							</td>
						</tr>
						<tr>
							<td style="font-size: 18px">
								请选择查看的线路
							</td>
						</tr>
						<tr>
							<td>
								<select name="route_id">
									<c:forEach items="${all_route}" var="sif">
										<option value="${sif.id}"
											<c:if test = "${sif.id == current_route_id}" >
		    				selected
		    			</c:if>>
											${sif.name}
										</option>

									</c:forEach>

								</select>
								<input type="submit" value="确定" />
							</td>
						</tr>
						<tr>
							<td style="color: white">
								========================
							</td>
						</tr>
						<tr>
							<td>
								<button type="button" onclick=
	;
>
									前一条线路
								</button>
							</td>
						</tr>
						<tr>
							<td>
								<button type="button" onclick=
	;
>
									后一条线路
								</button>
							</td>
						</tr>
						<tr>
							<td>
								<input type="hidden" value="<%=request.getAttribute("alarm")%>"
									id="al" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="content">
				<!-- 
这部分是画图的主页面
画图技术：svg

 -->
				<svg version="1.1" xmlns="http://www.w3.org/2000/svg">
				<% 
					//**********************************绘制线段********************************
					//将线段的起止坐标延长3px，使得线段衔接更紧凑
					//
					//
					double x1,y1,x2,y2;
					for (int i = 0; i < sg_num; i++) {
					
					if(sg[i].getStartX()<sg[i].getEndX())
					{
					x1 = sg[i].getStartX()-3;
					x2 = sg[i].getEndX()+3;
					y1 = sg[i].getStartY();
					y2 = sg[i].getEndY();
					}
					else if(sg[i].getStartX()>sg[i].getEndX())
					{
					x1 = sg[i].getStartX()+3;
					x2 = sg[i].getEndX()-3;
					y1 = sg[i].getStartY();
					y2 = sg[i].getEndY();
					}
					else if(sg[i].getStartY()<sg[i].getEndY())
					{
					x1 = sg[i].getStartX();
					x2 = sg[i].getEndX();
					y1 = sg[i].getStartY()-3;
					y2 = sg[i].getEndY()+3;
					}
					else if(sg[i].getStartY()>sg[i].getEndY())
					{
					x1 = sg[i].getStartX();
					x2 = sg[i].getEndX();
					y1 = sg[i].getStartY()+3;
					y2 = sg[i].getEndY()-3;
					}
					else
					{
					x1 = sg[i].getStartX();
					x2 = sg[i].getEndX();
					y1 = sg[i].getStartY();
					y2 = sg[i].getEndY();
					}
						out
								.print("<line id=\""
										+ i
										+ "\" x1=\""
										+ x1
										+ "\" y1=\""
										+ y1
										+ "\" x2=\""
										+ x2
										+ "\" y2=\""
										+ y2
										+ "\" style=\"stroke:rgb(99,99,99);stroke-width:7\" />");
					}

					//***********************下面是绘制站点和站点名****************//
					//第一个站点为分中心站点，使用大椭圆进行绘制，站点名绘制在椭圆正中心
					//其余的站点为普通站点，使用小圆绘制，站点名绘制在站点位置偏右10px
					for (int i = 0; i < sxy_num; i++) {
						if (i == 0) {
							String sa = sxy[i].getName();
							double sx = (sxy[i].getX() - 8 * sa.length());//将站名置于正中
							out
									.print("<ellipse id=\""
											+ sxy[i].getId()
											+ "\" cx=\""
											+ sxy[i].getX()
											+ "\" cy=\""
											+ sxy[i].getY()
											+ "\" rx=\"100\" ry=\"50\" stroke=\"black\" stroke-width=\"2\" style=\"fill:yellow\"/>");
							out.print("<text id=\"" + sxy[i].getId() + "\" x=\"" + sx
									+ "\" y=\"" + sxy[i].getY() + "\""
									+ "style=\"font-size:24\">" + sxy[i].getName()
									+ "</text>");
						} else {
							double sx = (sxy[i].getX() + 10);//将站名置于右边
							double sy = (sxy[i].getY() + 5);
							out
									.print("<circle id=\""
											+ sxy[i].getId()
											+ "\" name=\""
											+ sxy[i].getId()
											+ "\" cx=\""
											+ sxy[i].getX()
											+ "\" cy=\""
											+ sxy[i].getY()
											+ "\" r=\"10\" stroke=\"black\" stroke-width=\"2\" style=\"fill:blue\"/>");
							out.print("<text id=\"" + sxy[i].getId() + "\" x=\"" + sx
									+ "\" y=\"" + sy + "\">"
									+ sxy[i].getName() + "</text>");
						}
					}
				%>

				<circle id="me" name="ass" cx="20" cy="20" r="10" stroke="black"
					stroke-width="2" style="fill:blue" />
				</svg>
			</div>

		</div>
		<!--  
		---下面是右击菜单的div
		---在右击的情况下触发
		---
		-->
		<div id="bmenu"
			style="position: absolute; display: none; top: 0px; left: 0px; width: 150px; margin: 0px; padding: 2px; border: 1px solid #cccccc; background-color: #CEE2FF;">
			<ul>
				<li id="checkLink">
					查看台账信息
				</li>
				<li id="edit">
					查看路由信息
				</li>
				<li id="del">
					查看设备故障
				</li>
				<li class="separator"></li>
				<li id="prop">
					属性
				</li>
			</ul>
		</div>

	</body>
	<script type="text/javascript">
	var cmenu = new contextMenu( {
		menuID : "bmenu",
		targetEle : "contextMenu"
	}, {
		bindings : {
			'checkLink' : function(o) {
				b(o)
			},
			'edit' : function(o) {
				alert("编辑 " + o.id);
			},
			'del' : function(o) {
				alert("删除 " + o.id);
			},
			'prop' : function() {
				alert("查看属性");
			}
		}
	});

	cmenu.buildContextMenu();
</script>
</html>