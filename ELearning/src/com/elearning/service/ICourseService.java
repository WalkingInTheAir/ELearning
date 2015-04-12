package com.elearning.service;


import java.util.List;
import java.util.Set;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.elearning.domain.Course;
import com.elearning.domain.Department;

public interface ICourseService {

	public PageContent<Course> showCourses(PageInfo page, String strTypeId, String strRelativeId, String courseName);
	
	public ResultMessage deleteCourseById(int courseId);

	public ResultMessage mdfCourse(Course c);

	public ResultMessage addCourse(Course c, String conditions, Object[] params);

	public List<Department> getCdtClasses(int courseId);

	public ResultMessage assignTeacher(int courseId, int teacherId, Set<Integer> classIdList);

	public PageContent<Course> getClassCourses(int clsId, PageInfo p);

	public ResultMessage changeCourseTeacher(int classId, int courseId, int teacherId);
	
	//public List<Course> queryToList(String conditions, Object[] params);
}
