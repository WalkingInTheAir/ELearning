<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/Pagination"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专业管理</title>
</head>
<body>
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active" id="showMajorsTab">
			<a href="#showMajors" role="tab" data-toggle="tab">专业信息</a>
		</li>
		<li role="presentation" id="addMajorTab">
			<a href="#addMajor" role="tab" data-toggle="tab">添加专业</a>
		</li>
		<li role="presentation" id="addMajorsTab">
			<a href="#addMajors" role="tab" data-toggle="tab">批量添加</a>
		</li>
		<li role="presentation">
			<a href="#settings" role="tab" data-toggle="tab">Settings</a>
		</li>
	</ul>

	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="showMajors">
			<c:set var="depts" value="${requestScope.filter }"></c:set>
			<c:set var="majors" value="${requestScope.cata }"></c:set>
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
						<th>班级名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="major" items="${majors.content }"
						varStatus="row">
						<tr class="${row.count%2==0?'':'info' }">
							<td>${row.count }</td>
							<td>${major.dept.name }</td>
							<td>${major.name }</td>
							<td>
								<button type="button" class="btn btn-success btn-xs" data-majorid="${major.id }" data-deptid="${major.dept.id }" onclick="_modifyDept(this)">修改</button>
								<button type="button" class="btn btn-danger  btn-xs" data-majorid="${major.id }" onclick="_deleteDept(this)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p:pagination />
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addMajor">
			<div class="form-group">
				<label for="departName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-10">
					<select class="form-control" style="width:auto;" id="deptName"></select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">专业名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="majorName" name="majorName" placeholder="请设置专业名称" maxlength="15" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addMajorBtn" value="添加">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addMajors">
			<div class="form-group">
				<label for="departName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-10">
					<select class="form-control" style="width:auto;" id="deptName"></select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">请选择文件</label>
				<div class="col-sm-10">
					<input style="margin-top:5px;" type="file" accept=".xls,.xlsx" id="majorNames" name="majorNames" maxlength="15" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addMajorsBtn" value="添加">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="settings">...</div>
	</div>
	<script type="text/javascript" src="page_admin/js/major.js"></script>
</body>
</html>