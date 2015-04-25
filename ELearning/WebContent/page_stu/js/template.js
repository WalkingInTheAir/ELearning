$(function(){
	
	console.log("menuIndex:" + menuIndex);
	if(menuIndex < 0){
		menuIndex = 0;
	}
	//为当前激活的menu添加样式
	var $currMenu = $("#sideMenu ul").find("li").eq(menuIndex);
	$currMenu.addClass("active");
	
	$("#sideMenu ul li a").bind("click", function(event){
		var obj = $(this);
		var target = obj.attr("target");
		//记录当前点击菜单的位置，方便添加样式
		var menuIndex = obj.parents("li").index();
		var item = obj.data("item");
		//请求页面
		var include = obj.attr("href").replace("#","");
		var $form = $("#MenuForm");
		var url = "TrxManager";
		//为隐藏栏位复制
		$("#menuIndex").val(menuIndex);
		$("#item").val(item);
		$("#include").val(include);
		$("#target").val(target);
		$form.attr("method", "post");
		$form.attr("action", encodeURI(url));
		$form.submit();
		return false;//阻止默认事件
	});
});