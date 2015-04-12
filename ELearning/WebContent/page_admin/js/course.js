$(function(){
	//隐藏修改院系tab
	$(".nav li").click(function(){
		if($(this).attr("id") != "mdfCourseTab"){
			$("#mdfCourseTab").css("display", "none");
		}
	});
	$("#addCourseTab, #showCoursesTab, #asnTeacherTab").click(function(){
		$(this).find("a:first").tab("show");
		var $form = $("#MainForm");
		var typeSelect = $($(this).find("a").attr("href")).find("#typeName");
		var options = buildAjaxOptions('CourseTypeServlet?method=showPage', {
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form){
			var content = resText.content;
			typeSelect.empty();
			typeSelect.append("<option value=\"-1\">--请选择类型--</option>")
			$.each(content, function(index, c){
				var option = $("<option></option>");
				option.val(c.id).text(c.name);
				typeSelect.append(option);
			});
		});
		$form.ajaxSubmit(options);
		
		if($(this).attr("id") == "asnTeacherTab"){
			//$("#chooseCourse #typeName").trigger("change");
			var deptClass = $("#chooseClass #deptName");
			var deptTeacher = $("#chooseTeacher #deptName");
			var options2 = buildAjaxOptions('DepartServlet?method=showPage', {
				ajaxPost : 'T'
			}, function(resText, statusText, xhr, $form){
				var depts = resText.content;
				deptClass.empty();
				deptClass.append("<option value='-1'>--请选择院系--</option>");
				deptTeacher.empty();
				deptTeacher.append("<option value='-1'>--请选择院系--</option>");
				$.each(depts, function(index, c){
					var $option1 = $("<option></option>");
					var $option2 = $("<option></option>");
					$option1.val(c.id).text(c.name);
					$option2.val(c.id).text(c.name);
					deptTeacher.append($option1);
					deptClass.append($option2);
					
				});
			});
			
			$form.ajaxSubmit(options2);
		}
		
	});
	
	
	$("#addCourse #typeName, #showCourses #typeName, #chooseCourse #typeName").change(function(){
		var typeId = parseInt($(this).val());
		var parentId = $(this).data("parent");
		var deptNameObj = $($(this).data("parent")).find("#deptName");
		var majorNameObj = $($(this).data("parent")).find("#majorName");
		var classNameObj = $($(this).data("parent")).find("#className");
		var target = $(parentId).find("#deptName");
		var $form = $("#MainForm");
		if(parentId == "#chooseCourse"){
			$("#chooseCourse #courseList ul:first").empty();
			$("#chooseClass #classList ul:first").empty();
		}
		deptNameObj.empty().attr("disabled", "true");
		majorNameObj.empty().attr("disabled", "true");
		classNameObj.empty().attr("disabled", "true");
		if (typeId == -1) {
			return;
		} else if (typeId == 1) {
			if (parentId == "#chooseCourse") {
				chooseCourse();
			}
		}else{
			var options = buildAjaxOptions('DepartServlet?method=showPage', {
				ajaxPost : 'T'
			}, function(resText, statusText, xhr, $form){
				var content = resText.content;
				target.empty();
				target.append("<option value=\"-1\">--请选择院系--</option>")
				$.each(content, function(index, c){
					var option = $("<option></option>");
					option.val(c.id).text(c.name);
					target.append(option);
				});
				target.removeAttr("disabled");
			});
			$form.ajaxSubmit(options);
		}
		
	});
	
	$("#addCourse #deptName, #showCourses #deptName, #chooseCourse #deptName").change(function(){
		var deptId = $(this).val();
		if(deptId <= 0){
			alertMsg("请选择院系", 'W');
			return;
		}
		var parentId = $(this).data("parent");
		var typeId = parseInt($(parentId).find("#typeName").val());
		if(typeId > 2){
			var target = $($(this).data("parent")).find("#majorName");
			var $form = $("#MainForm");
			var options = buildAjaxOptions('MajorServlet?method=showPage', {
				deptId : deptId,
				ajaxPost : 'T'
			}, function(resText, statusText, xhr, $form){
				var content = resText.content;
				target.empty();
				target.append("<option value=\"-1\">--请选择专业--</option>")
				$.each(content, function(index, c){
					var option = $("<option></option>");
					option.val(c.id).text(c.name);
					target.append(option);
				});
				target.removeAttr("disabled");
			});
			$form.ajaxSubmit(options);
		}else{
			if(parentId == "#chooseCourse"){
				chooseCourse();
			}
		}
	});
	
	$("#addCourse #majorName, #showCourses #majorName, #chooseCourse #majorName").change(function(){
		var majorId = $(this).val();
		if(majorId <= 0){
			alertMsg("请选择专业", 'W');
			return;
		}
		var parentId = $(this).data("parent");
		var typeId = parseInt($(parentId).find("#typeName").val());
		if(typeId == 4){
			var target = $($(this).data("parent")).find("#className");
			var $form = $("#MainForm");
			var options = buildAjaxOptions('ClassServlet?method=showPage', {
				majorId : majorId,
				ajaxPost : 'T'
			}, function(resText, statusText, xhr, $form){
				var content = resText.content;
				target.empty();
				target.append("<option value=\"-1\">--请选择班级-</option>")
				$.each(content, function(index, c){
					var option = $("<option></option>");
					option.val(c.id).text(c.name);
					target.append(option);
				});
				target.removeAttr("disabled");
			});
			$form.ajaxSubmit(options);
		}else if(typeId == 3){
			if(parentId == "#chooseCourse"){
				chooseCourse();
			}
		}
	});
	
	$("#chooseCourse #className").change(function(){
		var value = $(this).val();
		if(value > -1){
			chooseCourse();
		}
	})
	$("#addCourse #addCourseBtn").click(function(){
		var typeId = $("#addCourse #typeName").val();
		if(typeId < 1){
			alertMsg("请选择课程类型", 'W');
			return;
		}
		var courseName = $("#addCourse #courseName").val();
		if(null == courseName || courseName.trim().length == 0){
			alertMsg("课程名称不能为空", 'W');
			return;
		}
		
		var deptId = $("#addCourse #deptName").val();
		var majorId = $("#addCourse #majorName").val();
		var classId = $("#addCourse #className").val();
		var relativeId;
		switch(parseInt(typeId)){
			case 1: relativeId = null;break;
			case 2: relativeId = deptId;break;
			case 3: relativeId = majorId;break;
			case 4: relativeId = classId;break;
			default:relativeId = null;break;
		}
		
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseServlet?method=addCourse', {
			ajaxPost : 'T',
			courseName : courseName,
			typeId : typeId,
			relativeId : relativeId
		},function(resText, statusText, xhr, $form){
			if(resText.type=='S'){
				$form.resetForm();
				$("#addCourse #typeName").val(-1);
				$("#addCourse #typeName").trigger("change");
			}
			ShowMsg(resText);
		});
		$form.ajaxSubmit(options);
	});
	
	
	$("#searchBtn").click(function(){
		var typeId = $("#showCourses #typeName").val();
		var courseName = $("#showCourses #courseName").val();
		var deptId = $("#showCourses #deptName").val();
		var majorId = $("#showCourses #majorName").val();
		var classId = $("#showCourses #className").val();
		var relativeId;
		switch(parseInt(typeId)){
			case 1: relativeId = null;break;
			case 2: relativeId = deptId;break;
			case 3: relativeId = majorId;break;
			case 4: relativeId = classId;break;
			default:relativeId = null;break;
		}
		
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseServlet?method=showPage', {
			ajaxPost : 'T',
			courseName : courseName,
			typeId : typeId,
			relativeId : relativeId
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	});
	
	$("#mdfCourseBtn").click(function(){
		var courseName = $("#mdfCourse #courseName").val();
		var courseId = $("#mdfCourse #courseName").data("courseid");
		var origValue = $("#mdfCourse #courseName").data("origvalue");
		
		if(courseName.trim().length == 0){
			alertMsg("课程名称不能为空", 'W');
			return;
		}
		
		if(courseName == origValue){
			alertMsg("您没做任何修改", 'W');
			return;
		}
		
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseServlet?method=modifyCourse', {
			ajaxPost : 'T',
			courseName : courseName,
			courseId : courseId
		}, function(resText, statusText, xhr, $form) {
			if (resText.type == 'S') {
				$("#showCoursesTab").trigger("click");
				$("#searchBtn").trigger("click");
			}
			ShowMsg(resText);
		});
		$form.ajaxSubmit(options);
	});
	
	
	$("#chooseTeacher #deptName").change(function(){
		
		var target = $("#chooseTeacher #teacherList ul:first");
		var deptId = parseInt($(this).val());
		if(deptId < 1){
			target.empty();
		}else{
			var $form = $("#MainForm");
			var options = buildAjaxOptions('TeacherServlet?method=showPage', {
				ajaxPost : 'T',
				forList : true,
				deptId : deptId
			}, function(resText, statusText, xhr, $form) {
				var teachers = resText.content;
				target.empty();
				$.each(teachers, function(index, t){
					var li = $("<li><label><input type='radio' name='cdtTeacher' value='" + t.id +"'/>" + t.name + "</lable></li>");
					target.append(li);
				});
			});
			$form.ajaxSubmit(options);
		}
	});
	
	//分配教师按钮
	$("#asnTeacherBtn").click(function(){
		var courseId = $("#asnTeacher #courseList input[name='cdtCourse']:checked").val();
		if(!courseId){
			alertMsg("请选择课程", 'W');
			return;
		}
		var classIds = [];
		$("#asnTeacher #classList input[name='cdtClass']").each(function(){
			if($(this).prop("checked")){
				classIds.push($(this).val());
			}
		});
		if(classIds.length == 0){
			alertMsg("请选择班级", 'W');
			return;
		}
		var teacherId = $("#asnTeacher #teacherList input[name='cdtTeacher']:checked").val();
		if(!teacherId){
			alertMsg("请选择教师", 'W');
			return;
		}
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseServlet?method=asnTeacher', {
			ajaxPost : 'T',
			courseId : courseId,
			classIds : classIds.join(","),
			teacherId : teacherId
		}, function(resText, statusText, xhr, $form) {
			if(resText.type == "S"){
				$("#asnTeacher #deptName").val(-1).trigger("change");
				$("#chooseTeacher #deptName").val(-1).trigger("change");
				$("#chooseClass #classList ul:first").empty();
			}
			ShowMsg(resText);
		});
		$form.ajaxSubmit(options);
	});
});


/**
 * 翻页后重置内容
 * @param currPage
 * @param pageSize
 * @param content
 */
function reBuildPageContent(currPage, pageSize, content){
	 var tbody = $("#showCourses table tbody");
	 //清空内容
	 tbody.empty();
	 if(content.length == 0){
		 alertMsg("没有检索到相关内容", 'W');
		 return;
	 }
	 $.each(content, function(index, c){
		 var tr = $("<tr></tr>");
		 var trClass = index % 2 == 0? "info" : "";
		 tr.attr("class", trClass);
		 var td1 = $("<td>" + ((currPage-1)*pageSize+index+1) + "</td>");
		 var td2 = $("<td>" + c.ct.name + "</td>");
		 var td3 = $("<td>" + c.name + "</td>");
		 var td4 = $("<td>" + c.relativeName + "</td>");
		 var td5 = $("<td></td>");
		 var modBtn = $("<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"_modifyCourse(this)\">修改</button>");
		 modBtn.data("courseid", c.id)
		 	.data("coursename", c.name)
		 	.data("belong", c.relativeName)
		 	.data("coursetype", c.ct.name)
		 	.data("origvalue", c.name);
		 var delBtn = $("<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"_deleteCourse(this)\">删除</button>");
		 delBtn.data("courseid", c.id);
		 td5.append(modBtn).append(delBtn);
		 tr.append(td1).append(td2).append(td3).append(td4).append(td5);
		 tbody.append(tr);
	 });
}

//翻页
function pageBarOnClick(pageNo) {
	var typeId = $("#showCourses #typeName").val();
	var courseName = $("#showCourses #courseName").val();
	var deptId = $("#showCourses #deptName").val();
	var majorId = $("#showCourses #majorName").val();
	var classId = $("#showCourses #className").val();
	var relativeId;
	switch (parseInt(typeId)) {
	case 1:
		relativeId = null;
		break;
	case 2:
		relativeId = deptId;
		break;
	case 3:
		relativeId = majorId;
		break;
	case 4:
		relativeId = classId;
		break;
	default:
		relativeId = null;
		break;
	}

	var $form = $("#MainForm");
	var options = buildAjaxOptions('CourseServlet?method=showPage', {
		currPage : pageNo,
		pageSize : $("#pageSize").val(),
		ajaxPost : 'T',
		courseName : courseName,
		typeId : typeId,
		relativeId : relativeId
	}, onNextPageSuccess);
	$form.ajaxSubmit(options);
}


function _deleteCourse(obj) {
	var courseId = $(obj).data("courseid");
	if (confirm("确认删除?")) {
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseServlet?method=deleteCourse', {
			ajaxPost : 'T',
			courseId : courseId
		},function(resText, statusText, xhr, $form) { //删除成功回调函数
			//TODO
			var tbody = $(obj).parent().parent().parent();
			if (tbody.children("tr").length == 1 && $("#currPage").val() > 1) { //是否为当前页的最后一行
				//返回上一页
				pageBarOnClick($("#currPage").val() - 1);
			} else {
				pageBarOnClick($("#currPage").val());
			}
			onSuccessCallBack(resText, statusText, xhr, $form);
		});
		$form.ajaxSubmit(options);
	}
}


function _modifyCourse(obj){
	$("#mdfCourseTab").css("display", "block");
	$("#mdfCourseTab a").tab("show");
	//取值
	var courseId = $(obj).data("courseid");
 	var courseName = $(obj).data("coursename");
 	var belong = $(obj).data("belong");
 	var courseType = $(obj).data("coursetype");
 	var origValue = $(obj).data("origvalue");
	//赋值
	$("#mdfCourse #typeName").val(courseType);
	$("#mdfCourse #belong").val(belong);
	$("#mdfCourse #courseName").val(courseName).data("courseid", courseId).data("origvalue", origValue);
}


function chooseCourse(){
	var typeId = $("#chooseCourse #typeName").val();
	if(typeId == -1){
		return;
	}
	var target = $("#chooseCourse #courseList ul:first");
	target.empty();
	var deptId = $("#chooseCourse #deptName").val();
	var majorId = $("#chooseCourse #majorName").val();
	var classId = $("#chooseCourse #className").val();
	var relativeId;
	switch(parseInt(typeId)){
		case 1: relativeId = null;break;
		case 2: relativeId = deptId;break;
		case 3: relativeId = majorId;break;
		case 4: relativeId = classId;break;
		default:relativeId = null;break;
	}
	var $form = $("#MainForm");
	var options = buildAjaxOptions('CourseServlet?method=showPage', {
		ajaxPost : 'T',
		forList : true,
		typeId : typeId,
		relativeId : relativeId
	}, function(resText, statusText, xhr, $form){
		var content = resText.content;
		$.each(content, function(index, c){
			var radio = $("<input type=\"radio\" name=\"cdtCourse\" />");
			radio.bind("click", function(){
				cdtCourseOnClick(this);
			});
			radio.val(c.id);
			var li = $("<li></li>");
			var label = $("<label><label>");
			label.text(c.name);
			label.prepend(radio);
			li.append(label);
			target.append(li);
		});
	});
	$form.ajaxSubmit(options);
}


function cdtCourseOnClick(obj){
	var $form = $("#MainForm");
	var course = $(obj);
	var target = $("#classList ul");
	target.empty();
	var options = buildAjaxOptions('CourseServlet?method=cdtClasses', {
		ajaxPost : 'T',
		courseId : course.val()
	}, function(resText, statusText, xhr, $form){
		if(resText.type){
			ShowMsg(resText);
		}else{
			if(resText.length == 0){
				alertMsg("该课程已被分配", 'S');
				return;
			}
			$.each(resText, function(di, dc){
				var $deptLi = $("<li data-child='.b'><span class='glyphicon glyphicon-chevron-up switch' onclick='switchOnClick(this)'></span></li>");
				var $deptChBox = $("<input type='checkbox' name='cdtDept'/>").val(dc.id);
				var $deptLabel = $("<label class='sub' onclick='subOnClick(this)'></label>").append($deptChBox).append(dc.name);
				$deptLi.append($deptLabel);
				var $deptUl = $("<ul class=\'b\'></ul>");
				var majors = dc.majors;
				$.each(majors, function(mi, mc){
					var $majorLi = $("<li data-child='.c'><span class='glyphicon glyphicon-chevron-up switch' onclick='switchOnClick(this)'></li>");
					var $majorChBox = $("<input type='checkbox'name='cdtMajor'/>").val(mc.id);
					var $majorLabel = $("<label class='sub' onclick='subOnClick(this)'></label>").append($majorChBox).append(mc.name);
					$majorLi.append($majorLabel);
					var $majorUl = $("<ul class=\'c\'></ul>");
					var clses = mc.classes;
					$.each(clses, function(ci, cc){
						var $classLi = $(" <li><label class='sub' onclick='subOnClick(this)'><input type='checkbox' name='cdtClass' value= '" + cc.id + "'/>" + cc.name + "</label></li>");
						$majorUl.append($classLi);
					});
					$majorLi.append($majorUl);
					$deptUl.append($majorLi);
				});
				$deptLi.append($deptUl);
				target.append($deptLi);
			});
		}
	});
	$form.ajaxSubmit(options);
}

function switchOnClick(obj){
	var target = $(obj).parent().children($(obj).parent().data("child"));
    var display = target.css("display") == "none"? "block" : "none";
    target.css("display", display);
    $(obj).toggleClass("glyphicon-chevron-up", 1000).toggleClass("glyphicon-chevron-down", 1000);
}

function subOnClick(obj){
	 var isChecked = $(obj).children("input[type='checkbox']").prop("checked");
     var parent = $(obj).parent("li");
     parent.children($(parent.data("child"))).find("input[type='checkbox']").each(function(){
         $(this).prop("checked", isChecked);
     });
}