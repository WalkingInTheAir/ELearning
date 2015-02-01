<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>班级管理</title>
</head>
<body>
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active">
			<a href="#addClass" role="tab" data-toggle="tab">班级院系</a>
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
			添加班级
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addClasses">
			批量添加
		</div>
		<div role="tabpanel" class="tab-pane fade" id="messages">...</div>
		<div role="tabpanel" class="tab-pane fade" id="settings">...</div>
	</div>
</body>
</html>