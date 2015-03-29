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
		<li role="presentation" class="active" id="showTypesTab">
			<a href="#showTypes" role="tab" data-toggle="tab">课程类型信息</a>
		</li>
		<li role="presentation" id="addTypeTab">
			<a href="#addType" role="tab" data-toggle="tab">添加课程类型</a>
		</li>
		<li role="presentation" id="addTypesTab">
			<a href="#addTypes" role="tab" data-toggle="tab">批量添加</a>
		</li>
		<li role="presentation" id="mdfTypeTab" style="display:none">
			<a href="#mdfType" role="tab" data-toggle="tab">修改课程类型信息</a>
		</li>
	</ul>

	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="showTypes">
	
			<c:set var="types" value="${requestScope.cata }"></c:set>
			<table class="table table-hover data-table"
				style="font-size: 13px; margin-left: 10%; width: 80%;">
				<thead>
					<tr>
						<th>编号</th>
						<th>课程类型</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="type" items="${types.content }"
						varStatus="row">
						<tr class="${row.count%2==0?'':'info' }">
							<td>${row.count }</td>
							<td>${type.name }</td>
							<td>
								<button type="button" class="btn btn-success btn-xs" data-typeid="${type.id }" data-typename="${type.name }" onclick="_modifyType(this)">修改</button>
								<button type="button" class="btn btn-danger  btn-xs" data-typeid="${type.id }" onclick="_deleteType(this)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p:pagination />
			
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addType">
			<div class="form-group">
				<label for="typeName" class="col-sm-2 control-label">课程类型名称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="typeName" placeholder="请设置课程类型名称"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addTypeBtn" value="添加">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addTypes">
			<p>。。。。</p>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="mdfType">
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
	<script type="text/javascript" src="page_admin/js/courseType.js"></script>
</body>
</html>