function onSuccessCallBack(resText, statusText, xhr, $form){
	if(resText.type=='S'){
		$form.resetForm();
	}
	ShowMsg(resText);
}

function buildAjaxOptions(url, data, onSuccess, beforeSubmit, dataType, type,
		onError, onUploadProgress) {
	var options = {
		url : undefined2Default(url),
		type : undefined2Default(type, 'POST'),
		dataType : undefined2Default(dataType, 'JSON'),
		data : data,
		beforeSubmit : isCallBackFunction(beforeSubmit),
		success : isCallBackFunction(onSuccess),
		error : isCallBackFunction(onSuccess),
		onUploadProgress : isCallBackFunction(onUploadProgress),
	}
	return options;
}

function undefined2Default(name, defName) {
	defName = typeof (defName) == 'undefined' ? '' : defName;
	return typeof (name) == 'undefined' ? defName : name;
}
function isFunction(funcName) {
	return typeof (funcName) == 'function';
}

function isCallBackFunction(funcName) {
	return isFunction(funcName) ? funcName : null;
}
function genFormHiddenField(name, value) {
	return genFormField(name, value, true);
}
function genFormField(name, value, isHidden, isDisabled) {
	var $input = $("<input/>");
	$input.attr("name", name).attr("value", value);
	if (isHidden) {
		$input.attr("type", "hidden");
	} else {
		$input.attr("type", "text");
	}
	if (isDisabled) {
		$input.attr("disabled", "disabled");
	}

	return $input;
}