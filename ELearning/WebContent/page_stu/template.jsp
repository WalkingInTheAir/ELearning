<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="path" uri="/BasePath"%>
<%@ taglib prefix="page" uri="/PageContent" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<path:basepath/>
<link rel="stylesheet" href='showmsg/ShowMsg.css'>
<link rel="stylesheet" href="plugin/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="template/css/common.css" />
<link rel="stylesheet" href="template/css/header.css" />
<link rel="stylesheet" href="template/css/template.css">
<link rel="stylesheet" href="page_stu/css/template.css">
</head>
<body>
<%@include file="../template/header.jsp" %>
<form id="MenuForm" name="MenuForm" action="TrxManager" method="post" class="form-horizontal" role="form">
	<input type="hidden" id="menuIndex" name="menuIndex" />
	<input type="hidden" id="tabIndex" name="tabIndex"/>
	<input type="hidden" id="include" name="include" />
	<input type="hidden" id="target" name="target" />
	<input type="hidden" id="item" name="item" />
	<input type="hidden" id="method" name="method" value="coursePage"/>
	<input type="hidden" id="method" name="courseId" value="${requestScope.course.id }"/>
	<input type="hidden" id="currPage" value="${requestScope.pageInfo.currPage }" />
	<input type="hidden" id="pageSize" value="${requestScope.pageInfo.pageSize }" />
	</form>
<div id="sideMenu" style="float: left; width: 20%;">
	<h2 class="courseName">${requestScope.course.name }</h2>
    <div class="panel">
        <ul class="nav _navlist">
            <li><a href="/StudentServlet" data-item="desc" target="S">课程描述<span class="glyphicon glyphicon-chevron-right"></span></a></li>
            <li><a href="/StudentServlet" data-item="outline" target="S">课程大纲<span class="glyphicon glyphicon-chevron-right"></span></a></li>
            <li><a href="/StudentServlet" data-item="courseware" target="S">课件<span class="glyphicon glyphicon-chevron-right"></span></a></li>
            <li><a href="/StudentServlet" data-item="video" target="S">教学视频<span class="glyphicon glyphicon-chevron-right"></span></a></li>
            <li><a href="/StudentServlet" data-item="task" target="S">作业<span class="glyphicon glyphicon-chevron-right"></span></a></li>
            <li><a href="/StudentServlet" data-item="communicate" target="S">交流区<span class="glyphicon glyphicon-chevron-right"></span></a></li>
            <li><a href="/StudentServlet" data-item="FAQ" target="S">FAQ<span class="glyphicon glyphicon-chevron-right"></span></a></li>
        </ul>
    </div>
</div>
<script type="text/javascript" src="plugin/jquery-2.1.1.js"></script>
<script type="text/javascript" src="plugin/jquery.form.js"></script>
<script type="text/javascript" src="plugin/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="page_stu/js/template.js"></script>
<script type="text/javascript" src="showmsg/ShowMsg.js"></script>
<!-- 主要内容区 -->
<div id="_content">
	<form id="MainForm" name="MainForm" class="form-horizontal" role="form">
		<page:includePage />
	</form>
</div>
</body>
</html>