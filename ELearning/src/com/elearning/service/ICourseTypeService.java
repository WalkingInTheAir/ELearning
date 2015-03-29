package com.elearning.service;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.elearning.domain.CourseType;

public interface ICourseTypeService {
	
	public boolean isExist(String conditions, Object[] params);
	
	public ResultMessage addType(CourseType type);
	
	public PageContent<CourseType> showTypes(PageInfo page);

	public ResultMessage modifyType(CourseType type);

	public ResultMessage deleteType(int typeId);
}
