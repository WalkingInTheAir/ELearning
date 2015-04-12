<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link type="text/css" rel="stylesheet" href="template/pagination.css"/>
<c:set var="currPage" value="${requestScope.pageInfo.currPage }"></c:set>
<c:set var="totalPage" value="${requestScope.pageInfo.totalPage }"></c:set>
<nav id="pageBar" style="display:${(totalPage==null || totalPage == 0)? 'none' : 'block'}">
	<ul class="pagination pagination-sm" data-barlength="5">
		<li id="currInTotal"><a >${currPage }/${totalPage }</a></li>
		<li id="indexBar"><a href="#" aria-label="Index" title="首页" data-pageno="0">
				<span class="glyphicon glyphicon-backward" aria-hidden="true"></span>
			</a>
		</li>
		<li  id="preBar"><a href="#" aria-label="Previous" title="上一页" data-pageno="${currPage - 1 }">
				<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
			</a>
		</li>
		<c:choose>
			<c:when test="${currPage > 1 && currPage < totalPage}">
				<li class="availableBar"><a href="#" data-pageno="${currPage - 1 }">${currPage - 1}</a></li>
				<li class="availableBar currPageBar active"><a href="#" data-pageno="${currPage}">${currPage}</a></li>
				<li class="availableBar"><a href="#" data-pageno="${currPage + 1 }">${currPage + 1}</a></li>
			</c:when>
			<c:when test="${currPage == 1}">
				<li class="availableBar currPageBar active"><a href="#" data-pageno="${currPage }">${currPage}</a></li>
				<c:if test="${currPage + 1 <= totalPage }">
					<li class="availableBar" ><a href="#" data-pageno="${currPage + 1 }">${currPage + 1}</a></li>
				</c:if>
				<c:if test="${currPage + 2 <= totalPage }">
					<li class="availableBar"><a href="#" data-pageno="${currPage + 2 }">${currPage + 2}</a></li>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${currPage - 2 > 0 }">
					<li class="availableBar"><a href="#" data-pageno="${currPage - 2 }">${currPage - 2}</a></li>
				</c:if>
				<c:if test="${currPage - 1 > 0 }">
					<li class="availableBar" ><a href="#" data-pageno="${currPage - 1 }">${currPage - 1}</a></li>
				</c:if>
				<li class="availableBar currPageBar active"><a href="#" data-pageno="${currPage }">${currPage}</a></li>
			</c:otherwise>
		</c:choose>
		<li id="nextBar"><a href="#" aria-label="next" title="下一页" data-pageno="${currPage + 1 }">
				<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			</a>
		</li>
		<li id="lastBar"><a href="#" aria-label="Last" title="尾页" data-pageno="${totalPage }">
				<span class="glyphicon glyphicon-forward" aria-hidden="true"></span>
			</a>
		</li>
	</ul>
</nav>
<!-- TODO -->
<script type="text/javascript" src="template/pagination.js"></script>