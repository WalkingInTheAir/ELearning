$(function(){
	
	$("#addCourseTab").click(function(){
		$(this).find("a:first").tab("show");
		var $form = $("#MainForm");
		var typeSelect = $("#addCourse").find("#typeName");
		var options = buildAjaxOptions('CourseTypeServlet?method=showPage', {
			ajaxPost : 'T'
		}, function(resText, statusText, xhr, $form){
			var content = resText.content;
			typeSelect.empty();
			typeSelect.append("<option value=\"-1\">--请选择类型--</option>")
			$.each(content, function(index, c){
				var option = $("<option></option>");
				option.val(c.id).text(c.name);
				typeSelect.append(option);
			});
		});
		$form.ajaxSubmit(options);
	});
	
	$("#addCourse #typeName").change(function(){
		var typeId = $(this).val();
		var typeName = $(this).text();
		
	});
});