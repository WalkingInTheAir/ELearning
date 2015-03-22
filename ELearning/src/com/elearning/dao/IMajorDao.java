package com.elearning.dao;

import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.elearning.domain.Major;

public interface IMajorDao {
	public boolean addMajor(Major m) throws Exception;
	public boolean deleteMajor(int id) throws Exception;
	public boolean updateMajor(Major m) throws Exception;
	public boolean isExist(Major m) throws Exception;
	public List<Major> getMajorsByCondition(String condition, Object[] params) throws Exception;
	public PageContent<Major> getMajors(String condition, Object[] params, PageInfo pageInfo) throws Exception;
}
