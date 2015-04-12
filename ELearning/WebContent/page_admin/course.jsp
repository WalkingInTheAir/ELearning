<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/Pagination"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程管理</title>
<link rel="stylesheet" href="page_admin/css/course.css" />
</head>
<body>
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active" id="showCoursesTab"><a
			href="#showCourses" role="tab" data-toggle="tab">课程信息</a></li>
		<li role="presentation" id="addCourseTab"><a href="#addCourse"
			role="tab" data-toggle="tab">添加课程</a></li>
		<li role="presentation" id="addCoursesTab"><a href="#addCourses"
			role="tab" data-toggle="tab">批量添加</a></li>
		<li role="presentation" id="mdfCourseTab" style="display: none">
			<a href="#mdfCourse" role="tab" data-toggle="tab">修改课程信息</a>
		</li>
	</ul>

	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="showCourses">
			<c:set var="cts" value="${requestScope.filter }"></c:set>
			<div class="form-group">
				<label for="typeName" class="col-sm-2 control-label">课程类型</label>
				<div class="col-sm-2">
					<select id="typeName" class="form-control"
						data-parent="#showCourses">
						<option value="-1">--请选择课程类型--</option>
						<c:forEach var="c" items="${cts}">
							<option value="${c.id }">${c.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-4">
					<div class="input-group">
						<input type="text" class="form-control" placeholder="请输入课程名称"
							id="courseName" name="courseName" /> <span
							class="input-group-btn">
							<button class="btn btn-primary" type="button" id="searchBtn">查找</button>
						</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">院系</label>
				<div class="col-sm-2">
					<select id="deptName" name="deptName" class="form-control"
						data-parent="#showCourses" disabled></select>
				</div>
				<label class="col-sm-1 control-label">专业</label>
				<div class="col-sm-2">
					<select id="majorName" name="majorName" class="form-control"
						data-parent="#showCourses" disabled></select>
				</div>
				<label class="col-sm-1 control-label">班级</label>
				<div class="col-sm-2">
					<select id="className" name="className" class="form-control"
						data-parent="#showCourses" disabled></select>
				</div>
			</div>
			<hr />
			<table class="table table-hover data-table"
				style="font-size: 13px; margin-left: 10%; width: 80%;">
				<thead>
					<tr>
						<th>编号</th>
						<th>课程类型</th>
						<th>课程名称</th>
						<th>所属</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<p:pagination />
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addCourse">
			<div class="form-group">
				<label for="typeName" class="col-sm-2 control-label">课程类型</label>
				<div class="col-sm-2">
					<select id="typeName" class="form-control" data-parent="#addCourse"></select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">院系</label>
				<div class="col-sm-2">
					<select id="deptName" name="deptName" class="form-control"
						data-parent="#addCourse" disabled></select>
				</div>
				<label class="col-sm-1 control-label">专业</label>
				<div class="col-sm-2">
					<select id="majorName" name="majorName" class="form-control"
						data-parent="#addCourse" disabled></select>
				</div>
				<label class="col-sm-1 control-label">班级</label>
				<div class="col-sm-2">
					<select id="className" name="className" class="form-control"
						data-parent="#addCourse" disabled></select>
				</div>
			</div>
			<hr />
			<div class="form-group" style="margin-top: 50px;">
				<label class="col-sm-2 control-label">课程名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="courseName"
						name="courseName" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addCourseBtn"
						value="添加">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addCourses">
			<p>。。。。</p>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="mdfCourse">
			<div class="form-group">
				<label class="col-sm-2 control-label">课程类型名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="typeName"
						name="typeName" disabled>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">所属</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="belong" name="belong"
						disabled>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">课程名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="courseName"
						name="courseName">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="mdfCourseBtn"
						value="修改">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="page_admin/js/course.js"></script>
</body>
</html>