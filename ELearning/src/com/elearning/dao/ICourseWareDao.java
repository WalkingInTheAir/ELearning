package com.elearning.dao;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.elearning.domain.CourseWare;

public interface ICourseWareDao {
	public int add(CourseWare cw) throws Exception;
	public PageContent<CourseWare> query(PageInfo page, int courseId) throws Exception;
	public boolean delete(int cwId) throws Exception;
	public CourseWare getCourseWareById(int id) throws Exception;
}
