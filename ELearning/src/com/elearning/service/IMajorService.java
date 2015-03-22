package com.elearning.service;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.elearning.domain.Major;

public interface IMajorService {
	public PageContent<Major> showMajors(String conditions, Object[] params,
			PageInfo pageInfo) throws Exception;
	
	public boolean isExist(int deptId, String majorName) throws Exception;
	
	public boolean addMajor(Major major) throws Exception;
	
	public ResultMessage deleteMajor(int majorId);
	
	public ResultMessage modifyMajor(Major major);
}
