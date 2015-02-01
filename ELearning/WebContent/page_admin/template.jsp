<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="path" uri="/BasePath"%>
<%@ taglib prefix="page" uri="/PageContent" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Administrator Template</title>
<path:basepath/>
<link rel="stylesheet" href="plugin/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="page_admin/css/template.css">
<link rel="stylesheet" href='showmsg/ShowMsg.css'>
</head>
<body>
	<!-- header区 -->
	<header>
		<h1>Administrator 后台管理模板</h1>
	</header>
	<hr />
	<!-- 菜单栏 -->
	<form id="MenuForm" name="MenuForm" action="TrxManager" method="post" class="form-horizontal" role="form">
		<input type="hidden" id="menuIndex" name="menuIndex" />
		<input type="hidden" id="itemIndex" name="itemIndex" />
		<input type="hidden" id="tabIndex" name="tabIndex"/>
		<input type="hidden" id="include" name="include" />
		<input type="hidden" id="trxStatus" name="trxStatus" />
		<input type="hidden" id="target" name="target" />
	</form>
	<div id="sideMenu" style="float: left; width: 20%;">
		<div class="panel _sidePanel">
			<a href="#menu1" class="collapsed _navTitle" data-toggle="collapse"
				data-parent="#sideMenu">机构管理<span
				class="glyphicon glyphicon-chevron-up"></span></a>
			<ul id="menu1" class="nav _navlist collapse">
				<li><a href="department.jsp" target="T">院系管理</a></li>
				<li><a href="classes.jsp" target="P">班级管理</a></li>
			</ul>
		</div>
		<div class="panel _sidePanel">
			<a href="#menu2" class="collapsed _navTitle" data-toggle="collapse"
				data-parent="#sideMenu">用户管理<span
				class="glyphicon glyphicon-chevron-up"></span></a>
			<ul id="menu2" class="nav _navlist collapse">
				<li><a href="">增加用户</a></li>
				<li><a href="modifyEmployee.jsp">修改用户</a></li>
				<li><a href="">删除用户</a></li>
				<li><a href="">用户列表</a></li>

			</ul>
		</div>
		<div class="panel _sidePanel">
			<a href="#menu3" class="collapsed _navTitle" data-toggle="collapse"
				data-parent="#sideMenu">用户管理<span
				class="glyphicon glyphicon-chevron-up"></span></a>
			<ul id="menu3" class="nav _navlist collapse">
				<li><a href="#">增加用户</a></li>
				<li><a href="#">修改用户</a></li>
				<li><a href="#">删除用户</a></li>
				<li><a href="#">用户列表</a></li>

			</ul>
		</div>
		<div class="panel _sidePanel">
			<a href="#menu4" class="collapsed _navTitle" data-toggle="collapse"
				data-parent="#sideMenu">用户管理<span
				class="glyphicon glyphicon-chevron-up"></span></a>
			<ul id="menu4" class="nav _navlist collapse">
				<li><a href="#">增加用户</a></li>
				<li><a href="#">修改用户</a></li>
				<li><a href="#">删除用户</a></li>
				<li><a href="#">用户列表</a></li>
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
