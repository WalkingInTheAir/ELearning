$(function(){
	
	//隐藏修改院系tab
	$(".nav li").click(function(){
		if($(this).attr("id") != "mdfStudentTab"){
			$("#mdfStudentTab").css("display", "none");
		}
	});
	
	/**
	 * 添加学生tab
	 */
	$("#addStudentTab, #showStudentTab").click(function(event, params) {
		var target = $(this).find("a:first");
		target.tab("show");
		var isShowStudent = $(this).attr("id") === 'showStudentTab';
		var $form = $("#MainForm");
		var deptSelect = $(target.attr("href")).find("#deptName");
		var options = buildAjaxOptions('DepartServlet?method=showPage', {
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form){
			var content = resText.content;
			deptSelect.empty();
			deptSelect.append("<option value=\"-1\">--请选择院系--</option>");
			if(isShowStudent){
				deptSelect.append("<option value=\"0\">--显示全部--</option>")
			}
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
	
	$("#showStudentTab").click(function() {
		$(this).find("a:first").tab("show");
		var $form = $("#MainForm");
		var options = buildAjaxOptions('StudentServlet?method=showPage', {
			currPage : $("#currPage").val(),
			ajaxPost : 'T'
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	});
	
	//院系下拉框
	$("#addStudent #deptName, #showStudent #deptName").change(function(){
		var deptId = $(this).val();
		var $select = $(this).parent().parent().next().find("#majorName");
		if(deptId == -1){
			$select.empty();
			$select.append("<option value=\"-1\">--请选择专业--</option>");
			return;
		}else if(deptId == 0){
			queryStudents(0, 0, 0);
			return;
		}
		var isShowStudent = $(this).parents(".tab-pane:first").attr("id") === 'showStudent';
		var $form = $("#MainForm");
		var options = buildAjaxOptions('MajorServlet?method=showPage', {
			ajaxPost : 'T',
			deptId : deptId
		}, function(resText, statusText, xhr, $form){
			var majors = resText.content;
			$select.empty();
			$select.append("<option value=\"-1\">--请选择专业--</option>")
			if(isShowStudent){
				$select.append("<option value=\"0\">--显示全部--</option>")
			}
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
	
	$("#addStudent #majorName, #showStudent #majorName").change(function(){
		var majorId = $(this).val();
		var deptId = $(this).parent().parent().parent().find("#deptName").val();
		var $select = $(this).parent().parent().find("#className");
		if(majorId == -1){
			$select.empty();
			$select.append("<option value=\"-1\">--请选择班级--</option>")
			return;
		}else if(majorId == 0){
			queryStudents(deptId, 0, 0);
			return;
		}
		var isShowStudent = $(this).parents(".tab-pane:first").attr("id") === 'showStudent';
		var $form = $("#MainForm");
		var options = buildAjaxOptions('ClassServlet?method=showPage', {
			ajaxPost : 'T',
			majorId : majorId
		}, function(resText, statusText, xhr, $form){
			var classes = resText.content;
			$select.empty();
			$select.append("<option value=\"-1\">--请选择班级--</option>");
			if(isShowStudent){
				$select.append("<option value=\"0\">--显示全部--</option>")
			}
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
	
	$("#showStudent #className").change(function(){
		var deptId = $(this).parent().parent().parent().find("#deptName").val();
		var majorId = $(this).parent().parent().parent().find("#majorName").val();
		var classId = $(this).val();
		if(classId == -1){
			return;
		}else{
			queryStudents(deptId, majorId, classId);
		}
	});
	
	$("#addStudentBtn").click(function(){
		var deptId = $("#addStudent #deptName").val();
		var majorId = $("#addStudent #majorName").val();
		var classId = $("#addStudent #className").val();
		
		if(null == classId || classId < 0){
			alertMsg("请选择班级" , 'w');
			return;
		}
		var stuName = $("#addStudent #studentName").val();
		if(null == stuName || stuName.length == 0){
			alertMsg("学生姓名不能为空", 'w');
			return;
		}
		var stuNum = $("#addStudent #studentNum").val();
		if(null == stuNum || stuNum.length == 0){
			alertMsg("学生学号不能为空", 'w');
			return;
		}
		
		var $form = $("#MainForm");
		var options = buildAjaxOptions('StudentServlet?method=addStudent', {
			ajaxPost : 'T',
			classId : classId,
			stuName : stuName,
			stuNum : stuNum
		}, onSuccessCallBack);
		$form.ajaxSubmit(options);
	});
	
	
	$("#mdfStudentBtn").click(function(){
		var stuObj = $("#mdfStudent #stuName");
		var origValue = stuObj.data("origvalue");
		var value = stuObj.val();
		if(origValue == value){
			alertMsg("您没有做任何修改", 'W');
			return;
		}
		var stuId = stuObj.data("stuid");
		var $form = $("#MainForm");
		var options = buildAjaxOptions('StudentServlet?method=modifyStudent', {
			ajaxPost : 'T',
			stuId : stuId,
			stuName : value
		}, function(resText, statusText, xhr, $form){
			if (resText.type == 'S') {
				$("#showStudentTab").trigger("click");
			}
			ShowMsg(resText);
		});
		$form.ajaxSubmit(options);
	});
});

function queryStudents(deptId, majorId, classId){
	
	var $form = $("#MainForm");
	var options = buildAjaxOptions('StudentServlet?method=showPage', {
		ajaxPost : 'T',
		classId : classId,
		deptId : deptId,
		majorId : majorId,
	}, onNextPageSuccess);
	$form.ajaxSubmit(options);
}

/**
 * 翻页后重置内容
 * @param currPage
 * @param pageSize
 * @param content
 */
function reBuildPageContent(currPage, pageSize, content){
	 var tbody = $("#showStudent table tbody");
	 //清空内容
	 tbody.empty();
	 $.each(content, function(index, c){
		 var tr = $("<tr></tr>");
		 var trClass = index % 2 == 0? "info" : "";
		 tr.attr("class", trClass);
		 var td1 = $("<td>" + ((currPage-1)*pageSize+index+1) + "</td>");
		 var td2 = $("<td>" + c.clas.major.dept.name + "</td>");
		 var td3 = $("<td>" + c.clas.major.name + "</td>");
		 var td4 = $("<td>" + c.clas.name + "</td>");
		 var td5 = $("<td>" + c.name + "</td>");
		 var td6 = $("<td>" + c.num + "</td>");
		 var td7 = $("<td></td>");
		 var modBtn = $("<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"_modifyStudent(this)\">修改</button>");
		 modBtn.data("stuid", c.id)
		 	.data("stuname", c.name)
		 	.data("stunum", c.num)
		 	.data("classname", c.clas.name)
		 	.data("majorname", c.clas.major.name)
		 	.data("deptname", c.clas.major.dept.name);
		 var delBtn = $("<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"_deleteStudent(this)\">删除</button>");
		 delBtn.data("stuid", c.id);
		 td7.append(modBtn).append(delBtn);
		 tr.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
		 tbody.append(tr);
	 });
}

//翻页
function pageBarOnClick(pageNo) {
	var $form = $("#MainForm");
	var options = buildAjaxOptions('StudentServlet?method=showPage', {
		currPage : pageNo,
		pageSize : $("#pageSize").val(),
		deptId : $("#showStudent").find("select[id='deptName']").val(),
		majorId : $("#showStudent").find("#majorName").val(),
		classId : $("#showStudent").find("#className").val(),
		ajaxPost : 'T'
	}, onNextPageSuccess);
	$form.ajaxSubmit(options);
}

function _deleteStudent(obj) {
	if (confirm("确认删除?")) {
		var stuId = $(obj).data("stuid");
		if (stuId <= 0) {
			alertMsg("参数异常，请稍后重试", 'W');
			return;
		} else {
			var $form = $("#MainForm");
			var options = buildAjaxOptions('StudentServlet?method=deleteStudent', {
				stuId : stuId,
				ajaxPost : 'T'
			}, function(resText, statusText, xhr, $form) { // 删除成功回调函数
				// TODO
				var tbody = $(obj).parent().parent().parent();
				if (tbody.children("tr").length == 1
						&& $("#currPage").val() > 1) { // 是否为当前页的最后一行
					// 返回上一页
					pageBarOnClick($("#currPage").val() - 1);
				} else {
					pageBarOnClick($("#currPage").val());
				}
				var $deptName = $(obj).parents(".tab-pane").eq(0).find("#deptName");
				var $majorName = $(obj).parents(".tab-pane").eq(0).find("#majorName");
				var $className = $(obj).parents(".tab-pane").eq(0).find("#className");
				var deptName = $deptName.val();
				var majorName = $majorName.val();
				var className = $className.val();
				onSuccessCallBack(resText, statusText, xhr, $form);
				$deptName.val(deptName);
				$majorName.val(majorName);
				$className.val(className);
			});
			$form.ajaxSubmit(options);
		}
	}
}

function _modifyStudent(obj){
	$("#mdfStudentTab").css("display", "block");
	$("#mdfStudentTab a").tab("show");
	$("#mdfStudentTab").trigger("click", {options:{async: false}});
	//取值
	var stuId = $(obj).data("stuid");
	var stuName = $(obj).data("stuname");
	var stuNum = $(obj).data("stunum");
	var className = $(obj).data("classname");
	var majorName = $(obj).data("majorname");
	var deptName = $(obj).data("deptname");
	//赋值
	$("#mdfStudent #deptName").val(deptName);
	$("#mdfStudent #majorName").val(majorName);
	$("#mdfStudent #className").val(className);
	$("#mdfStudent #stuNum").val(stuNum);
	$("#mdfStudent #stuName").val(stuName).data("origvalue", stuName).data("stuid", stuId);
}

