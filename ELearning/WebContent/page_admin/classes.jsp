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
		<li role="presentation" class="active">
			<a href="#addClass" role="tab" data-toggle="tab">班级信息</a>
		</li>
		<li role="presentation">
			<a href="#addClasses" role="tab" data-toggle="tab">批量添加</a>
		</li>
		<li role="presentation">
			<a href="#messages" role="tab" data-toggle="tab">Messages</a>
		</li>
		<li role="presentation">
			<a href="#settings" role="tab" data-toggle="tab">Settings</a>
		</li>
	</ul>

	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="addClass">
			<div class="form-group">
				<label for="deptName1" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-10">
					<select class="form-control" style="width:auto;" id="deptName1">
						<option class="form-control">计算机与软件学院</option>
						<option>经济管理学院</option>
						<option>能源与电气学院</option>
						<option>艺术与设计学院</option>
						<option>计算机与软件学院</option>
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
					<c:forEach var="dept" items="${requestScope.cata.content }"
						varStatus="row">
						<tr class="${row.count%2==0?'':'info' }">
							<td>${row.count }</td>
							<td>${dept.name }</td>
							<td>
								<button type="button" class="btn btn-success btn-xs"
									data-deptid="${dept.id }" data-deptname="${dept.name }"
									onclick="_modifyDept(this)">修改</button>
								<button type="button" class="btn btn-danger  btn-xs"
									data-deptid="${dept.id }" onclick="_deleteDept(this)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p:pagination />
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addClasses">
			<div class="form-group">
				<label for="departName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-10">
					<select class="form-control" style="width:auto;" id="deptName1">
						<option class="form-control">计算机与软件学院</option>
						<option>经济管理学院</option>
						<option>能源与电气学院</option>
						<option>艺术与设计学院</option>
						<option>计算机与软件学院</option>
					</select>
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="messages">...</div>
		<div role="tabpanel" class="tab-pane fade" id="settings">...</div>
	</div>
</body>
</html>