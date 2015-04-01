<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/Pagination"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程管理</title>
</head>
<body>
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active" id="showCoursesTab">
			<a href="#showCourses" role="tab" data-toggle="tab">课程信息</a>
		</li>
		<li role="presentation" id="addCourseTab">
			<a href="#addCourse" role="tab" data-toggle="tab">添加课程</a>
		</li>
		<li role="presentation" id="addCoursesTab">
			<a href="#addCourses" role="tab" data-toggle="tab">批量添加</a>
		</li>
		<li role="presentation" id="mdfCourseTab" style="display:none">
			<a href="#mdfCourse" role="tab" data-toggle="tab">修改课程信息</a>
		</li>
	</ul>

	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="showCourses">
			
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addCourse">
			<div class="form-group">
				<label for="typeName" class="col-sm-2 control-label">课程类型</label>
				<div class="col-sm-2">
					<select id="typeName" class="form-control"></select>
				</div>
				<label for="deptName" class="col-sm-2 control-label">所属院系</label>
				<div class="col-sm-2">
					<select id="deptName" class="form-control" disabled>
						<option value="-1">--请选择院系--</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">课程名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="courseName" name="courseName" placeholder="请设置课程名称" maxlength="15" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addTypeBtn" value="添加">
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
					<input type="text" class="form-control" id="typeName" name="typeName" placeholder="请设置专业名称" maxlength="15" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="mdfTypeBtn" value="修改">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="page_admin/js/course.js"></script>
</body>
</html>