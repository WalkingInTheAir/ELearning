$(function(){
	//隐藏修改院系tab
	$(".nav li").click(function(){
		if($(this).attr("id") != "modifyClassTab"){
			$("#modifyClassTab").css("display", "none");
		}
	});
	
	//点击添加班级Tab
	$("#addClassTab, #showClassesTab").click(function(event, params){
		$(this).find("a:first").tab("show");
		var $form = $("#MainForm");
		var $select = $($(this).find("a:first").attr("href")).find("select[id='deptName']");
		var options = buildAjaxOptions('DepartServlet?method=showPage', {
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form){
			var depts = resText.content;
			$select.empty();
			$select.append($("<option value=\"-1\">--请选择院系--</option>"));
			$.each(depts, function(index, c){
				var $option = $("<option></option>");
				$option.val(c.id).text(c.name);
				$select.append($option);
			});
		});
		if(params && params.options){
			$.extend(true, options, params.options);
		}
		$form.ajaxSubmit(options);
	});
	
	$("#showClassesTab").click(function(){
		var $form = $("#MainForm");
		var options = buildAjaxOptions('ClassServlet?method=showPage', {
			currPage : $("#currPage").val(),
			ajaxPost : 'T'
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	})
	//添加班级的院系下拉框
	$("#addClass #deptName").change(function(){
		var deptId = $(this).val();
		var $select = $(this).parent().parent().next().find("#majorName");
		if(deptId == -1){
			$select.empty();
			$select.append("")
			return;
		}
		var $form = $("#MainForm");
		var options = buildAjaxOptions('MajorServlet?method=showPage', {
			ajaxPost : 'T',
			deptId : deptId
		}, function(resText, statusText, xhr, $form){
			var majors = resText.content;
			$select.empty();
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
	
	//点击添加班级按钮
	$("#addClassBtn").click(function(){
		var major = $(this).parent().parent().parent().find("#majorName");
		var majorId = -1;
		var className = $(this).parent().parent().parent().find("#className").val();
		if(major.length > 0 && major.val() != null){
			majorId = major.val();
		}
		if(majorId < 0){
			alertMsg("请选择专业", 'W');
			return false;
		}
		if(className == null || className.trim().length == 0){
			alertMsg("请设置班级名称", 'W');
			return false;
		}
		
		var $form = $("#MainForm");
		var options = buildAjaxOptions('ClassServlet?method=addClass', {
			ajaxPost : 'T',
			majorId : majorId,
			className : className
		}, onSuccessCallBack);
		$form.ajaxSubmit(options);
	});
	
	/**
	 * 根据院系显示专业
	 */
	$("#showClasses #deptName").change(function(){
		var deptId = $(this).val();
		var $majorSelect = $(this).parent().parent().find("select[id='majorName']");
		if(deptId < 0){
			$majorSelect.empty();
			$majorSelect.append("<option value=\"-1\">--请选择专业--</option>");
			return;
		}
		var $form = $("#MainForm");
		var options = buildAjaxOptions('MajorServlet?method=showPage', {
			ajaxPost : 'T',
			deptId : deptId
		}, function(resText, statusText, xhr, $form){
			$majorSelect.empty();
			$majorSelect.append("<option value=\"-1\">--请选择专业--</option>");
			$majorSelect.append("<option value=\"0\">--全部--</option>");
			$.each(resText.content, function(index, c){
				var option = $("<option></option>").val(c.id).text(c.name);
				$majorSelect.append(option);
			})
		});
		$form.ajaxSubmit(options);
	});
	
	$("#showClasses #majorName").change(function(){
		 var deptId = $(this).parent().parent().find("select[id='deptName']").val();
		 var majorId = $(this).val();
		 if(deptId < 0){
			 alertMsg("请先选择院系", 'W');
			 return;
		 }
		 var $form = $("#MainForm");
		 var options = buildAjaxOptions('ClassServlet?method=showPage', {
				ajaxPost : 'T',
				majorId : majorId,
				deptId : deptId
			}, onNextPageSuccess);
			$form.ajaxSubmit(options);
	});
	
	/**
	 * 修改按钮
	 */
	$("#mdfClassBtn").click(function(){
		var deptNameObj = $("#modifyClass").find("#deptName");
		var majorNameObj = $("#modifyClass").find("#majorName");
		var classNameObj = $("#modifyClass").find("#className");
		
		var className = classNameObj.val();
		var origValue = classNameObj.data("origvalue");
		if(className == origValue){
			alertMsg("与原信息一致，请您修改！");
			return;
		}
		var classId = classNameObj.data("classid");
		var deptId = deptNameObj.data("deptid");
		var majorId = majorNameObj.data("majorid");
		
		 var $form = $("#MainForm");
		 var options = buildAjaxOptions('ClassServlet?method=mdfClass', {
				ajaxPost : 'T',
				classId : classId,
				majorId : majorId,
				deptId : deptId,
				className : className
			}, function(resText, statusText, xhr, $form){
				if (resText.type == 'S') {
					$("#showClassesTab").trigger("click");
				}
				ShowMsg(resText);
			});
		$form.ajaxSubmit(options);
	});
});

//翻页
function pageBarOnClick(pageNo) {
	var $form = $("#MainForm");
	var options = buildAjaxOptions('ClassServlet?method=showPage', {
		currPage : pageNo,
		pageSize : $("#pageSize").val(),
		deptId : $("#showClasses").find("select[id='deptName']").val(),
		majorId : $("#showClasses").find("select[id='majorName']").val(),
		ajaxPost : 'T'
	}, onNextPageSuccess);
	$form.ajaxSubmit(options);
}

function reBuildPageContent(currPage, pageSize, content){
	var tbody = $("#showClasses table tbody");
	 //清空内容
	 tbody.empty();
	 $.each(content, function(index, c){
		 var tr = $("<tr></tr>");
		 var trClass = index % 2 == 0? "info" : "";
		 tr.attr("class", trClass);
		 var td1 = $("<td>" + ((currPage-1)*pageSize+index+1) + "</td>");
		 var td2 = $("<td>" + c.major.dept.name + "</td>");
		 var td3 = $("<td>" + c.major.name + "</td>");
		 var td4 = $("<td>" + c.name + "</td>");
		 var td5 = $("<td></td>");
		 var modBtn = $("<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"_modifyClass(this)\">修改</button>");
		 modBtn.data("classid", c.id)
		 	.data("classname", c.name)
		 	.data("majorid", c.major.id)
		 	.data("majorname", c.major.name)
		 	.data("deptid", c.major.dept.id)
		 	.data("deptname", c.major.dept.name);
		 var delBtn = $("<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"_deleteClass(this)\">删除</button>");
		 delBtn.data("classid", c.id);
		 td5.append(modBtn).append(delBtn);
		 tr.append(td1).append(td2).append(td3).append(td4).append(td5);
		 tbody.append(tr);
	 });
}

/**
 * 删除班级
 * @param obj
 */
function _deleteClass(obj) {
	if (confirm("确认删除?")) {
		var $form = $("#MainForm");
		var classId = $(obj).data("classid");
		var options = buildAjaxOptions('ClassServlet?method=deleteClass', {
			classId : classId,
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form) { // 删除成功回调函数
			// TODO
			var tbody = $(obj).parent().parent().parent();
			if (tbody.children("tr").length == 1 && $("#currPage").val() > 1) { // 是否为当前页的最后一行
				// 返回上一页
				pageBarOnClick($("#currPage").val() - 1);
			} else {
				pageBarOnClick($("#currPage").val());
			}
			var $origDeptObj = $(obj).parents(".tab-pane").eq(0).find("#deptName")
			var origDeptValue = $origDeptObj.val();
			var $origMajorObj = $(obj).parents(".tab-pane").eq(0).find("#majorName")
			var origMajorValue = $origMajorObj.val();
			onSuccessCallBack(resText, statusText, xhr, $form);
			$origDeptObj.val(origDeptValue);
			$origMajorObj.val(origMajorValue);
		});
		$form.ajaxSubmit(options);
	}
}

/**
 * 修改班级信息
 * @param obj
 */
function _modifyClass(obj){
	$("#modifyClassTab").css("display", "block");
	$("#modifyClassTab a").tab("show");
	
	var classId = $(obj).data("classid");
	var className = $(obj).data("classname");
	var deptId = $(obj).data("deptid");
	var deptName = $(obj).data("deptname");
	var majorId = $(obj).data("majorid");
	var majorName = $(obj).data("majorname");
	
	var $targetDept = $("#modifyClass").find("#deptName");
	var $targetMajor = $("#modifyClass").find("#majorName");
	var $targetClass = $("#modifyClass").find("#className");
	
	$targetDept.val(deptName).data("deptid", deptId);
	$targetMajor.val(majorName).data("majorid", majorId);
	$targetClass.val(className).data("classid", classId).data("origvalue", className);
}
