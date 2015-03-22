package com.elearning.dao;

import com.elearning.domain.Clas;

public interface IClasDao {

	public int addClas(Clas clas) throws Exception;
	
	public int deleteClas(int clasId) throws Exception;
	
	public int modifyClas(Clas clas) throws Exception;
	
	public boolean isExist(String clasName, String deptName) throws Exception;
	
	public Clas getClasById(int id) throws Exception;
}
