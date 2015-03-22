package com.elearning.service;

import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.elearning.domain.Department;
		
public interface IDepartService {
	public ResultMessage addDepart(Department dept);
	public ResultMessage addDeparts(List<Department> depts);
	public PageContent<Department> showDeparts(PageInfo page) throws Exception;
	public ResultMessage modifyDept(Department dept) throws Exception;
	public ResultMessage deleteDept(int deptId) throws Exception;
	public List<Department> getDeparts(String condition, Object[] params) throws Exception;
}
	