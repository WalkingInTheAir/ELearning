//$(function(){
	
	$("#pageBar a").bind("click", function(event){
		return barOnClick(event, $(this));
	});
	
//});


function barOnClick(event, obj){
	//event.stopPropagation();
	//获取点击的页码
	var pageNo = obj.data("pageno");
	if(pageNo){
		//alert(pageNo);
		//return false;
		if(pageNo != undefined){
			//接口方法，具体功能具体实现
			if(typeof(pageBarOnClick) == "function"){
				pageBarOnClick(pageNo);
			}
		}
		return false;
	}
	return false;
}

//翻页成功的回调函数
function onNextPageSuccess(resText, statusText, xhr, $form){
	var pageInfo = resText.pageInfo;
	var currPage = parseInt(pageInfo.currPage);
	var pageSize = parseInt(pageInfo.pageSize);
	var totalPage = parseInt(pageInfo.totalPage);
	var content = resText.content;
	//更新隐藏域current page 和 page size
	$("#currPage").val(currPage);
	$("#pageSize").val(pageSize);
	//重置翻页栏
	reBuildPageBar(currPage, totalPage);
	//添加接口方法
	if(typeof reBuildPageContent == 'function'){
		reBuildPageContent(currPage, pageSize, content);
	}
}

/**
 * 翻页后修改翻页栏
 * @param currPage
 * @param totalPage
 */
function reBuildPageBar(currPage, totalPage){
	
	if(totalPage <= 0){
		$("#pageBar").hide();
		return;
	}
	if($("#pageBar").is(":hidden")){
		$("#pageBar").show();
	}
	
	currPage = parseInt(currPage);
	totalPage = parseInt(totalPage);
	//当前页与总页数信息
	$("#currInTotal a").text(currPage + "/" + totalPage);
	//首页
	$("#indexBar a").data("pageno", 1);
	//上一页
	$("#preBar a").data("pageno", currPage-1);
	//下一页
	$("#nextBar a").data("pageno", currPage+1);
	//尾页
	$("#lastBar a").data("pageno", totalPage);
	//当前页栏
	var currPageBar = $(".currPageBar:first");
	//具体可选页码栏
	var availableBars = $(".availableBar");
	
	var lastPageNo = parseInt(currPageBar.find("a:first").data("pageno"));
	var lastPageNoIndex = availableBars.index(currPageBar);
	currPageBar.find("a:first-child").data("pageno", currPage).text(currPage);
	
	//可选页面长度
	var barLength = parseInt($(".pagination:first").data("barlength"));
	//开始页
	var startPageNo = currPage - (barLength - 1) / 2;
	//1是最小页码
	if(startPageNo < 1){
		startPageNo = 1;
	}
	//左跨度
	var leftSpan = currPage - startPageNo;
	
	//结束页
	var endPageNo = currPage + (barLength - 1) / 2;
	//总页数为最大页码
	if(endPageNo > totalPage){
		endPageNo = totalPage;
	}
	//右跨度
	var rightSpan = endPageNo - currPage;
	//当前页栏在总页栏中的下标
	var currIndex = availableBars.index(currPageBar);
	//起始下标
	var startIndex = currIndex - leftSpan;
	//结束下标
	var endIndex = currIndex + rightSpan;
	//左半部分
	for(var i = temp = startIndex; i < currIndex || temp > 0; i++){
		var bar = null;
		if(i < 0){	//需新增页栏
			//生成新的结点
			bar = $("<li class=\"availableBar\"><a data-pageno=\"\" href=\"#\"></a></li>");
			//绑定事件
			bar.find("a:first").bind("click", function(event){
				return barOnClick(event, $(this));
			});
			//插入
			availableBars.eq(0).before(bar);
		}else{
			if(temp > 0){	//有剩余结点，依次删除
				availableBars.eq(--temp).remove();
			}
			//使用现有的结点
			bar = availableBars.eq(i);
		}
		//赋值
		bar.find("a:first-child").data("pageno", startPageNo).text(startPageNo++);
	}
	//右半部分
	for(var i = temp = endIndex; i > currIndex || temp < availableBars.length - 1; i--){
		var bar = null;
		if(i > availableBars.length - 1){	//结点不够，需生成新结点
			//生成新结点
			bar = $("<li class=\"availableBar\"><a data-pageno=\"\" href=\"#\"></a></li>");
			//绑定事件
			bar.find("a:first").bind("click", function(event){
				return barOnClick(event, $(this));
			});
			//追加
			availableBars.last().after(bar);
		}else{
			if(temp < availableBars.length - 1){	//结点过多，需移除部分
				temp++;
				//移除结点
				availableBars.eq(availableBars.length-(temp-endIndex)).remove();
			}
			//使用现有结点
			bar = availableBars.eq(i);
		}
		//赋值
		bar.find("a:first-child").data("pageno", endPageNo).text(endPageNo--);
	}
}