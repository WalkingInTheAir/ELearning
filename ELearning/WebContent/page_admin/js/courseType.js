$(function(){
	//隐藏修改院系tab
	$(".nav li").click(function(){
		if($(this).attr("id") != "mdfTypeTab"){
			$("#mdfTypeTab").css("display", "none");
		}
	});
	/**
	 * 添加课程按钮
	 */
	$("#addTypeBtn").click(function(){
		var $form = $("#MainForm");
		var typeName = $("#addType").find("#typeName").val();
		if(null == typeName || typeName.trim().length == 0){
			alertMsg("课程名称不能为空");
			return;
		}
		var options = buildAjaxOptions('CourseTypeServlet?method=addType', 
				{typeName : typeName}, 
				onSuccessCallBack);
		$form.ajaxSubmit(options);
	});
	
	
	$("#showTypesTab").click(function() {
		$(this).find("a:first").tab("show");
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseTypeServlet?method=showPage', {
			currPage : $("#currPage").val(),
			ajaxPost : 'T'
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	});
	
	
	$("#mdfTypeBtn").click(function(){
		var target = $("#mdfType").find("#typeName");
		var typeName = target.val();
		if(null == typeName || typeName.trim().length == 0){
			alertMsg("课程类型不能为空", 'W');
			return;
		}
		var origValue = target.data("origvalue");
		if(origValue == typeName){
			alertMsg("您没做任何修改", 'W');
			return;
		}
		var typeId = target.data("typeid");
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseTypeServlet?method=modifyType', {
			typeId: typeId,
			typeName : typeName,
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form) {
			if (resText.type == 'S') {
				$("#showTypesTab").trigger("click");
			}
			ShowMsg(resText);
		});
		$form.ajaxSubmit(options);
	});
});

/**
 * 修改课程类型信息
 */
function _modifyType(obj){
	$("#mdfTypeTab").css("display", "block");
	$("#mdfTypeTab a").tab("show");
	//传值
	var typeId = $(obj).data("typeid");
	var typeName = $(obj).data("typename");
	
	var target = $("#mdfType").find("#typeName");
	target.val(typeName).data("typeid", typeId).data("origvalue", typeName);
}

function _deleteType(obj) {
	if (confirm("确认删除?")) {
		var $form = $("#MainForm");
		var typeId = $(obj).data("typeid");
		var options = buildAjaxOptions('CourseTypeServlet?method=deleteType', {
			typeId : typeId,
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form) {	//删除成功回调函数
			//TODO
			var tbody = $(obj).parent().parent().parent();
			if(tbody.children("tr").length == 1 && $("#currPage").val() > 1){	//是否为当前页的最后一行
				//返回上一页
				pageBarOnClick($("#currPage").val() - 1);
			}else{
				pageBarOnClick($("#currPage").val());
			}
			onSuccessCallBack(resText, statusText, xhr, $form);
		});
		$form.ajaxSubmit(options);
	}
}

//翻页
function pageBarOnClick(pageNo) {
	var $form = $("#MainForm");
	var options = buildAjaxOptions('CourseTypeServlet?method=showPage', {
		currPage : pageNo,
		pageSize : $("#pageSize").val(),
		ajaxPost : 'T'
	}, onNextPageSuccess);
	$form.ajaxSubmit(options);
}

//重置内容区
function reBuildPageContent(currPage, pageSize, content){
	
	 var tbody = $("#showTypes table tbody");
	 //清空内容
	 tbody.empty();
	 $.each(content, function(index, c){
		 var tr = $("<tr></tr>");
		 var trClass = index % 2 == 0? "info" : "";
		 tr.attr("class", trClass);
		 var td1 = $("<td>" + ((currPage-1)*pageSize+index+1) + "</td>");
		 var td2 = $("<td>" + c.name + "</td>");
		 var td3 = $("<td></td>");
		 var modBtn = $("<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"_modifyType(this)\">修改</button>");
		 modBtn.data("typeid", c.id).data("typename", c.name);
		 var delBtn = $("<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"_deleteType(this)\">删除</button>");
		 delBtn.data("typeid", c.id);
		 td3.append(modBtn).append(delBtn);
		 tr.append(td1).append(td2).append(td3);
		 tbody.append(tr);
	 });
}