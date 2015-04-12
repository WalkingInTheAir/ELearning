$(function(){
	//隐藏修改院系tab
	$(".nav li").click(function(){
		if($(this).attr("id") != "mdfTeacherTab"){
			$("#mdfTeacherTab").css("display", "none");
		}
	});
	/**
	 * 添加教师tab
	 */
	$("#addTeacherTab, #showTeacherTab, #mdfTeacherTab").click(function(event, params) {
		var target = $(this).find("a:first");
		target.tab("show");
		var $form = $("#MainForm");
		var deptSelect = $(target.attr("href")).find("#deptName");
		var options = buildAjaxOptions('DepartServlet?method=showPage', {
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form){
			var content = resText.content;
			deptSelect.empty();
			deptSelect.append("<option value=\"-1\">--请选择院系--</option>")
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
	
	/**
	 * 添加教师按钮
	 */
	$("#addTeacherBtn").click(function(){
		var teacherName = $("#addTeacher").find("#teacherName").val();
		var teacherNum = $("#addTeacher").find("#teacherNum").val();
		var deptId = $("#addTeacher").find("#deptName").val();
		
		if(null == teacherName || teacherName.trim().length == 0){
			alertMsg("教师名称不能为空", 'w');
			return ;
		}
		if(null == teacherNum || teacherNum.trim().length == 0){
			alertMsg("教师工号不能为空", 'w');
			return;
		}
		if(deptId < 0){
			alertMsg("请您选择院系", 'w');
			return;
		}
		var $form = $("#MainForm");
		var options = buildAjaxOptions('TeacherServlet?method=addTeacher', {
			ajaxPost : 'T',
			teacherName : teacherName,
			teacherNum : teacherNum,
			deptId : deptId
		}, onSuccessCallBack);
		$form.ajaxSubmit(options);
	});
	
	
	$("#showTeacher").find("select[id='deptName']").change(function(){
		var $form = $("#MainForm");
		var options = buildAjaxOptions('TeacherServlet?method=showPage', {
			deptId : $(this).val(),
			ajaxPost : 'T'
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	});
	
	
	$("#showTeacherTab").click(function() {
		$(this).find("a:first").tab("show");
		var $form = $("#MainForm");
		var options = buildAjaxOptions('TeacherServlet?method=showPage', {
			currPage : $("#currPage").val(),
			ajaxPost : 'T'
		}, onNextPageSuccess);
		$form.ajaxSubmit(options);
	});
	
	$("#mdfTeacherBtn").click(function(){
		var teacherNameObj = $("#mdfTeacher").find("#teacherName");
		var value = teacherNameObj.val();
		var origValue = teacherNameObj.data("origvalue");
		var teacherId = teacherNameObj.data("teacherid");
		if(value.trim().length == 0){
			alertMsg("教师姓名不能为空", 'W');
			return false;
		}
		if(value == origValue){
			alertMsg("您没做任何修改", 'W');
			return false;
		}
		var $form = $("#MainForm");
		var options = buildAjaxOptions('TeacherServlet?method=modifyTeacher', {
			teacherId : teacherId,
			teacherName : value,
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form) {
			if (resText.type == 'S') {
				$("#showTeachersTab").trigger("click");
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
	 var tbody = $("#showTeacher table tbody");
	 //清空内容
	 tbody.empty();
	 $.each(content, function(index, c){
		 var tr = $("<tr></tr>");
		 var trClass = index % 2 == 0? "info" : "";
		 tr.attr("class", trClass);
		 var td1 = $("<td>" + ((currPage-1)*pageSize+index+1) + "</td>");
		 var td2 = $("<td>" + c.dept.name + "</td>");
		 var td3 = $("<td>" + c.num + "</td>");
		 var td4 = $("<td>" + c.name + "</td>");
		 var td5 = $("<td></td>");
		 var modBtn = $("<button type=\"button\" class=\"btn btn-success btn-xs\" onclick=\"_modifyTeacher(this)\">修改</button>");
		 modBtn.data("teacherid", c.id).data("deptid", c.dept.id).data("teachername", c.name).data("teachernum", c.num);
		 var delBtn = $("<button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"_deleteTeacher(this)\">删除</button>");
		 delBtn.data("teacherid", c.id);
		 td5.append(modBtn).append(delBtn);
		 tr.append(td1).append(td2).append(td3).append(td4).append(td5);
		 tbody.append(tr);
	 });
}

//翻页
function pageBarOnClick(pageNo) {
	var $form = $("#MainForm");
	var options = buildAjaxOptions('TeacherServlet?method=showPage', {
		currPage : pageNo,
		pageSize : $("#pageSize").val(),
		deptId : $("#showTeacher").find("select[id='deptName']").val(),
		ajaxPost : 'T'
	}, onNextPageSuccess);
	$form.ajaxSubmit(options);
}

/**
 * 删除teacher
 * @param obj
 */
function _deleteTeacher(obj) {
	if (confirm("确认删除?")) {
		var $form = $("#MainForm");
		var teacherId = $(obj).data("teacherid");
		var options = buildAjaxOptions('TeacherServlet?method=deleteTeacher', {
			teacherId : teacherId,
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
			var $origDeptObj = $(obj).parents(".tab-pane").eq(0).find("#deptName")
			var origDeptValue = $origDeptObj.val();
			onSuccessCallBack(resText, statusText, xhr, $form);
			$origDeptObj.val(origDeptValue);
			onSuccessCallBack(resText, statusText, xhr, $form);
		});
		$form.ajaxSubmit(options);
	}
}

function _modifyTeacher(obj){
	$("#mdfTeacherTab").css("display", "block");
	$("#mdfTeacherTab a").tab("show");
	$("#mdfTeacherTab").trigger("click", {options:{async: false}});
	//取值
	var deptId = $(obj).data("deptid");
	var teacherId = $(obj).data("teacherid");
	var teacherName = $(obj).data("teachername");
	var teacherNum = $(obj).data("teachernum");
	//赋值
	var teacherNameObj = $("#mdfTeacher").find("#teacherName");
	teacherNameObj.val(teacherName).data("origvalue", teacherName).data("teacherid", teacherId);
	var teacherNumObj = $("#mdfTeacher").find("#teacherNum");
	teacherNumObj.val(teacherNum);
	var deptNameObj = $("#mdfTeacher").find("#deptName");
	deptNameObj.val(deptId);
}
