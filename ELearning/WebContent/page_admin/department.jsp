<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>院系管理</title>
</head>
<body>
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation">
			<a href="#addDepart" role="tab" data-toggle="tab">添加院系</a>
		</li>
		<li role="presentation">
			<a href="#addDeparts" role="tab" data-toggle="tab">批量添加</a>
		</li>
		<li role="presentation">
			<a href="#messages" role="tab" data-toggle="tab">Messages</a>
		</li>
		<li role="presentation">
			<a href="#settings" role="tab" data-toggle="tab">Settings</a>
		</li>
	</ul>

	<!-- Tab panes -->
	<div class="tab-content">
		<!-- 添加院系 -->
		<div role="tabpanel" class="tab-pane active" id="addDepart">
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
					<input type="file" accept=".xls" id="departsFile" name="departsFile" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addDepartsBtn" value="添加">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="messages">...</div>
		<div role="tabpanel" class="tab-pane fade" id="settings">...</div>
	</div>
	<script type="text/javascript" src="page_admin/js/department.js"></script>
</body>
</html>