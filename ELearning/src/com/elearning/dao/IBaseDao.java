package com.elearning.dao;

import java.util.List;

import com.core.jdbc.converter.IResultSetConverter;

public interface IBaseDao {
	public int delete(String sqlStr, Object[] params) throws Exception;
	public int add(String sqlStr, Object[] params) throws Exception;
	public int update(String sqlStr, Object[] params) throws Exception;
	public int getAccount(String sqlStr, Object[] params) throws Exception;
	public <T> List<T> queryToList(String sqlStr, Object[] params, IResultSetConverter<T> converter) throws Exception;
	public <T> T queryToBean(String sqlStr, Object[] params, IResultSetConverter<T> converter) throws Exception;
}
