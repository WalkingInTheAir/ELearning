<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="path" uri="/BasePath"%>
<%@ taglib prefix="page" uri="/PageContent" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administrator Template</title>
<path:basepath/>
<link rel="stylesheet" href='showmsg/ShowMsg.css'>
<link rel="stylesheet" href="plugin/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="template/css/common.css" />
<link rel="stylesheet" href="template/css/header.css" />
<link rel="stylesheet" href="template/css/template.css">
<link rel="stylesheet" href="page_admin/css/template.css">
</head>
<body>
	<!-- header区 -->
	<%-- <header>
		<div id="logoDiv"> </div>
		<h3 id="title1"></h3>	
		<div id="loginInfoDiv">
			<span id="loginInfo"><a><span class="glyphicon glyphicon-user"></span><c:out value="${sessionScope.user.name }"> </c:out></a></span>
			<span id="logout"><a><span class="glyphicon glyphicon glyphicon-off"></span>退出</a></span>
		</div>	
	</header> --%>
<%-- 	<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header" id="logo">
        </div>
        <div class="navbar-collapse collapse" role="navigation">
            <ul class="nav navbar-nav navbar-right hidden-sm">
                <li><a><span class="glyphicon glyphicon-user"></span><c:out value="${sessionScope.user.name }"> </c:out></a></li>
                <li><a><span class="glyphicon glyphicon glyphicon-off"></span>退出</a></li>
            </ul>
        </div>
    </div>
</div>
	<hr id="headerHr"/> --%>
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
		<div class="panel _sidePanel">
			<a href="#menu1" class="collapsed _navTitle" data-toggle="collapse"
				data-parent="#sideMenu">机构管理<span
				class="glyphicon glyphicon-chevron-up"></span></a>
			<ul id="menu1" class="nav _navlist collapse">
				<li><a href="/DepartServlet?method=showPage" target="S" data-include="department.jsp">院系管理</a></li>
				<li><a href="/MajorServlet?method=showPage" target="S" data-include="major.jsp">专业管理</a></li>
				<li><a href="/ClassServlet?method=showPage" target="S" data-include="classes.jsp">班级管理</a></li>
			</ul>
		</div>
		<div class="panel _sidePanel">
			<a href="#menu2" class="collapsed _navTitle" data-toggle="collapse"
				data-parent="#sideMenu">人员管理<span
				class="glyphicon glyphicon-chevron-up"></span></a>
			<ul id="menu2" class="nav _navlist collapse">
				<li><li><a href="/TeacherServlet?method=showPage" target="S">教师管理</a></li>
				<li><a href="/StudentServlet?method=showPage" target="S">学生管理</a></li>

			</ul>
		</div>
		<div class="panel _sidePanel">
			<a href="#menu3" class="collapsed _navTitle" data-toggle="collapse"
				data-parent="#sideMenu">课程管理<span
				class="glyphicon glyphicon-chevron-up"></span></a>
			<ul id="menu3" class="nav _navlist collapse">
				<li><a href="/CourseTypeServlet?method=showPage" target="S">课程类型管理</a></li>
				<li><a href="/CourseServlet?method=showPage" target="S">课程管理</a></li>
				<li><a href="/page_admin/asignTeacher.jsp" target="T">教师分配</a></li>
			</ul>
		</div>
		<div class="panel _sidePanel">
			<a href="#menu4" class="collapsed _navTitle" data-toggle="collapse"
				data-parent="#sideMenu">安全设置<span
				class="glyphicon glyphicon-chevron-up"></span></a>
			<ul id="menu4" class="nav _navlist collapse">
				<li><a href="#">密码管理</a></li>
			</ul>
		</div>
	</div>
	<script type="text/javascript" src="plugin/jquery-2.1.1.js"></script>
	<script type="text/javascript" src="plugin/jquery.form.js"></script>
	<script type="text/javascript" src="plugin/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="page_admin/js/template.js"></script>
	<script type="text/javascript" src="showmsg/ShowMsg.js"></script>
	<!-- 主要内容区 -->
	<div id="_content">
		<form id="MainForm" name="MainForm" class="form-horizontal" role="form">
			<page:includePage />
		</form>
	</div>
</body>
</html>
