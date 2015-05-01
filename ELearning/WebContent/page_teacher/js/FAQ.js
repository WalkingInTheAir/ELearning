$(function(){
	//隐藏修改院系tab
	$(".nav li").click(function(){
		if($(this).attr("id") != "modifyFAQTab"){
			$("#modifyFAQTab").css("display", "none");
		}
	});
	$("#showFAQTab").click(function() {
		$(this).find("a:first").tab("show");
		var $form = $("#MainForm");
		var options = buildAjaxOptions('TeacherServlet?method=backOperate&item=FAQ', {
			currPage : $("#currPage").val(),
			ajaxPost : 'T',
			courseId : $("#courseId").val()
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	});
	
	$("#addFAQBtn").click(function(){
		var courseId = $("#courseId").val();
		var faqName = $("#addFAQ #faqName").val();
		var faqAnswer = $("#addFAQ #faqAnswer").val();
		if(courseId <= 0){
			alertMsg("参数异常，请重试", 'W');
			return;
		}
		if(null == faqName || faqName.trim().length == 0){
			alertMsg("问题名称不能为空", 'W');
			return;
		}
		
		if(null == faqAnswer || faqAnswer.trim().length == 0){
			alertMsg("问题答案不能为空", 'W');
			return;
		}
		
		var $form = $("#MainForm");
		var options = buildAjaxOptions('FAQServlet?method=addFAQ', {
			ajaxPost : 'T',
			courseId: courseId,
			faqName : faqName,
			faqAnswer : faqAnswer
		}, onSuccessCallBack);
		$form.ajaxSubmit(options);
	});
	
	$("#mdfFAQBtn").click(function(){
		var $form = $("#MainForm");
		var faqId = $("#modifyFAQ #faqName").data("id");
		var faqName = $("#modifyFAQ #faqName").val();
		var faqAnswer = $("#modifyFAQ #faqAnswer").val();
		if(faqId <= 0){
			alertMsg("参数异常，请重试", 'W');
			return;
		}
		if(null == faqName || faqName.trim().length == 0){
			alertMsg("问题名称不能为空", 'W');
			return;
		}
		
		if(null == faqAnswer || faqAnswer.trim().length == 0){
			alertMsg("问题答案不能为空", 'W');
			return;
		}
		var options = buildAjaxOptions('FAQServlet?method=modifyFAQ', {
			faqId: faqId,
			faqName : faqName,
			faqAnswer : faqAnswer,
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form) {
			if (resText.type == 'S') {
				$("#showFAQTab").trigger("click");
			}
			ShowMsg(resText);
		});

		$form.ajaxSubmit(options);
	});
});


function _modifyFAQ(obj){
	$("#modifyFAQTab").css("display", "block");
	$("#modifyFAQTab a").tab("show");
	var faqId = $(obj).data("id");
	var faqName = $(obj).data("name");
	var faqAnswer = $(obj).data("answer");
	//传值
	$("#modifyFAQ #faqName").val(faqName).data("id", faqId);
	$("#modifyFAQ #faqAnswer").val(faqAnswer);
}

function _deleteFAQ(obj){
	if (confirm("确认删除?")) {
		var $form = $("#MainForm");
		var faqId = $(obj).data("id");
		var options = buildAjaxOptions('FAQServlet?method=deleteFAQ', {
			faqId : faqId,
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
	var options = buildAjaxOptions('TeacherServlet?method=backOperate&item=FAQ', {
		currPage : pageNo,
		pageSize : $("#pageSize").val(),
		ajaxPost : 'T',
		courseId : $("#courseId").val()
	}, onNextPageSuccess);
	$form.ajaxSubmit(options);
}

//重置内容区
function reBuildPageContent(currPage, pageSize, content){
	
	 var tbody = $("#showFAQ table tbody");
	 //清空内容
	 tbody.empty();
	 $.each(content, function(index, c){
		 var tr = $("<tr></tr>");
		 var trClass = index % 2 == 0? "info" : "";
		 tr.attr("class", trClass);
		 var td1 = $("<td>" + ((currPage-1)*pageSize+index+1) + "</td>");
		 var td2 = $("<td>" + c.name + "</td>");
		 var td3 = $("<td></td>");
		 var modBtn = $("<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"_modifyFAQ(this)\">修改</button>");
		 modBtn.data("id", c.id).data("name", c.name).data("answer", c.answer);
		 var delBtn = $("<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"_deleteFAQ(this)\">删除</button>");
		 delBtn.data("id", c.id);
		 td3.append(modBtn).append(delBtn);
		 tr.append(td1).append(td2).append(td3);
		 tbody.append(tr);
	 });
}