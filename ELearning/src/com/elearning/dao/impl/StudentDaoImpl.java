package com.elearning.dao.impl;

import java.sql.ResultSet;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.jdbc.converter.ConverterFactory;
import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;
import com.elearning.dao.IStudentDao;
import com.elearning.domain.Clas;
import com.elearning.domain.Department;
import com.elearning.domain.Major;
import com.elearning.domain.Student;

public class StudentDaoImpl extends ABaseDao implements IStudentDao{
	
	private IResultSetConverter<Student> conv = new IResultSetConverter<Student>(){

		@Override
		public Student conver(ResultSet rs) throws Exception {
			int clsId = rs.getInt("CLS_ID");
			String className = rs.getString("CLS_NAME");
			int majorId = rs.getInt("MAJOR_ID");
			String majorName = rs.getString("MAJOR_NAME");
			int deptId = rs.getInt("DEPT_ID");
			String deptName = rs.getString("DEPT_NAME");
			int stuId = rs.getInt("STU_ID");
			String stuNum = rs.getString("STU_NUMBER");
			String stuName = rs.getString("STU_NAME");
			Department dept = new Department(deptId, deptName);
			Major major = new Major(majorId, majorName, dept);
			Clas clas = new Clas(clsId, className, major);
			Student stu = new Student();
			stu.setId(stuId);
			stu.setName(stuName);
			stu.setNum(stuNum);
			stu.setClas(clas);
			return stu;
		}
		
	};
	
	@Override
	public int addStudent(Student s) throws Exception {
		String sql = "INSERT INTO TB_STUDENT VALUES(NULL,?,?,?,?)";
		return super.add(sql,
				new Object[] { s.getName(), s.getNum(), s.getPassword(),
						s.getClas().getId() });
	}

	@Override
	public PageContent<Student> getStudents(String conditions, Object[] params,
			PageInfo page) throws Exception {
		StringBuffer sb = new StringBuffer(
				"SELECT * FROM VIEW_STUDENT ");
		if (!StringUtils.isBlank(conditions) && null != params) {
			sb.append(" WHERE ").append(conditions);
		}
		return DBManager.queryByPagination(sb.toString(), params, this.conv, page);
	}

	@Override
	public int deleteStudent(int stuId) throws Exception {
		String sql = "DELETE FROM TB_STUDENT WHERE STU_ID = ?";
		return super.delete(sql, new Object[]{stuId});
	}

	@Override
	public int modifyStudent(Student stu) throws Exception {
		String sql = "UPDATE TB_STUDENT SET STU_NAME = ? WHERE STU_ID = ?";
		return super.update(sql, new Object[]{stu.getName(), stu.getId()});
	}

	@Override
	public boolean isExist(String stuNum) throws Exception {
		String sql = "SELECT COUNT(*) FROM TB_STUDENT WHERE STU_NUMBER = ?";
		return super.queryToBean(sql, new Object[] { stuNum },
				ConverterFactory.getConverter(Integer.class)) > 0;
	}
	
	
	
}
