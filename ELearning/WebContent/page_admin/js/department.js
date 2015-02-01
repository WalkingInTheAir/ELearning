$(function() {
	$("#addDepartBtn").click(function() {
		var $form = $("#MainForm");
		var options = buildAjaxOptions('DepartServlet?method=addDepart', 
				$form.formSerialize(), 
				onAddSuccess,
				beforeSubmit);
		$("#MainForm").ajaxSubmit(options);
	});
	
	$("#addDepartsBtn").click(function(){
		var $form = $("#MainForm");
		var options = buildAjaxOptions('DepartServlet?method=addDeparts');
				
	});
});

function onAddSuccess(resText, statusText, xhr, $form){
	if(resText.type=='S'){
		$form.clearForm();
	}
	ShowMsg(resText);
}

function beforeSubmit(){
	//console.log($("#MainForm").length);
}