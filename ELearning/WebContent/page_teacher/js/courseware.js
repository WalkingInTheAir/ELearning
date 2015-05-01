$(function(){
	$("#addCourseWareBtn").click(function(){
		if($("#courseware").val().length < 1){
			alertMsg("请选择文件", 'W');
			return;
		}
		var $form = $("#MainForm");
		$form.append("<input type='hidden' name='courseId' value='" + $("#courseId").val() + "' />");
		var options = buildAjaxOptions('CourseWareServlet?method=addCourseWare',
				$form.formSerialize(),
				onSuccessCallBack);
		
		$form.ajaxSubmit(options);
	});
	
	$("#showCourseWareTab").click(function() {
		$(this).find("a:first").tab("show");
		var $form = $("#MainForm");
		var options = buildAjaxOptions('TeacherServlet?method=backOperate&item=courseware', {
			ajaxPost : 'T',
			courseId : $("#courseId").val()
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	});
});

function _download(obj){
	var cwId = $(obj).data("id");
	window.open("CourseWareServlet?method=download&courseWareId=" + cwId);
}

function _delete(obj){
	if (confirm("确认删除?")) {
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseWareServlet?method=delete', {
			ajaxPost : 'T',
			courseWareId : $(obj).data("id")
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

function reBuildPageContent(currPage, pageSize, content){
	 var tbody = $("#showCourseWare table tbody");
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
		 var td2 = $("<td>" + c.origName + "</td>");
		 var td3 = $("<td></td>");
		 var modBtn = $("<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"_download(this)\">下载</button>");
		 modBtn.data("id", c.id);
		 var delBtn = $("<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"_delete(this)\">删除</button>");
		 delBtn.data("id", c.id);
		 td3.append(modBtn).append(delBtn);
		 tr.append(td1).append(td2).append(td3);
		 tbody.append(tr);
	 });
}

function pageBarOnClick(pageNo) {

	var $form = $("#MainForm");
	var options = buildAjaxOptions('TeacherServlet?method=backOperate&item=courseware', {
		courseId : $("#courseId").val(),
		ajaxPost : 'T'
	}, onNextPageSuccess);
	$form.ajaxSubmit(options);
}