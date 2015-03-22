package com.elearning.dao.impl;

import java.util.List;

import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;
import com.elearning.dao.IBaseDao;

public abstract class ABaseDao implements IBaseDao{

	@Override
	public int delete(String sqlStr, Object[] params) throws Exception {
		return this.perform(sqlStr, params);
	}

	@Override
	public int add(String sqlStr, Object[] params) throws Exception {
		return this.perform(sqlStr, params);
	}

	@Override
	public int update(String sqlStr, Object[] params) throws Exception {
		return this.perform(sqlStr, params);
	}

	@Override
	public int getAccount(String sqlStr, Object[] params) throws Exception {
		return this.perform(sqlStr, params);
	}

	
	protected int perform(String sqlStr, Object[] params) throws Exception{
		return DBManager.executeUpdate(sqlStr, params);
	}

	@Override
	public <T> List<T> queryToList(String sqlStr, Object[] params,
			IResultSetConverter<T> converter) throws Exception {
		return DBManager.queryToList(sqlStr, params, converter);
	}

	@Override
	public <T> T queryToBean(String sqlStr, Object[] params,
			IResultSetConverter<T> converter) throws Exception {
		return DBManager.queryToBean(sqlStr, params, converter);
	}
	
	

}
