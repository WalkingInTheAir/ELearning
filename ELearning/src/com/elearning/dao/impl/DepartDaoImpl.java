package com.elearning.dao.impl;

import com.core.jdbc.converter.ConverterFactory;
import com.core.jdbc.helper.DBManager;
import com.elearning.dao.IDepartDao;
import com.elearning.domain.Department;

public class DepartDaoImpl implements IDepartDao {

	@Override
	public int addDepart(Department dept) throws Exception {
		return DBManager.executeUpdate(
				"INSERT INTO TB_DEPARTMENT VALUES(NULL,?)",
				new Object[] { dept.getName() });
	}

	@Override
	public boolean isExist(String deptName) throws Exception {
		return DBManager.queryToBean(
					"SELECT COUNT(*) FROM TB_DEPARTMENT WHERE NAME=?",
					new Object[] { deptName },
					ConverterFactory.getConverter(Integer.class)
				) > 0;
	}

}
