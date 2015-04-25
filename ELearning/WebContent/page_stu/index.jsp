<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="path" uri="/BasePath"%>
<%@ taglib prefix="page" uri="/PageContent" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="plugin/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="page_stu/css/index.css">
    <link rel="stylesheet" href="template/css/header.css">
</head>
<body>
<%@include file="../template/header.jsp" %>
<div class="jumbotron">
    <div class="container">
        <h1>E-Learning</h1>
        <h2>简洁、高效的学习平台，让学习无处不在</h2>
    </div>
</div>
<div class="container">
    <div class="row">
    <c:forEach var="course" items="${requestScope.courses }" varStatus="index">
        <div class="col-sm-4">
            <div class="thumbnail">
                <a href="TrxManager?courseId=${course.id}&target=T&method=coursePage" title="" target="_blank">
                    <h2 class="class-title class-title-${index.index%4 + 1}">${course.name }</h2>
                </a>
            </div>
        </div>
     </c:forEach>
</div>
</div>

<footer class="footer ">
    <div class="container">
        <hr/>
        <div class="row footer-bottom">
            <ul class="list-inline text-center">
                <li><a target="_blank">E-Learning网络教学平台</a></li>
            </ul>
        </div>
    </div>
</footer>
</body>
</html>