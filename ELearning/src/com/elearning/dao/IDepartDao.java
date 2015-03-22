package com.elearning.dao;

import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.elearning.domain.Department;

public interface IDepartDao {
	public int addDepart(Department dept) throws Exception;
	public boolean isExist(String deptName) throws Exception;
	boolean addDepats(List<Object[]> deptNames) throws Exception;
	public PageContent<Department> queryDepts(PageInfo page) throws Exception;
	public boolean deleteDepts(int deptId) throws Exception;
	public boolean updateDept(Department dept) throws Exception;
	public List<Department> queryDeparts(String conditions, Object[] params) throws Exception;
}
