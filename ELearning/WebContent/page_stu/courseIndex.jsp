<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	#courseDesc{
	border:1px solid;
		width:70%;
		margin:40px auto;
	}
</style>
</head>

<body>
	<div id="courseDesc">${requestScope.course.desc }</div>
</body>
</html>