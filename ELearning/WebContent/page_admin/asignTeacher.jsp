<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="p" uri="/Pagination"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教师分配</title>
<link rel="stylesheet" href="page_admin/css/course.css">
</head>
<body>
	<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active" id="asnTeacherTab"><a href="#asnTeacher"
			role="tab" data-toggle="tab">教师指派</a></li>
		<li role="presentation" id="classCourseTab"><a href="#classCourse"
			role="tab" data-toggle="tab">班级课程信息</a></li>
		<li role="presentation" id="mdfTeacherTab" style="display:none"><a href="#mdfTeacher"
			role="tab" data-toggle="tab">修改代课教师</a></li>
	</ul>
	
	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="asnTeacher">
				<div class="form-group">
					<div class="col-md-4" id="chooseCourse">
						<div class="form-group">
							<label class="control-label">选择课程</label>
						</div>
						<div class="form-group">
							<label for="typeName" class="col-sm-3 control-label">类型</label>
							<div class="col-sm-8">
								<select id="typeName" class="form-control"
									data-parent="#chooseCourse"></select>
							</div>
						</div>
						<div class="form-group">
							<label for="deptName" class="col-sm-3 control-label">院系</label>
							<div class="col-sm-8">
								<select id="deptName" name="deptName" class="form-control"
									data-parent="#chooseCourse" disabled></select>
							</div>
						</div>
						<div class="form-group">
							<label for="majorName" class="col-sm-3 control-label">专业</label>
							<div class="col-sm-8">
								<select id="majorName" name="majorName" class="form-control"
									data-parent="#chooseCourse" disabled></select>
							</div>
						</div>
						<div class="form-group">
							<label for="className" class="col-sm-3 control-label">班级</label>
							<div class="col-sm-8">
								<select id="className" name="className" class="form-control"
									data-parent="#chooseCourse" disabled></select>
							</div>
						</div>
						<div class="form-group">
							<div class="candidateDiv" id="courseList">
								<ul class="list-unstyled">
								</ul>
							</div>
						</div>
					</div>
					<div class="col-md-4" id="chooseClass">
						<div class="form-group">
							<label class="control-label">选择班级</label>
						</div>
						<div class="form-group">
							<div class="candidateDiv"  id="classList"  style="margin-left:15%; max-height:300px;">
								<ul class="list-unstyled">
								</ul>
							</div>
						</div>
					</div>
					<div class="col-md-4" id="chooseTeacher">
						<div class="form-group">
							<label class="control-label">选择教师</label>
						</div>
						<div class="form-group">
							<label for="typeName" class="col-sm-3 control-label">院系</label>
							<div class="col-sm-8">
								<select id="deptName" class="form-control"
									data-parent="#asnTeacher"></select>
							</div>
						</div>
						<div class="form-group">
							<div class="candidateDiv" id="teacherList">
								<ul class="list-unstyled" >
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-11 control-label"></label>
					<div class="col-sm-1">
						<input type="button" value="确定" class="btn btn-info" id="asnTeacherBtn"/>
					</div>
				</div>
			</div>
			<div role="tabpanel" class="tab-pane fade" id="classCourse">
				<div class="form-group">
					<label class="col-sm-2 control-label">院系</label>
					<div class="col-sm-2">
						<select id="deptName" name="deptName" class="form-control"
							data-parent="#classCourse"></select>
					</div>
					<label class="col-sm-1 control-label">专业</label>
					<div class="col-sm-2">
						<select id="majorName" name="majorName" class="form-control"
							data-parent="#classCourse">
							<option value="-1">--请选择专业--</option>	
						</select>
					</div>
					<label class="col-sm-1 control-label">班级</label>
					<div class="col-sm-2">
						<select id="className" name="className" class="form-control"
							data-parent="#classCourse">
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
							<th>课程名称</th>
							<th>教师</th>
							<th>工号</th>
							<th>部门</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<p:pagination />
			</div>
			<div role="tabpanel" class="tab-pane fade" id="mdfTeacher">
				<div class="col-md-5" id="courseInfo">
					<div class="form-group">
						<label class="col-sm-3 control-label">课程名称</label>
						<div class="col-sm-8">
							<input type="text" id="courseName" class="form-control" disabled/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">教师工号</label>
						<div class="col-sm-8">
							<input type="text" id="teacherNum" class="form-control" disabled/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">教师名称</label>
						<div class="col-sm-8">
							<input type="text" id="teacherName" class="form-control" disabled/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">所在部门</label>
						<div class="col-sm-8">
							<input type="text" id="deptNameInfo" class="form-control" disabled/>
						</div>
					</div>
				</div>
				<div class="col-md-5" id="changeTeacher">
					<div class="form-group">
						<label class="col-sm-3 control-label">院系</label>
						<div class="col-sm-8">
							<select id="deptName" class="form-control"></select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">教师</label>
						<div class="col-sm-8">
							<select id="teacherName" class="form-control"></select>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-9 control-label"></label>
					<div class="col-sm-1">
						<input type="button" value="修改" class="btn btn-primary" id="changeTeacherBtn"/>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="page_admin/js/course.js"></script>
		<script type="text/javascript" src="page_admin/js/asignTeacher.js"></script>
</body>
</html>