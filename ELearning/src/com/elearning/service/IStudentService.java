package com.elearning.service;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.elearning.domain.Student;

public interface IStudentService {

	public ResultMessage addStudent(Student stu);

	public boolean isExist(Student stu);

	public PageContent<Student> showStudents(String conditions,
			Object[] params, PageInfo page);

	public ResultMessage deleteStudent(int stuId);
	
	public ResultMessage modifyStudent(Student stu);
}
