$(function(){
	
	
	$("#mdfDescBtn").click(function(){
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseServlet?method=modifyCourse', {
			ajaxPost : 'T',
			courseDesc : descUE.getContent(),
			courseId : $("#courseId").val()
		}, function(resText, statusText, xhr, $form) {
			if (resText.type == 'S') {
				ShowMsg(resText);
				$("#sideMenu").find("._navlist").eq(menuIndex).children("li").eq(itemIndex).find("a:first").trigger("click");
			}
		});
		$form.ajaxSubmit(options);
	});
	
	$("#mdfOutlineBtn").click(function(){
		var $form = $("#MainForm");
		var options = buildAjaxOptions('CourseServlet?method=modifyCourse', {
			ajaxPost : 'T',
			courseOutline : outlineUE.getContent(),
			courseId : $("#courseId").val()
		}, function(resText, statusText, xhr, $form) {
			if (resText.type == 'S') {
				$("#sideMenu").find("._navlist").eq(menuIndex).children("li").eq(itemIndex).find("a:first").trigger("click");
			}
			ShowMsg(resText);
		});
		$form.ajaxSubmit(options);
	});
});
