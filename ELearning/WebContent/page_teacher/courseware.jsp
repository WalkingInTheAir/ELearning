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
		<li role="presentation" id="addCourseWareTab">
			<a href="#addCourseWare" role="tab" data-toggle="tab">上传课件</a>
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
								<button type="button" class="btn btn-danger  btn-xs"
									data-id="${cw.id }"
									onclick="_delete(this)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p:pagination />
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addCourseWare">
			<div class="form-group">
				<label for="courseware" class="col-sm-2 control-label">请选择课件</label>
				<div class="col-sm-10">
					<input style="margin-top:5px;" type="file" accept=".ppt,.pptx" id="courseware" name="courseware" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addCourseWareBtn" value="上传">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="page_teacher/js/courseware.js"></script>
</body>
</html>