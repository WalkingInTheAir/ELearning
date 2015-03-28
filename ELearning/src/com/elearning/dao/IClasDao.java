package com.elearning.dao;

import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.elearning.domain.Clas;

public interface IClasDao {

	public int addClas(Clas clas) throws Exception;
	
	public int deleteClas(int clasId) throws Exception;
	
	public int modifyClas(Clas clas) throws Exception;
	
	public Clas getClasById(int id) throws Exception;

	public PageContent<Clas> getClasses(String conditions, Object[] params,
			PageInfo pageInfo) throws Exception;
	
	public List<Clas> getClassesToList(String conditions, Object[] params) throws Exception;

	boolean isExist(int majorId, String className) throws Exception;
}
