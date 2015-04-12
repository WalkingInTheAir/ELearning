package com.elearning.dao;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.elearning.domain.Student;

public interface IStudentDao {
	
	public int addStudent(Student s) throws Exception;
	public PageContent<Student> getStudents(String conditions, Object[] params, PageInfo page) throws Exception;
	public int deleteStudent(int stuId) throws Exception;
	public int modifyStudent(Student stu) throws Exception;
	public boolean isExist(String stuNum) throws Exception;
}
