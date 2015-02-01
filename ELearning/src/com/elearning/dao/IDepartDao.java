package com.elearning.dao;

import com.elearning.domain.Department;

public interface IDepartDao {
	public int addDepart(Department dept) throws Exception;
	public boolean isExist(String deptName) throws Exception;
}
