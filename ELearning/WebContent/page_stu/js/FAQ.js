$(function() {
	$(".viewAnswer").click(function() {
		var target = $(this).parent().find(".faqAnswerDiv");
		if (target.is(":hidden")) {
			$(this).text("隐藏答案");
			target.slideDown();
		} else {
			$(this).text("显示答案");
			target.slideUp();
		}
	});
});