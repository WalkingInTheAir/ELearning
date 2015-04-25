package com.elearning.dao.impl;

import java.sql.ResultSet;

import com.core.jdbc.converter.IResultSetConverter;
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
		sql.append("SELECT ")
			.append(colPre + "_id,")
			.append(colPre + "_number,")
			.append(colPre + "_name,")
			.append(colPre + "_password ")
			.append(" FROM ").append(tbName)
			.append(" WHERE ")
			.append(colPre + "_NUMBER = ? ").append(" AND ").append(colPre + "_PASSWORD = ?");

		super.queryToBean(sql.toString(), new Object[]{user.getNum(), user.getPassword()},
				new IResultSetConverter<User>(){
					@Override
					public User conver(ResultSet rs) throws Exception {
						user.setId(rs.getInt(1));
						user.setName(rs.getString(3));
						user.setNum(rs.getString(2));
						user.setPassword(rs.getString(4));
						return user;
					}
			
		});
		return user.getId() > 0;
	}

}
