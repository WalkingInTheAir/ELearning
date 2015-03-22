package com.elearning.service.impl;

import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.elearning.dao.IMajorDao;
import com.elearning.dao.impl.MajorDaoImpl;
import com.elearning.domain.Major;
import com.elearning.service.IMajorService;

public class MajorServiceImpl implements IMajorService {

	private IMajorDao dao = new MajorDaoImpl();

	@Override
	public PageContent<Major> showMajors(String conditions, Object[] params,
			PageInfo pageInfo) throws Exception {
		return dao.getMajors(conditions, params, pageInfo);
	}

	@Override
	public boolean isExist(int deptId, String majorName) throws Exception {
		List<Major> majors = dao
				.getMajorsByCondition(" F_DEPT_ID = ? and MAJOR_NAME = ?",
						new Object[] { deptId, majorName });

		return majors.size() > 0;
	}

	@Override
	public boolean addMajor(Major major) throws Exception {
		return dao.addMajor(major);
	}

	@Override
	public ResultMessage deleteMajor(int majorId){
		ResultMessage result = null;
		try {
			if (dao.deleteMajor(majorId)) {
				result = ResultMessageFactory.getSuccessResult("删除成功");
			} else {
				result = ResultMessageFactory.getErrorResult("删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultMessageFactory.getErrorResult("无法进行删除操作");
		}
		return result;
	}

	@Override
	public ResultMessage modifyMajor(Major major) {
		ResultMessage result = null;
		try {
			if(dao.updateMajor(major)){
				result = ResultMessageFactory.getSuccessResult("修改成功");
			}else{
				result = ResultMessageFactory.getWarningResult("修改失败，请重试");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		return result;
	}

}
