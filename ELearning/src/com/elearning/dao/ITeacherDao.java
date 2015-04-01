package com.elearning.dao;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.elearning.domain.Teacher;

public interface ITeacherDao {
	public int addTeacher(Teacher t) throws Exception;
	public PageContent<Teacher> getTeachers(String conditions, Object[] params, PageInfo page) throws Exception;
	public int deleteTeacher(int teacherId) throws Exception;
	public int modifyTeacher(Teacher t) throws Exception;
}
