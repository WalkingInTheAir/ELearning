package com.elearning.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.elearning.dao.IDepartDao;
import com.elearning.dao.impl.DepartDaoImpl;
import com.elearning.domain.Department;
import com.elearning.service.IDepartService;

public class DepartServiceImpl implements IDepartService {
	private IDepartDao dao = new DepartDaoImpl();

	@Override
	public ResultMessage addDepart(Department dept){
		ResultMessage result = null;
		try {
			if(dao.isExist(dept.getName())){
				result = ResultMessageFactory.getWarningResult("院系：" + dept.getName() + "已存在, 请修改后重试");
			}else{
				result = dao.addDepart(dept) > 0 
						? ResultMessageFactory.getSuccessResult("恭喜您，操作成功")
						: ResultMessageFactory.getErrorResult("网络异常，请稍后再试");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultMessageFactory.getErrorResult("网络异常，请稍后再试");
		}
		return result;
	}

	@Override
	public ResultMessage addDeparts(List<Department> depts) {
		ResultMessage result = null;
		List<Object[]> params = new ArrayList<Object[]>();
		StringBuffer existDepts = new StringBuffer();
		existDepts.append("[");
		try {
			for (Department dept : depts) {
				String deptName = dept.getName();
				if (dao.isExist(deptName)) {
					existDepts.append(deptName).append(", ");
				}else{
					params.add(new Object[]{deptName});
				}
			}
			dao.addDepats(params);
			if(existDepts.length() > 1){
				existDepts.delete(existDepts.lastIndexOf(", "), existDepts.length()-1);
				existDepts.append("]");
				result = ResultMessageFactory.getErrorResult("操作成功，部门:" + existDepts.toString() + "已经存在,无需重复添加");
			}else{
				result = ResultMessageFactory.getSuccessResult("恭喜您，操作成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultMessageFactory.getErrorResult("网络异常，请稍后再试");
		}
		return result;
	}

	@Override
	public PageContent<Department> showDeparts(PageInfo page) throws Exception {
		return dao.queryDepts(page);
	}

	/**
	 * 修改部门信息
	 */
	@Override
	public ResultMessage modifyDept(Department dept) throws Exception{
		ResultMessage rs = null;
		if(dao.isExist(dept.getName())){
			rs = ResultMessageFactory.getWarningResult("院系名称已存在，请修改后重试");
		}else{
			boolean isSuccess = dao.updateDept(dept);
			if(isSuccess){
				rs = ResultMessageFactory.getSuccessResult("修改成功");
			}else{
				rs = ResultMessageFactory.getErrorResult("系统错误，请联系我们");
			}
		}
		return rs;
	}

	/**
	 * 删除部门信息
	 * @throws Exception 
	 */
	@Override
	public ResultMessage deleteDept(int deptId) throws Exception {
		ResultMessage rs = null;
		if(dao.deleteDepts(deptId)){
			rs = ResultMessageFactory.getSuccessResult("删除成功！");
		}else{
			rs = ResultMessageFactory.getErrorResult("系统错误，请稍后再试");
		}
		return rs;
	}

	@Override
	public List<Department> getDeparts(String condition, Object[] params) throws Exception {
		return dao.queryDeparts(condition, params);
	}
}
