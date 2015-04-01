package com.elearning.service;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.elearning.domain.Teacher;

public interface ITeacherService {
	public ResultMessage addTeacher(Teacher t);
	public PageContent<Teacher> getTeachers(String conditions, Object[] params, PageInfo page);
	public ResultMessage delteTeacher(int teacherId);
	public ResultMessage modifyTeacher(Teacher t);
}
