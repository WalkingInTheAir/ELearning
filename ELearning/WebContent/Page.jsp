<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="plugin/bootstrap/css/bootstrap.css">
</head>
<body>
<%
	request.setAttribute("currPage", 4);
	request.setAttribute("totalPage", 4);
%>	
	
	<nav id="pageBar">
		<c:set var="currPage" value="${requestScope.currPage }"></c:set>
		<c:set var="totalPage" value="${requestScope.totalPage }"></c:set>
		<ul class="pagination pagination-sm">
			<li><a>${currPage }/${totalPage }</a></li>
			<li><a href="#" aria-label="Index" title="首页" data-pageNo="0">
					<span class="glyphicon glyphicon-backward" aria-hidden="true"></span>
				</a>
			</li>
			<li><a href="#" aria-label="Previous" title="上一页" data-pageNo="${currPage - 1 }">
					<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				</a>
			</li>
			<c:choose>
				<c:when test="${currPage > 1 && currPage < totalPage}">
					<li><a href="#" data-pageNo="${currPage - 1 }">${currPage - 1}</a></li>
					<li class="active"><a href="#" data-pageNo="${currPage}">${currPage}</a></li>
					<li><a href="#" data-pageNo="${currPage + 1 }">${currPage + 1}</a></li>
				</c:when>
				<c:when test="${currPage == 1}">
					<li class="active"><a href="#" data-pageNo="${currPage }">${currPage}</a></li>
					<c:if test="${currPage + 1 <= totalPage }">
						<li><a href="#" data-pageNo="${currPage + 1 }">${currPage + 1}</a></li>
					</c:if>
					<c:if test="${currPage + 2 <= totalPage }">
						<li><a href="#" data-pageNo="${currPage + 2 }">${currPage + 2}</a></li>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${currPage - 2 > 0 }">
						<li><a href="#" data-pageNo="${currPage - 2 }">${currPage - 2}</a></li>
					</c:if>
					<c:if test="${currPage - 1 > 0 }">
						<li><a href="#" data-pageNo="${currPage - 1 }">${currPage - 1}</a></li>
					</c:if>
					<li class="active"><a href="#" data-pageNo="${currPage }">${currPage}</a></li>
				</c:otherwise>
			</c:choose>
			<li><a href="#" aria-label="next" title="下一页" data-pageNo="${currPage + 1 }">
					<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				</a>
			</li>
			<li><a href="#" aria-label="Last" title="尾页" data-pageNo="${totalPage }">
					<span class="glyphicon glyphicon-forward" aria-hidden="true"></span>
				</a>
			</li>
		</ul>
	</nav>
</body>
</html>