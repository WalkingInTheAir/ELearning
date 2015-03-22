package com.elearning.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.jdbc.converter.ConverterFactory;
import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;
import com.elearning.dao.IDepartDao;
import com.elearning.domain.Department;

public class DepartDaoImpl extends ABaseDao implements IDepartDao {
	
	private IResultSetConverter<Department> conv = new IResultSetConverter<Department>() {
		@Override
		public Department conver(ResultSet rs) throws Exception {
			Department dept = new Department();
			dept.setId(rs.getInt("DEPT_ID"));
			dept.setName(rs.getString("DEPT_NAME"));
			return dept;
		}
	};
	@Override
	public int addDepart(Department dept) throws Exception {
		/*
		 * return DBManager.executeUpdate(
		 * "INSERT INTO TB_DEPARTMENT VALUES(NULL,?)", new Object[] {
		 * dept.getName() });
		 */
		return super.add("INSERT INTO TB_DEPARTMENT VALUES(NULL,?)",
				new Object[] { dept.getName() });
	}

	@Override
	public boolean isExist(String deptName) throws Exception {
		/*
		 * return DBManager.queryToBean(
		 * "SELECT COUNT(*) FROM TB_DEPARTMENT WHERE NAME=?", new Object[] {
		 * deptName }, ConverterFactory.getConverter(Integer.class) ) > 0;
		 */
		return super.queryToBean(
				"SELECT COUNT(*) FROM TB_DEPARTMENT WHERE DEPT_NAME=?",
				new Object[] { deptName },
				ConverterFactory.getConverter(Integer.class)) > 0;
	}

	@Override
	public boolean addDepats(List<Object[]> deptNames) throws Exception {
		return DBManager.executeBatch(
				"INSERT INTO TB_DEPARTMENT VALUES(NULL, ?)", deptNames);
	}

	@Override
	public PageContent<Department> queryDepts(PageInfo page) throws Exception {
		String sql = "SELECT * FROM TB_DEPARTMENT";
		return DBManager.queryByPagination(sql, this.conv, page);
	}

	@Override
	public boolean deleteDepts(int deptId) throws Exception {
		/*
		 * String sql = "DELETE FROM TB_DEPARTMENT WHERE DEPT_ID = ?"; int result =
		 * DBManager.executeUpdate(sql, new Object[] { deptId }); return result
		 * > 0;
		 */
		return super.delete("DELETE FROM TB_DEPARTMENT WHERE DEPT_ID = ?",
				new Object[] { deptId }) > 0;
	}

	@Override
	public boolean updateDept(Department dept) throws Exception {
		/*
		 * String sql = "UPDATE TB_DEPARTMENT SET NAME=? WHERE ID=?"; return
		 * false;
		 */
		return super.update("UPDATE TB_DEPARTMENT SET DEPT_NAME=? WHERE DEPT_ID=?",
				new Object[] { dept.getName(), dept.getId() }) > 0;
	}

	@Override
	public List<Department> queryDeparts(String conditions, Object[] params)
			throws Exception {
		StringBuffer sb = new StringBuffer("SELECT * FROM TB_DEPARTMENT ");
		if (!StringUtils.isBlank(conditions) && null != params) {
			sb.append(conditions);
		}
		return super.queryToList(sb.toString(), params, this.conv);
	}
}
