function _download(obj){
	var cwId = $(obj).data("id");
	window.open("CourseWareServlet?method=download&courseWareId=" + cwId);
}