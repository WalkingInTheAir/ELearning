<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header" id="logo">
        </div>
        <div class="navbar-collapse collapse" role="navigation" id="headerA">
            <ul class="nav navbar-nav navbar-right hidden-sm">
                <li><a><span class="glyphicon glyphicon-user"></span><c:out value="${sessionScope.user.name }"> </c:out></a></li>
                <li><a href="SecServlet?method=logout"><span class="glyphicon glyphicon glyphicon-off"></span>退出</a></li>
            </ul>
        </div>
    </div>
</div>