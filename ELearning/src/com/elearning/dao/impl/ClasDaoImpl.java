package com.elearning.dao.impl;

import com.elearning.dao.IClasDao;
import com.elearning.domain.Clas;

public class ClasDaoImpl extends ABaseDao implements IClasDao {

	@Override
	public int addClas(Clas clas) throws Exception {
		return super.add("INSERT INTO TB_CLASS VALUES(NULL, ?, ?)",
				new Object[] { clas.getName(), clas.getDept().getId() });
	}

	@Override
	public int deleteClas(int clasId) throws Exception {
		return super.delete("DELETE FROM TB_CLASS WHERE ID = ?", new Object[]{clasId});
	}

	@Override
	public int modifyClas(Clas clas) throws Exception {
		return super.update("UPDATE TB_CLASS SET NAME = ? WHERE ID = ?",
				new Object[] { clas.getName(), clas.getId() });
	}

	@Override
	public boolean isExist(String clasName, String deptName) throws Exception {
		return false;
	}

	@Override
	public Clas getClasById(int id) throws Exception {
		return null;
	}

}
