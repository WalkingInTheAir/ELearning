<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/Pagination"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生管理</title>
</head>
<body>
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active" id="showStudentTab">
			<a href="#showStudent" role="tab" data-toggle="tab">学生信息</a>
		</li>
		<li role="presentation" id="addStudentTab">
			<a href="#addStudent" role="tab" data-toggle="tab">添加学生</a>
		</li>
		<li role="presentation" id="addStudentsTab">
			<a href="#addStudents" role="tab" data-toggle="tab">批量添加</a>
		</li>
		<li role="presentation" id="mdfStudentTab" style="display:none">
			<a href="#mdfStudent" role="tab" data-toggle="tab">修改学生信息</a>
		</li>
	</ul>

	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="showStudent">
			<c:set var="depts" value="${requestScope.filter }"></c:set>
			<c:set var="stus" value="${requestScope.cata }"></c:set>
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
			<div class="form-group">
				<label for="majorName" class="col-sm-2 control-label">专业名称</label>
				<div class="col-sm-3">
					<select id="majorName" class="form-control">
						<option value="-1">--请选择专业--</option>
					</select>
				</div>
				<label for="deptName" class="col-sm-2 control-label">班级名称</label>
				<div class="col-sm-3">
					<select id="className" class="form-control">
						<option value="-1">--请选择班级--</option>
					</select>
				</div>
			</div>
			<hr />
			<table class="table table-hover data-table"
				style="font-size: 13px; margin-left: 10%; width: 80%;">
				<thead>
					<tr>
						<th>编号</th>
						<th>院系</th>
						<th>专业</th>
						<th>班级</th>
						<th>姓名</th>
						<th>学号</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="s" items="${stus.content }"
						varStatus="row">
						<tr class="${row.count%2==0?'':'info' }">
							<td>${row.count }</td>
							<td>${s.clas.major.dept.name }</td>
							<td>${s.clas.major.name }</td>
							<td>${s.clas.name }</td>
							<td>${s.name }</td>
							<td>${s.num }</td>
							<td>
								<button type="button" class="btn btn-success btn-xs"  onclick="_modifyStudent(this)"
									data-stuid="${s.id }"
									data-stuname="${s.name }"
									data-stunum="${s.num }"
									data-classname="${s.clas.name }"
									data-majorname="${s.clas.major.name }"
									data-deptname="${s.clas.major.dept.name }">修改</button>
								<button type="button" class="btn btn-danger  btn-xs" data-stuid="${s.id }" onclick="_deleteStudent(this)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p:pagination />
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addStudent">
			<div class="form-group">
				<label for="deptName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-3">
					<select id="deptName" class="form-control">
						<option value="-1">--请选择院系--</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="majorName" class="col-sm-2 control-label">专业名称</label>
				<div class="col-sm-3">
					<select id="majorName" class="form-control">
						<option value="-1">--请选择专业--</option>
					</select>
				</div>
				<label for="deptName" class="col-sm-2 control-label">班级名称</label>
				<div class="col-sm-3">
					<select id="className" class="form-control">
						<option value="-1">--请选择班级--</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="studentName" name="studentName" placeholder="请输入学生姓名" maxlength="15" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">学号</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="studentNum" name="studentNum" placeholder="请输入学生学号" maxlength="15" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addStudentBtn" value="添加">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addStudents">
			<p>。。。。</p>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="mdfStudent">
			<div class="form-group">
				<label for="deptName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-2">
					<input id="deptName" name="deptName" class="form-control" disabled/>
				</div>
			</div>
			<div class="form-group">
				<label for="majorName" class="col-sm-2 control-label">专业名称</label>
				<div class="col-sm-2">
					<input id="majorName" name="majorName" class="form-control" disabled/>
				</div>
			</div>
			<div class="form-group">
				<label for="className" class="col-sm-2 control-label">班级名称</label>
				<div class="col-sm-2">
					<input id="className" name="className" class="form-control" disabled/>
				</div>
			</div>
			<div class="form-group">
				<label for="stuNum" class="col-sm-2 control-label">学号</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="stuNum" name="stuNum" disabled>
				</div>
			</div>
			<div class="form-group">
				<label for="stuName" class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="stuName" name="stuName" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="mdfStudentBtn" value="修改">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="page_admin/js/student.js"></script>
</body>
</html>