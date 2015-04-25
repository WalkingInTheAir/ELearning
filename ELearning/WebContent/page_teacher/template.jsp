<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="path" uri="/BasePath"%>
<%@ taglib prefix="page" uri="/PageContent" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Teacher Template</title>
<path:basepath/>
<link rel="stylesheet" href='showmsg/ShowMsg.css'>
<link rel="stylesheet" href="plugin/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="template/css/common.css" />
<link rel="stylesheet" href="template/css/header.css" />
<link rel="stylesheet" href="template/css/template.css">
<link rel="stylesheet" href="page_teacher/css/template.css">
</head>
<body>
<%@include file="../template/header.jsp" %>
	<!-- 菜单栏 -->
	<form id="MenuForm" name="MenuForm" action="TrxManager" method="post" class="form-horizontal" role="form">
		<input type="hidden" id="menuIndex" name="menuIndex" />
		<input type="hidden" id="itemIndex" name="itemIndex" />
		<input type="hidden" id="tabIndex" name="tabIndex"/>
		<input type="hidden" id="include" name="include" />
		<input type="hidden" id="trxStatus" name="trxStatus" />
		<input type="hidden" id="target" name="target" />
		<input type="hidden" id="currPage" value="${requestScope.pageInfo.currPage }" />
		<input type="hidden" id="pageSize" value="${requestScope.pageInfo.pageSize }" />
	</form>
	<div id="sideMenu">
		<c:set var="cs" value="${sessionScope.courses }"></c:set>
		<c:forEach var="c" items="${cs }">
			<div class="panel _sidePanel">
				<a href="#menu1" class="collapsed _navTitle" data-toggle="collapse"
					data-parent="#sideMenu">${c.name }<span
					class="glyphicon glyphicon-chevron-up"></span></a>
				<ul id="menu1" class="nav _navlist collapse">
					<li><a href="/TeacherServlet" data-item="courseInfo" target="S">课程信息</a></li>
					<li><a href="/TeacherServlet" data-item="courseware" target="S" data-include="major.jsp">课件管理</a></li>
					<li><a href="/TeacherServlet" data-item="video" target="S" data-include="classes.jsp">视频管理</a></li>
					<li><a href="/TeacherServlet" data-item="task" target="S" data-include="classes.jsp">作业管理</a></li>
					<li><a href="/TeacherServlet" data-item="comunicate" target="S" data-include="classes.jsp">互动交流</a></li>
					<li><a href="/TeacherServlet" data-item="FAQ" target="S" data-include="classes.jsp">FAQ</a></li>
				</ul>
			</div>
		</c:forEach>
	</div>
	<script type="text/javascript" src="plugin/jquery-2.1.1.js"></script>
	<script type="text/javascript" src="plugin/jquery.form.js"></script>
	<script type="text/javascript" src="plugin/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="page_teacher/js/template.js"></script>
	<script type="text/javascript" src="showmsg/ShowMsg.js"></script>
	<!-- 主要内容区 -->
	<div id="_content">
		<form id="MainForm" name="MainForm" class="form-horizontal" role="form">
			<page:includePage />
		</form>
	</div>
</body>
</html>
