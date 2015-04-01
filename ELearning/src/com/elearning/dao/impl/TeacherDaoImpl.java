package com.elearning.dao.impl;

import java.sql.ResultSet;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;
import com.elearning.dao.ITeacherDao;
import com.elearning.domain.Department;
import com.elearning.domain.Teacher;

public class TeacherDaoImpl extends ABaseDao implements ITeacherDao{

	private IResultSetConverter<Teacher> conv = new IResultSetConverter<Teacher>(){
		@Override
		public Teacher conver(ResultSet rs) throws Exception {
			Teacher t = new Teacher();
			t.setId(rs.getInt("TEACHER_ID"));
			t.setNum(rs.getString("TEACHER_NUMBER"));
			t.setName(rs.getString("TEACHER_NAME"));
			Department dept = new Department();
			dept.setId(rs.getInt("F_DEPT_ID"));
			dept.setName(rs.getString("DEPT_NAME"));
			t.setDept(dept);
			return t;
		}
	};
	@Override
	public int addTeacher(Teacher t) throws Exception {
		String sqlStr = "INSERT INTO TB_TEACHER VALUES(NULL,?,?,?,?)";
		return super.add(sqlStr,
				new Object[] { t.getNum(), t.getName(), t.getNum(),
						t.getDept().getId() });
	}

	@Override
	public PageContent<Teacher> getTeachers(String conditions, Object[] params,
			PageInfo page) throws Exception {
		StringBuffer sb = new StringBuffer(
				"SELECT * FROM TB_TEACHER T, TB_DEPARTMENT D ");
		if (!StringUtils.isBlank(conditions) && null != params) {
			sb.append(" WHERE ").append(conditions);
		}
		if (sb.indexOf("WHERE") < 0) {
			sb.append(" WHERE ");
		}else{
			sb.append(" AND ");
		}
		sb.append(" T.F_DEPT_ID = D.DEPT_ID");
		return DBManager.queryByPagination(sb.toString(), params, this.conv, page);
	}

	@Override
	public int deleteTeacher(int teacherId) throws Exception {
		String sql = "DELETE FROM TB_TEACHER WHERE TEACHER_ID = ?";
		return super.delete(sql, new Object[]{teacherId});
	}

	@Override
	public int modifyTeacher(Teacher t) throws Exception {
		String sql = "UPDATE TB_TEACHER SET TEACHER_NAME = ? WHERE TEACHER_ID = ?";
		return super.update(sql, new Object[]{t.getName(), t.getId()});
	}

}
