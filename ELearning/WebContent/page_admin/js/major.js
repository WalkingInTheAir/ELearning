$(function() {
	
	//隐藏修改院系tab
	$(".nav li").click(function(){
		if($(this).attr("id") != "mdfMajorTab"){
			$("#mdfMajorTab").css("display", "none");
		}
	});
	
	// 添加专业tab
	$("#addMajorTab,#addMajorsTab,#mdfMajorTab").click(function(event, params) {
		
		$(this).find("a:first").tab("show");
		var $form = $("#MainForm");
		var $select = $($(this).find("a:first").attr("href")).find("select[id='deptName']");
		var options = buildAjaxOptions('DepartServlet?method=showPage', {
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form){
			var depts = resText.content;
			$select.empty();
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
	
	//添加院系按钮
	$("#addMajorBtn").click(function(){
		var majorName = $("#majorName").val();
		if(majorName.length < 1){
			alertMsg("院系名称不能为空", "W");
		} else {
			var deptId = $("#addMajor").find("select[id='deptName']").val();
			var $form = $("#MainForm");
			var options = buildAjaxOptions('MajorServlet?method=addMajor', {
				deptId : deptId,
				majorName : majorName,
				ajaxPost : 'T'
			}, function(resText, statusText, xhr, $form) {
				if(resText.type.toLowerCase() == 's'){
					$form.resetForm();
				}
				ShowMsg(resText);
			});
			$form.ajaxSubmit(options);
		}
	});
	
	/**
	 * 专业信息Tab
	 */
	$("#showMajorsTab").click(function() {
		$(this).find("a:first").tab("show");
		var $form = $("#MainForm");
		var options = buildAjaxOptions('MajorServlet?method=showPage', {
			currPage : $("#currPage").val(),
			ajaxPost : 'T'
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	});
	
	/**
	 * 选定院系
	 */
	$("#showMajors").find("select[id='deptName']").change(function(){
		/*if($(this).val() < 0)
			return;*/
		var $form = $("#MainForm");
		var options = buildAjaxOptions('MajorServlet?method=showPage', {
			deptId : $(this).val(),
			ajaxPost : 'T'
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	});
	
	//修改院系信息
	$("#mdfMajorBtn").click(function() {
		var $form = $("#MainForm");
		var $select = $("#mdfMajor").find("#deptName");
		var deptId = $select.val();
		var origDeptId = $select.data("origid");
		var majorName = $("#mdf_majorName").val().trim();
		var origMajorName = $("#mdf_majorName").data("origname");
		var majorId = $("#mdf_majorId").val();
		if (deptId == origDeptId && majorName == origMajorName) {
			ShowMsg({
				type : "W",
				msg : "与原信息一致，请修改",
				title : "提示"
			});
			return false;
		}
		var options = buildAjaxOptions('MajorServlet?method=modifyMajor', {
			deptId : deptId,
			majorName : majorName,
			majorId : majorId,
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form) {
			if (resText.type == 'S') {
				$("#showMajorsTab").trigger("click");
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
	 var tbody = $("#showMajors table tbody");
	 //清空内容
	 tbody.empty();
	 $.each(content, function(index, c){
		 var tr = $("<tr></tr>");
		 var trClass = index % 2 == 0? "info" : "";
		 tr.attr("class", trClass);
		 var td1 = $("<td>" + ((currPage-1)*pageSize+index+1) + "</td>");
		 var td2 = $("<td>" + c.dept.name + "</td>");
		 var td3 = $("<td>" + c.name + "</td>");
		 var td4 = $("<td></td>");
		 var modBtn = $("<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"_modifyMajor(this)\">修改</button>");
		 modBtn.data("majorid", c.id).data("deptid", c.dept.id).data("majorname", c.name);
		 var delBtn = $("<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"_deleteMajor(this)\">删除</button>");
		 delBtn.data("majorid", c.id);
		 td4.append(modBtn).append(delBtn);
		 tr.append(td1).append(td2).append(td3).append(td4);
		 tbody.append(tr);
	 });
}

//翻页
function pageBarOnClick(pageNo) {
	var $form = $("#MainForm");
	var options = buildAjaxOptions('MajorServlet?method=showPage', {
		currPage : pageNo,
		pageSize : $("#pageSize").val(),
		deptId : $("#showMajors").find("select[id='deptName']").val(),
		ajaxPost : 'T'
	}, onNextPageSuccess);
	$form.ajaxSubmit(options);
}

/**
 * 删除专业
 * @param obj
 */
function _deleteMajor(obj) {
	if (confirm("确认删除?")) {
		var $form = $("#MainForm");
		var majorId = $(obj).data("majorid");
		var options = buildAjaxOptions('MajorServlet?method=deleteMajor', {
			majorId : majorId,
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form) { //删除成功回调函数
			//TODO
			var tbody = $(obj).parent().parent().parent();
			if (tbody.children("tr").length == 1 && $("#currPage").val() > 1) { //是否为当前页的最后一行
				//返回上一页
				pageBarOnClick($("#currPage").val() - 1);
			} else {
				pageBarOnClick($("#currPage").val());
			}
			var $select = $(obj).parents(".tab-pane").eq(0).find("#deptName");
			var origValue = $select.val();
			onSuccessCallBack(resText, statusText, xhr, $form);
			$select.val(origValue);
		});
		$form.ajaxSubmit(options);
	}
}

function _modifyMajor(obj){
	$("#mdfMajorTab").css("display", "block");
	$("#mdfMajorTab a").tab("show");
	$("#mdfMajorTab").trigger("click", {options:{async: false}});
	//取值
	var deptId = $(obj).data("deptid");
	var majorId = $(obj).data("majorid");
	var majorName = $(obj).data("majorname")
	//赋值
	var $select = $("#mdfMajor").find("#deptName");
	$select.val(deptId).data("origid", deptId);
	$("#mdf_majorName").val(majorName).data("origname", majorName);
	$("#mdf_majorId").val(majorId);
}

