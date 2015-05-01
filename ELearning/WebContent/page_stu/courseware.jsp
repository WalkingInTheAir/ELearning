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
</head>
<body>
<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active" id="showCourseWareTab">
			<a href="#showCourseWare" role="tab" data-toggle="tab">课件信息</a>
		</li>
	</ul>

	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="showCourseWare">
			<table class="table table-hover data-table"
				style="font-size: 13px; margin-left: 10%; width: 80%;">
				<thead>
					<tr>
						<th>编号</th>
						<th>课件名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="cw" items="${requestScope.cata.content }" varStatus="row">
						<tr class="${row.count%2==0?'':'info' }">
							<td>${row.count }</td>
							<td>${cw.origName }</td>
							<td>
								<button type="button" class="btn btn-success btn-xs"
									data-id="${cw.id }"
									onclick="_download(this)">下载</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p:pagination />
		</div>
	</div>
	<script type="text/javascript" src="page_stu/js/courseware.js"></script>
</body>
</html>