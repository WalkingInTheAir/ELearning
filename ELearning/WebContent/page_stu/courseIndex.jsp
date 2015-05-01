<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	#courseDesc{
		width:80%;
		margin:auto;
	}
</style>
</head>

<body>
	<div id="courseDesc">${empty requestScope.course.desc ? "老师正忙于备课，未来得及准备，请静候。。。" : requestScope.course.desc}</div>
</body>
</html>