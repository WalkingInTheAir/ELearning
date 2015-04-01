<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/Pagination"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教师管理</title>
</head>
<body>
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active" id="showTeacherTab">
			<a href="#showTeacher" role="tab" data-toggle="tab">教师信息</a>
		</li>
		<li role="presentation" id="addTeacherTab">
			<a href="#addTeacher" role="tab" data-toggle="tab">添加教师</a>
		</li>
		<li role="presentation" id="addTeachersTab">
			<a href="#addTeachers" role="tab" data-toggle="tab">批量添加</a>
		</li>
		<li role="presentation" id="mdfTeacherTab" style="display:none">
			<a href="#mdfTeacher" role="tab" data-toggle="tab">修改教师信息</a>
		</li>
	</ul>

	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="showTeacher">
			<c:set var="depts" value="${requestScope.filter }"></c:set>
			<c:set var="teachers" value="${requestScope.cata }"></c:set>
			<div class="form-group">
				<label for="deptName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-10">
					<select class="form-control" style="width:auto;" id="deptName">
						<option value="-1">--请选择院系--</option>
						<c:forEach var="dept" items="${depts }">
							<option value="${dept.id }">${dept.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<hr />
			<table class="table table-hover data-table"
				style="font-size: 13px; margin-left: 10%; width: 80%;">
				<thead>
					<tr>
						<th>编号</th>
						<th>院系名称</th>
						<th>教师工号</th>
						<th>教师姓名</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="t" items="${teachers.content }"
						varStatus="row">
						<tr class="${row.count%2==0?'':'info' }">
							<td>${row.count }</td>
							<td>${t.dept.name }</td>
							<td>${t.num }</td>
							<td>${t.name }</td>
							<td>
								<button type="button" class="btn btn-success btn-xs" data-teacherid="${t.id }" data-deptid="${t.dept.id }" data-teachername="${t.name }" data-teachernum="${t.num }" onclick="_modifyTeacher(this)">修改</button>
								<button type="button" class="btn btn-danger  btn-xs" data-teacherid="${t.id }" onclick="_deleteTeacher(this)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p:pagination />
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addTeacher">
			<div class="form-group">
				<label class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="teacherName" name="teacherName" placeholder="请输入教师姓名" maxlength="15" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">工号</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="teacherNum" name="teacherNum" placeholder="请输入教师工号" maxlength="15" required>
				</div>
			</div>
			<div class="form-group">
				<label for="deptName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-2">
					<select id="deptName" class="form-control"></select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addTeacherBtn" value="添加">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addTeachers">
			<p>。。。。</p>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="mdfTeacher">
			<div class="form-group">
				<label for="deptName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-2">
					<select id="mdfName" name="mdfName" class="form-control"></select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">工号</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="teacherNum" name="teacherNum" maxlength="15" disabled>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="teacherName" name="teacherName" maxlength="15" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="mdfTeacherBtn" value="修改">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="page_admin/js/teacher.js"></script>
</body>
</html>