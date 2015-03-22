<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="p" uri="/Pagination"%>
<%@taglib prefix="path" uri="/BasePath" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>院系管理</title>
</head>
<body>
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" id="showDepartsTab">
			<a href="#showDeparts" role="tab" data-toggle="tab">院系信息</a>
		</li>
		<li role="presentation">
			<a href="#addDepart" role="tab" data-toggle="tab">添加院系</a>
		</li>
		<li role="presentation">
			<a href="#addDeparts" role="tab" data-toggle="tab">批量添加</a>
		</li>
		<li role="presentation" id="mdfDepartTab" style="display:none">
			<a href="#modifyDepart" role="tab" data-toggle="tab">修改院系</a>
		</li>
	</ul>

	<!-- Tab panes -->
	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="showDeparts">
			<table class="table table-hover data-table" style="font-size:13px; margin-left:10%;width:80%;">
				<thead>
					<tr>
						<th>编号</th>
						<th>院系名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="dept" items="${requestScope.cata.content }" varStatus="row">
						<tr class="${row.count%2==0?'':'info' }">
							<td>${row.count }</td>
							<td>${dept.name }</td>
							<td>
								<button type="button" class="btn btn-success btn-xs" data-deptid="${dept.id }" data-deptname="${dept.name }" onclick="_modifyDept(this)">修改</button>
								<button type="button" class="btn btn-danger  btn-xs" data-deptid="${dept.id }" onclick="_deleteDept(this)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p:pagination/>
		</div>
		<!-- 添加院系 -->
		<div role="tabpanel" class="tab-pane fade" id="addDepart">
			<div class="form-group">
				<label for="departName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="departName" name="departName"
						placeholder="请设置院系名称" maxlength="15" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addDepartBtn" value="添加">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addDeparts">
			<div class="form-group">
				<label for="departName" class="col-sm-2 control-label">请选择文件</label>
				<div class="col-sm-10">
					<input style="margin-top:5px;" type="file" accept=".xls,.xlsx" id="departsFile" name="departsFile" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addDepartsBtn" value="添加">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="modifyDepart">
			<div class="form-group">
				<label for="departName" class="col-sm-2 control-label">院系名称</label>
				<div class="col-sm-10">
					<input type="hidden" name="mdf_departId" id="mdf_departId" />
					<input type="text" class="form-control" id="mdf_departName" name="mdf_departName" maxlength="15" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="mdfDepartBtn" value="修改">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="page_admin/js/department.js"></script>
</body>
</html>