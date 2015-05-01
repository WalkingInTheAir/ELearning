package com.elearning.dao;

import java.util.List;
import java.util.Set;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.jdbc.converter.IResultSetConverter;
import com.elearning.domain.Course;
import com.elearning.domain.Department;


public interface ICourseDao {
	
	public int addCourse(Course c, List<Integer> clsIds) throws Exception;
	
	public boolean isExist(Course c) throws Exception;

	public PageContent<Course> queryCourses(String rltTabName, String preName, String conditions, Object[] params,
			PageInfo page) throws Exception;

	public int deleteCourse(int courseId) throws Exception;

	public int updateCourse(String updateCol, Object[] params) throws Exception;

	public List<Department> getCdtClasses(int courseId) throws Exception;
	
	public int updateClassCourseTeacher(int courseId, int teacherId, Set<Integer> classIds) throws Exception;

	public PageContent<Course> queryClassCourses(int clsId, PageInfo p) throws Exception;

	public int updateCourseTeacher(int classId, int courseId, int teacherId) throws Exception;
	
	public List<Course> queryCourses(String sql, Object[] params, IResultSetConverter<Course> converter) throws Exception;
	
	public Course getCourseById(int courseId) throws Exception;
}
