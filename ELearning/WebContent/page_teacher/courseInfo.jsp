<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript" charset="utf-8" src="plugin/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugin/ueditor/ueditor.all.min.js"> </script>
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active" id="courseDescTab"><a
			href="#courseDescDiv" role="tab" data-toggle="tab">课程简介</a></li>
		<li role="presentation" id="courseOutlineTab"><a href="#courseOutlineDiv"
			role="tab" data-toggle="tab">课程大纲</a></li>
	</ul>
	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="courseDescDiv">
			<div class="form-group">
				<label for="courseDesc" class="control-label">课程简介</label>
			</div>
			<div class="form-group">
				<div class="">
					<script id="courseDesc" type="text/plain" style="width:90%;height:400px;overflow:auto">${requestScope.course.desc}</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="mdfDescBtn" value="修改">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="courseOutlineDiv">
			<div class="form-group">
				<label for="courseOutline" class="control-label">课程大纲</label>
			</div>
			<div class="form-group">
				<div class="">
					<script id="courseOutline" type="text/plain" style="width:90%;height:400px;overflow:auto">${requestScope.course.outline}</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="mdfOutlineBtn" value="修改">
				</div>
			</div>
		</div>
	</div>
	 <script type="text/javascript">
	    //实例化编辑器
	    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    	var descUE = UE.getEditor('courseDesc');
	    var outlineUE = UE.getEditor("courseOutline");
    </script>
    <script type="text/javascript" src="page_teacher/js/courseInfo.js"></script>
</body>
</html>