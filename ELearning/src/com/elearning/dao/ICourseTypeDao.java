package com.elearning.dao;

import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.elearning.domain.CourseType;

public interface ICourseTypeDao {
	public int addType(CourseType type) throws Exception;
	public int deleteType(int typeId) throws Exception;
	public int modifyType(CourseType type) throws Exception;
	public List<CourseType> queryList(String conditions, Object[] params) throws Exception;
	public CourseType getCourseType(String conditions, Object[] params) throws Exception;
	public PageContent<CourseType> getPage(PageInfo page) throws Exception;
}