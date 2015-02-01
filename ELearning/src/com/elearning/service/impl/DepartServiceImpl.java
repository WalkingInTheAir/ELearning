package com.elearning.service.impl;

import com.elearning.dao.IDepartDao;
import com.elearning.dao.impl.DepartDaoImpl;
import com.elearning.domain.Department;
import com.elearning.service.IDepartService;
import com.elearning.web.bean.ResultMessage;
import com.elearning.web.bean.ResultMessageFactory;

public class DepartServiceImpl implements IDepartService {
	private IDepartDao dao = new DepartDaoImpl();

	@Override
	public ResultMessage addDepart(Department dept){
		ResultMessage result = null;
		try {
			if(dao.isExist(dept.getName())){
				result = ResultMessageFactory.getFailResult("院系：" + dept.getName() + "已存在, 请修改后重试");
			}else{
				result = dao.addDepart(dept) > 0 
						? ResultMessageFactory.getSuccessResult("恭喜您，操作成功")
						: ResultMessageFactory.getFailResult("网络异常，请稍后再试");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultMessageFactory.getFailResult("网络异常，请稍后再试");
		}
		return result;
	}
}
