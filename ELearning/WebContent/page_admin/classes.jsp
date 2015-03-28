<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/Pagination"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>班级管理</title>
</head>
<body>
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active" id="showClassesTab">
			<a href="#showClasses" role="tab" data-toggle="tab">班级信息</a>
		</li>
		<li role="presentation" id="addClassTab">
			<a href="#addClass" role="tab" data-toggle="tab">添加班级</a>
		</li>
		<li role="presentation" id="addClassesTab">
			<a href="#addClasses" role="tab" data-toggle="tab">批量添加</a>
		</li>
		<li role="presentation" id="modifyClassTab" style="display:none">
			<a href="#modifyClass" role="tab" data-toggle="tab">修改班级信息</a>
		</li>
	</ul>

	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="showClasses">
			<c:set var="depts" value="${requestScope.filter }"></c:set>
			<div class="form-group">
				<label for="deptName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-2">
					<select class="form-control" style="width:auto;" id="deptName">
						<option value="-1">--请选择院系--</option>
						<c:forEach var="dept" items="${depts }">
							<option value="${dept.id }">${dept.name }</option>
						</c:forEach>
					</select>
				</div>
				<label for="majorName" class="col-sm-2 control-label">专业名称</label>
				<div class="col-sm-2">
					<select class="form-control" style="width:auto;" id="majorName">
						<option value="-1">--请选择专业--</option>
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
						<th>专业</th>
						<th>班级名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="clas" items="${requestScope.cata.content }" varStatus="row">
						<tr class="${row.count%2==0?'':'info' }">
							<td>${row.count }</td>
							<td>${clas.major.dept.name }</td>
							<td>${clas.major.name }</td>
							<td>${clas.name }</td>
							<td>
								<button type="button" class="btn btn-success btn-xs"
									data-classid="${clas.id }" 
									data-classname="${clas.name }"
									data-majorid="${clas.major.id }"
									data-majorname="${clas.major.name }"
									data-deptid="${clas.major.dept.id }"
									data-deptname="${clas.major.dept.name }"
									onclick="_modifyClass(this)">修改</button>
								<button type="button" class="btn btn-danger  btn-xs"
									data-classid="${clas.id }" 
									onclick="_deleteClass(this)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p:pagination />
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addClass">
			<div class="form-group">
				<label for="departName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-10">
					<select class="form-control" style="width:auto;" id="deptName"></select>
				</div>
			</div>
			<div class="form-group">
				<label for="majorName" class="col-sm-2 control-label">专业名称</label>
				<div class="col-sm-10">
					<select class="form-control" style="width:auto;" id="majorName">
						<option value="-1">--请选择专业--</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="className" class="col-sm-2 control-label">班级名称</label>
				<div class="col-sm-10">
					<input class="form-control" style="width:auto" id="className" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addClassBtn" value="添加">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addClasses"><p>程序员正在紧张编码中。。。。。。</p></div>
		<div role="tabpanel" class="tab-pane fade" id="modifyClass">
			<div class="form-group">
				<label for="departName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-10">
					<input type="text" id="deptName" class="form-control" disabled/>
				</div>
			</div>
			<div class="form-group">
				<label for="majorName" class="col-sm-2 control-label">专业名称</label>
				<div class="col-sm-10">
					<input type="text" id="majorName" class="form-control" disabled/>
				</div>
			</div>
			<div class="form-group">
				<label for="className" class="col-sm-2 control-label">班级名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" class="form-control" id="className" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="mdfClassBtn" value="修改">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="page_admin/js/class.js"></script>
</body>
</html>