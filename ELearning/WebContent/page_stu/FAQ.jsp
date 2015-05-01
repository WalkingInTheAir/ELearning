<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="path" uri="/BasePath"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<path:basepath/>
<link rel="stylesheet" href="page_stu/css/FAQ.css">
</head>
<body>
<div id="faqContainer">
	<c:if test="${empty requestScope.cata.content}">
		老师正忙于备课，未来得及准备，请静候。。。
	</c:if>
	<c:forEach var="faq" items="${requestScope.cata.content }">
	    <div class="faq">
	        <div class="viewAnswer"><span>查看答案</span></div>
	        <div class="faqNameDiv">${faq.name }</div>
	        <div class="faqAnswerDiv">${faq.answer }</div>
	    </div>
    </c:forEach>
</div>
<script type="text/javascript" src="page_stu/js/FAQ.js"></script>
</body>
</html>