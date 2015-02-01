var lastClass;
var ShowMsg = function(jsonData) {
	// var json=jQuery.parseJSON(jsonData);
	var $showMsgBox = $("#ShowMsgBox");
	var msgClass = getMsgClass(jsonData.type);
	var title = jsonData.title || '提示';
	var content = jsonData.msg;
	if ($showMsgBox.length > 0) {
		$showMsgBox.removeClass(lastClass).addClass(msgClass);
		$showMsgBox.children('.ShowMsg-title').text(title);
		$showMsgBox.children('p').text(content);
	} else { // 不存在
		$showMsgBox = $(
				"<div id='ShowMsgBox' class='ShowMsg' style='display:none;'></div>")
				.addClass(msgClass);
		var $close = $("<span class='ShowMsg-close'>×</span>");
		var $title = $("<span class='ShowMsg-title'></span>").text(title);
		var $content = $("<p></p>").text(content);
		$showMsgBox.append($close).append($title).append($content);
		$("body").append($showMsgBox);
	}
	lastClass = msgClass;
	$showMsgBox.slideDown('fast');
	$(".ShowMsg-close").click(function() {
		$(".ShowMsg").slideUp('fast', function() {
			$(".ShowMsg").removeClass(msgClass);
		});
	});
};
  
  function getMsgClass(clsFlag){
	  var cls;
	  switch(clsFlag){
	  case 'S':cls='ShowMsg-success';break;
	  case 'F':cls='ShowMsg-fail';break;
	  case 'W':cls='ShowMsg-warning';break;
	  default:
		  cls="ShowMsg-info";
	  }
	  return cls;
  }
