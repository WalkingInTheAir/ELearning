package com.elearning.service;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.elearning.domain.CourseWare;

public interface ICourseWareService {

	public PageContent<CourseWare> showCourseWares(PageInfo page, int courseId);
	
	public ResultMessage addCourseWare(CourseWare cw);

	public CourseWare getCourseWareById(int id);

	public ResultMessage delete(int cwId);
}
