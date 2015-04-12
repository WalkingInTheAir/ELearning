package com.elearning.service;

import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.elearning.domain.Clas;

public interface IClasService {
	public PageContent<Clas> showClasz(String conditions, Object[] params,
			PageInfo pageInfo);

	public boolean isExist(int majorId, String className);
	
	public ResultMessage addClass(Clas clas) throws Exception;
	
	public ResultMessage deleteClass(int classId);
	
	public ResultMessage mdfClass(Clas clas);
	
	public List<Integer> getClsIds(String conditions, Object[] params);
}
