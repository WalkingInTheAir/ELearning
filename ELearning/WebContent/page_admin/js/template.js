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
	}
	
	if(typeof(tabIndex) != 'undefined'){
		$("#_content").find(".nav").children("li").eq(tabIndex).addClass("active");
	}
	//控制菜单上的箭头
	$("._navTitle").click(function(){
		//兄弟节点
		$(this).parent().siblings().each(function(i, obj){
			$(obj).find(".glyphicon")
			.toggleClass("glyphicon-chevron-up", true, 1000)
			.toggleClass("glyphicon-chevron-down", false, 1000);
		});
		//自己
		$(this).find(".glyphicon")
			.toggleClass("glyphicon-chevron-up" , 1000)
			.toggleClass("glyphicon-chevron-down", 1000);
	});
	
	
	//点击子菜单
	$("#sideMenu ul li a").bind("click", function(event){
		var obj = $(this);
		var target = obj.attr("target");
		//记录当前点击菜单的位置，方便添加样式
		var menuIndex = obj.parents(".panel").index();
		var itemIndex = obj.parent().index();
		//请求页面
		var include = obj.attr("href").replace("#","");
		var $form = $("#MenuForm");
		var url = "TrxManager";
		//为隐藏栏位复制
		$("#menuIndex").val(menuIndex);
		$("#itemIndex").val(itemIndex);
		$("#include").val(include);
		$("#target").val(target);
		$form.attr("method", "post");
		$form.attr("action", encodeURI(url));
		$form.submit();
		return false;//阻止默认事件
	});
});

