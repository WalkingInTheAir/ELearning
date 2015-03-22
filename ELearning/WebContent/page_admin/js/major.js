$(function() {
	// 添加专业tab
	$("#addMajorTab,#addMajorsTab").click(function() {
		
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
		 var modBtn = $("<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"_modifyDept(this)\">修改</button>");
		 modBtn.data("majorid", c.id).data("deptid", c.dept.id);
		 var delBtn = $("<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"_deleteDept(this)\">删除</button>");
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
function _deleteDept(obj) {
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
			onSuccessCallBack(resText, statusText, xhr, $form);
		});
		$form.ajaxSubmit(options);
	}
}

