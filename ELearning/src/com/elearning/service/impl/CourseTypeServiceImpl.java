package com.elearning.service.impl;

import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.elearning.dao.ICourseTypeDao;
import com.elearning.dao.impl.CourseTypeDaoImpl;
import com.elearning.domain.CourseType;
import com.elearning.service.ICourseTypeService;

public class CourseTypeServiceImpl implements ICourseTypeService{
	ICourseTypeDao dao = new CourseTypeDaoImpl();
	
	@Override
	public boolean isExist(String conditions, Object[] params) {
		try {
			return null != dao.getCourseType(conditions, params);
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	@Override
	public ResultMessage addType(CourseType type) {
		ResultMessage rm = null;
		try {
			String conditions = " CT_NAME = ?";
			Object[] params = new Object[]{type.getName()};
			if(this.isExist(conditions, params)){
				rm = ResultMessageFactory.getWarningResult("该课程类型已存在，请修改");
				return rm;
			}
			int rows = dao.addType(type);
			if(rows <= 0){
				rm = ResultMessageFactory.getErrorResult("添加失败");
			}else{
				rm = ResultMessageFactory.getSuccessResult("添加成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		
		return rm;
	}

	@Override
	public PageContent<CourseType> showTypes(PageInfo page) {
		try {
			return dao.getPage(page);
		} catch (Exception e) {
			e.printStackTrace();
			PageContent<CourseType> types = new PageContent<CourseType>();
			types.setPage(page);
			return types;
		}
	}

	@Override
	public ResultMessage modifyType(CourseType type) {
		ResultMessage rm = null;
		try {
			String conditions = " CT_NAME = ?";
			Object[] params = new Object[]{type.getName()};
			if(this.isExist(conditions, params)){
				rm = ResultMessageFactory.getWarningResult("该课程类型已存在，请修改");
				return rm;
			}
			int rows = dao.modifyType(type);
			if(rows > 0){
				rm = ResultMessageFactory.getSuccessResult("修改成功");
			}else{
				rm = ResultMessageFactory.getErrorResult("修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		return rm;
	}

	@Override
	public ResultMessage deleteType(int typeId) {
		ResultMessage rm = null;
		try {
			int rows = dao.deleteType(typeId);
			if (rows > 0) {
				rm = ResultMessageFactory.getSuccessResult("删除成功");
			} else {
				rm = ResultMessageFactory.getErrorResult("删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		return rm;
	}

	@Override
	public List<CourseType> getTypes(String conditions, Object[] params) {
		
		try {
			return dao.queryList(conditions, params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
