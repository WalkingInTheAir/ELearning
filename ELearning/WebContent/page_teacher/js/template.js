$(function(){
	console.log("menuIndex:" + menuIndex + ",itemIndex:" + itemIndex + ", tabIndex:" + tabIndex);
	//为当前激活的menu添加样式
	if(menuIndex > -1 && itemIndex > -1){
		var $currMenu = $("#sideMenu").find("._navlist").eq(menuIndex);
		var $currItem = $currMenu.children("li").eq(itemIndex);
		$currMenu.addClass("in");
		$currItem.addClass("active");
		$currMenu.prev().children().toggleClass("glyphicon-chevron-up", 1000)
		.toggleClass("glyphicon-chevron-down", 1000);
		
		//为隐藏栏位赋值
		$("#menuIndex").val(menuIndex);
		$("#itemIndex").val(itemIndex);
		$("#include").val(includePage);
		$("#courseId").val($currMenu.data("courseid"));
	}
	
	$("#sideMenu ul li a").bind("click", function(event){
		var obj = $(this);
		var target = obj.attr("target");
		//记录当前点击菜单的位置，方便添加样式
		var menuIndex = obj.parents(".panel").index();
		var itemIndex = obj.parent().index();
		var item = obj.data("item");
		var courseId = obj.parents("ul:first").data("courseid");
		//请求页面
		var include = obj.attr("href").replace("#","");
		var $form = $("#MenuForm");
		var url = "TrxManager";
		//为隐藏栏位复制
		$("#menuIndex").val(menuIndex);
		$("#itemIndex").val(itemIndex);
		$("#item").val(item);
		$("#courseId").val(courseId);
		$("#include").val(include+"?method=backOperate");
		$("#target").val(target);
		$form.attr("method", "post");
		$form.attr("action", encodeURI(url));
		$form.submit();
		return false;//阻止默认事件
	});
});