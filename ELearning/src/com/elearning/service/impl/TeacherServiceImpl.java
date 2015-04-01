package com.elearning.service.impl;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.elearning.dao.ITeacherDao;
import com.elearning.dao.impl.TeacherDaoImpl;
import com.elearning.domain.Teacher;
import com.elearning.service.ITeacherService;

public class TeacherServiceImpl implements ITeacherService {

	private ITeacherDao dao = new TeacherDaoImpl();
	@Override
	public ResultMessage addTeacher(Teacher t) {
		ResultMessage rm = null;
		try {
			int rows = dao.addTeacher(t);
			if(rows > 0){
				rm = ResultMessageFactory.getSuccessResult("添加成功");
			}else{
				rm = ResultMessageFactory.getErrorResult("添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		return rm;
	}
	@Override
	public PageContent<Teacher> getTeachers(String conditions, Object[] params,
			PageInfo page) {
		try {
			return dao.getTeachers(conditions, params, page);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public ResultMessage delteTeacher(int teacherId) {
		ResultMessage rm = null;
		try {
			if(dao.deleteTeacher(teacherId) > 0){
				rm = ResultMessageFactory.getSuccessResult("删除成功");
			}else{
				rm = ResultMessageFactory.getErrorResult("删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		return rm;
	}
	@Override
	public ResultMessage modifyTeacher(Teacher t) {
		ResultMessage rm = null;
		try {
			if(dao.modifyTeacher(t) > 0){
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

}
