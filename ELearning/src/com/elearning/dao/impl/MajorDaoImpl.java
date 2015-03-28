package com.elearning.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.jdbc.converter.ConverterFactory;
import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;
import com.elearning.dao.IMajorDao;
import com.elearning.domain.Department;
import com.elearning.domain.Major;

public class MajorDaoImpl extends ABaseDao implements IMajorDao {
	
	private IResultSetConverter<Major> conv = new IResultSetConverter<Major>(){
		@Override
		public Major conver(ResultSet rs) throws Exception {
			Major m = new Major();
			int id = rs.getInt("MAJOR_ID");
			String name = rs.getString("MAJOR_NAME");
			int deptId = rs.getInt("DEPT_ID");
			String deptName = rs.getString("DEPT_NAME");
			Department dept = new Department(deptId, deptName);
			m.setDept(dept);
			m.setId(id);
			m.setName(name);
			return m;
		}
	};
	
	@Override
	public boolean addMajor(Major m) throws Exception {
		String sqlStr = "INSERT INTO TB_MAJOR VALUES(NULL,?, ?)";
		return super.add(sqlStr, new Object[] { m.getName(),
				m.getDept().getId() }) > 0;
	}

	@Override
	public boolean deleteMajor(int id) throws Exception {
		String sqlStr = "DELETE FROM TB_MAJOR WHERE MAJOR_ID = ?";
		return super.delete(sqlStr, new Object[] { id }) > 0;
	}

	@Override
	public boolean updateMajor(Major m) throws Exception {
		String sqlStr = "UPDATE TB_MAJOR SET MAJOR_NAME = ?, F_DEPT_ID = ? WHERE MAJOR_ID = ?";
		return super.delete(sqlStr, new Object[] { m.getName(),
				m.getDept().getId(), m.getId() }) > 0;
	}

	@Override
	public boolean isExist(Major m) throws Exception {
		String sqlStr = "SELECT COUNT(*) FROM TB_MAJOR WHERE MAJOR_NAME = ? AND DEPT_ID = ?";
		return super.queryToBean(sqlStr, new Object[] { m.getName(),
				m.getDept().getId() },
				ConverterFactory.getConverter(Integer.class)) > 0;
	}


	@Override
	public PageContent<Major> getMajors(String condition, Object[] params,
			PageInfo pageInfo) throws Exception {
		StringBuffer sb = new StringBuffer(
				"SELECT * FROM TB_MAJOR m, TB_DEPARTMENT d ");
		if (!StringUtils.isBlank(condition) && null != params) {
			sb.append(" WHERE ").append(condition);
		}
		if (sb.indexOf("WHERE") < 0) {
			sb.append(" WHERE ");
		}else{
			sb.append(" AND ");
		}
		sb.append(" m.F_DEPT_ID = d.DEPT_ID");
		return DBManager.queryByPagination(sb.toString(), params, this.conv, pageInfo);
	}

	@Override
	public List<Major> getMajorsToList(String condition, Object[] params)
			throws Exception {
		StringBuffer sb = new StringBuffer("SELECT * FROM TB_MAJOR m, TB_DEPARTMENT d ");
		if (!StringUtils.isBlank(condition) && null != params) {
			sb.append(" WHERE ").append(condition);
		}
		if (sb.indexOf("WHERE") < 0) {
			sb.append(" WHERE ");
		}else{
			sb.append(" AND ");
		}
		sb.append(" m.F_DEPT_ID = d.DEPT_ID");
		
		return super.queryToList(sb.toString(), params, this.conv);
	}

}
