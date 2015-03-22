$(function() {
	//添加院系按钮
	$("#addDepartBtn").click(function() {
		var $form = $("#MainForm");
		var options = buildAjaxOptions('DepartServlet?method=addDepart', 
				$form.formSerialize(), 
				onSuccessCallBack,
				beforeSubmit);
		$form.ajaxSubmit(options);
	});
	
	/**
	 * 批量添加院系
	 */
	$("#addDepartsBtn").click(function(){
		var $form = $("#MainForm");
		var options = buildAjaxOptions('DepartServlet?method=addDeparts',
				$form.formSerialize(),
				onSuccessCallBack);
		
		$form.ajaxSubmit(options);
	});
	
	/**
	 * 点击院系信息Tab
	 */
	$("#showDepartsTab").click(function() {
		$(this).find("a:first").tab("show");
		var $form = $("#MainForm");
		var options = buildAjaxOptions('DepartServlet?method=showPage', {
			currPage : $("#currPage").val(),
			ajaxPost : 'T'
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	});
	
	//隐藏修改院系tab
	$(".nav li").click(function(){
		if($(this).attr("id") != "mdfDepartTab"){
			$("#mdfDepartTab").css("display", "none");
		}
	});
	
	//修改院系信息
	$("#mdfDepartBtn").click(function(){
		var $form = $("#MainForm");
		var deptId = $("#mdf_departId").val();
		var deptName = $("#mdf_departName").val().trim();
		var origName = $("#mdf_departName").data("origname");
		if(deptName == origName){
			ShowMsg({type:"W", msg:"与原信息一致，请修改", title: "提示"});
			return false;
		}
		var options = buildAjaxOptions('DepartServlet?method=modifyDept',
				{deptId: deptId, deptName: deptName, ajaxPost : 'T'},
				onMdfSuccessCallBack);
		
		$form.ajaxSubmit(options);
	});
});

/**
 * 修改院系信息
 */
function _modifyDept(obj){
	$("#mdfDepartTab").css("display", "block");
	$("#mdfDepartTab a").tab("show");
	var deptId = $(obj).data("deptid");
	var deptName = $(obj).data("deptname");
	//传值
	$("#mdf_departId").val(deptId);
	$("#mdf_departName").val(deptName);
	$("#mdf_departName").data("origname", deptName);
}

function _deleteDept(obj) {
	if (confirm("确认删除?")) {
		var $form = $("#MainForm");
		var deptId = $(obj).data("deptid");
		var options = buildAjaxOptions('DepartServlet?method=deleteDept', {
			deptId : deptId,
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
//修改院系信息成功回调函数
function onMdfSuccessCallBack(resText, statusText, xhr, $form){
	if(resText.type=='S'){
		$("#showDepartsTab").trigger("click");
	}
	ShowMsg(resText);
}
function beforeSubmit(){
	//console.log($("#MainForm").length);
}

//翻页
function pageBarOnClick(pageNo) {
	var $form = $("#MainForm");
	var options = buildAjaxOptions('DepartServlet?method=showPage', {
		currPage : pageNo,
		pageSize : $("#pageSize").val(),
		ajaxPost : 'T'
	}, onNextPageSuccess);
	$form.ajaxSubmit(options);
}

//重置内容区
function reBuildPageContent(currPage, pageSize, content){
	
	 var tbody = $("#showDeparts table tbody");
	 //清空内容
	 tbody.empty();
	 $.each(content, function(index, c){
		 var tr = $("<tr></tr>");
		 var trClass = index % 2 == 0? "info" : "";
		 tr.attr("class", trClass);
		 var td1 = $("<td>" + ((currPage-1)*pageSize+index+1) + "</td>");
		 var td2 = $("<td>" + c.name + "</td>");
		 var td3 = $("<td></td>");
		 var modBtn = $("<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"_modifyDept(this)\">修改</button>");
		 modBtn.data("deptid", c.id).data("deptname", c.name);
		 var delBtn = $("<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"_deleteDept(this)\">删除</button>");
		 delBtn.data("deptid", c.id);
		 td3.append(modBtn).append(delBtn);
		 tr.append(td1).append(td2).append(td3);
		 tbody.append(tr);
	 });
}