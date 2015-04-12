package com.elearning.dao.impl;

import com.core.jdbc.converter.ConverterFactory;
import com.elearning.dao.IUserDao;
import com.elearning.domain.User;

public class UserDaoImpl extends ABaseDao implements IUserDao{

	@Override
	public boolean isExist(User user) throws Exception {
		String tbName = null;
		String colPre = null;
		if ("A".equals(user.getRole())) {
			tbName = "TB_ADMIN";
			colPre = "ADMIN";
		} else if ("T".equals(user.getRole())) {
			tbName = "TB_TEACHER";
			colPre = "TEACHER";
		} else if ("S".equals(user.getRole())) {
			tbName = "TB_STUDENT";
			colPre = "STU";
		}
		if (null == tbName || null == colPre) {
			return false;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM ").append(tbName).append(" WHERE ")
				.append(colPre + "_NUMBER = ? ").append(" AND ").append(colPre + "_PASSWORD = ?");

		return super.queryToBean(sql.toString(), new Object[]{user.getNum(), user.getPassword()},
				ConverterFactory.getConverter(Integer.class)) > 0;
	}

}
