$(function(){
	$("#asnTeacherTab").trigger("click");
	
	//隐藏修改院系tab
	$(".nav li").click(function(){
		if($(this).attr("id") != "mdfTeacherTab"){
			$("#mdfTeacherTab").css("display", "none");
		}
	});
	
	$("#classCourseTab, #mdfTeacherTab").click(function(event, params) {
		var target = $(this).find("a:first");
		target.tab("show");
		var $form = $("#MainForm");
		var deptSelect = $(target.attr("href")).find("#deptName");
		var options = buildAjaxOptions('DepartServlet?method=showPage', {
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form){
			var content = resText.content;
			deptSelect.empty();
			deptSelect.append("<option value=\"-1\">--请选择院系--</option>");
			$.each(content, function(index, c){
				var option = $("<option></option>");
				option.val(c.id).text(c.name);
				deptSelect.append(option);
			});
		});
		if(params && params.options){
			$.extend(true, options, params.options);
		}
		$form.ajaxSubmit(options);
	});
	
	//院系下拉框
	$("#classCourse #deptName").change(function(){
		var deptId = $(this).val();
		var $select = $(this).parent().parent().find("#majorName");
		if(deptId == -1){
			$select.empty();
			$select.append("<option value=\"-1\">--请选择专业--</option>");
			return;
		}
		var $form = $("#MainForm");
		var options = buildAjaxOptions('MajorServlet?method=showPage', {
			ajaxPost : 'T',
			deptId : deptId
		}, function(resText, statusText, xhr, $form){
			var majors = resText.content;
			$select.empty();
			$select.append("<option value=\"-1\">--请选择专业--</option>")
			if(majors.length == 0){
				alertMsg("该院系下还没有设置专业");
			}
			$.each(majors, function(index, c){
				var $option = $("<option></option>");
				$option.val(c.id).text(c.name);
				$select.append($option);
			});
		});
		$form.ajaxSubmit(options);
	});
	
	$("#classCourse #majorName").change(function(){
		var majorId = $(this).val();
		var deptId = $(this).parent().parent().find("#deptName").val();
		var $select = $(this).parent().parent().find("#className");
		if(majorId == -1){
			$select.empty();
			$select.append("<option value=\"-1\">--请选择班级--</option>")
			return;
		}
		var $form = $("#MainForm");
		var options = buildAjaxOptions('ClassServlet?method=showPage', {
			ajaxPost : 'T',
			majorId : majorId
		}, function(resText, statusText, xhr, $form){
			var classes = resText.content;
			$select.empty();
			$select.append("<option value=\"-1\">--请选择班级--</option>");
			if(classes.length == 0){
				alertMsg("该院系下还没有设置专业");
			}
			$.each(classes, function(index, c){
				var $option = $("<option></option>");
				$option.val(c.id).text(c.name);
				$select.append($option);
			});
		});
		$form.ajaxSubmit(options);
	});
	
	$("#classCourse #className").change(function(){
		var classId = $(this).val();
		if(classId == -1){
			return;
		}
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseServlet?method=classCourse', {
			currPage : $("#currPage").val(),
			ajaxPost : 'T',
			classId : classId
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	});
	
	
	$("#changeTeacher #deptName").change(function(){
		var $select = $("#changeTeacher #teacherName");
		var $form = $("#MainForm");
		var options = buildAjaxOptions('TeacherServlet?method=showPage', {
			deptId : $(this).val(),
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form){
			var teachers = resText.content;
			$select.empty();
			$select.append("<option value=\"-1\">--请选择教师--</option>");
			$.each(teachers, function(index, c){
				var $option = $("<option></option>");
				$option.val(c.id).text(c.name);
				$select.append($option);
			});
		});
		$form.ajaxSubmit(options);
	});
	
	$("#changeTeacherBtn").click(function(){
		var teacherId = $("#changeTeacher #teacherName").val() ? parseInt($("#changeTeacher #teacherName").val()) : -1;
		if(teacherId < 1){
			alertMsg("请选择教师", 'W');
			return;
		}
		var origTeacherId = parseInt($("#courseInfo #teacherName").data("teacherid"));
		if(teacherId == origTeacherId){
			alertMsg("与原教师相同，请修改", 'W');
			return;
		}
		var courseId = $("#courseInfo #courseName").data("courseid");
		var classId = $("#courseInfo #courseName").data("classid");
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseServlet?method=changeTeacher', {
			currPage : $("#currPage").val(),
			ajaxPost : 'T',
			classId : classId,
			courseId : courseId,
			teacherId : teacherId
		}, function(resText, statusText, xhr, $form) {
			if (resText.type == 'S') {
				$("#classCourseTab").trigger("click");
				var origClassId = $("#classCourse #className").val();
				$("#classCourse #className").trigger("change");
			}
			ShowMsg(resText);
		});
		$form.ajaxSubmit(options);
	});
});

//翻页
function pageBarOnClick(pageNo) {
	var classId = $("#classCourse #className").val();
	if(classId == -1){
		return;
	}
	var $form = $("#MainForm");
	var options = buildAjaxOptions('CourseServlet?method=classCourse', {
		currPage : pageNo,
		ajaxPost : 'T',
		classId : classId
	}, onNextPageSuccess);
	$form.ajaxSubmit(options);
}

function reBuildPageContent(currPage, pageSize, content){
	var tbody = $("#classCourse table tbody");
	 //清空内容
	 tbody.empty();
	 $.each(content, function(index, c){
		 var tr = $("<tr></tr>");
		 var trClass = index % 2 == 0? "info" : "";
		 tr.attr("class", trClass);
		 var td1 = $("<td>" + ((currPage-1)*pageSize+index+1) + "</td>");
		 var td2 = $("<td>" + c.name + "</td>");
		 var td3 = $("<td>" + c.teacher.name + "</td>");
		 var td4 = $("<td>" + c.teacher.num + "</td>");
		 var td5 = $("<td>" + c.teacher.dept.name + "</td>");
		 var td6 = $("<td></td>");
		 var modBtn = $("<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"_modifyCourse(this)\">修改</button>");
		 modBtn.data("classid", c.clas.id).data("courseid", c.id).data("coursename", c.name).data("teacherid", c.teacher.id)
		 		.data("teachername", c.teacher.name).data("teachernum", c.teacher.num)
		 		.data("deptid", c.teacher.dept.id).data("deptname", c.teacher.dept.name);
		 td6.append(modBtn);
		 tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
		 tbody.append(tr);
	 });
}

function _modifyCourse(obj){
	$("#mdfTeacherTab").css("display", "block");
	$("#mdfTeacherTab a").tab("show");
	//取值
	var classId = $(obj).data("classid");
	var courseId = $(obj).data("courseid");
	var courseName = $(obj).data("coursename");
	var teacherId = $(obj).data("teacherid");
	var teacherName = $(obj).data("teachername");
	var teacherNum = $(obj).data("teachernum");
	var deptId = $(obj).data("deptid");
	var deptName = $(obj).data("deptname");
	//赋值
	
	$("#courseInfo #courseName").val(courseName).data("courseid", courseId).data("classid", classId);
	$("#courseInfo #teacherNum").val(teacherNum);
	$("#courseInfo #teacherName").val(teacherName).data("teacherid", teacherId);
	$("#courseInfo #deptNameInfo").val(deptName).data("deptid", deptId);
	
	$("#mdfTeacherTab").trigger("click", {options:{async: false}});
}