package com.elearning.service.impl;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.elearning.dao.IStudentDao;
import com.elearning.dao.impl.StudentDaoImpl;
import com.elearning.domain.Student;
import com.elearning.service.IStudentService;

public class StudentServiceImpl implements IStudentService {
	
	private IStudentDao stuDao = new StudentDaoImpl();
	@Override
	public ResultMessage addStudent(Student stu) {
		ResultMessage rm = null;
		try {
			if(this.isExist(stu)){
				rm = ResultMessageFactory.getErrorResult("该学号已经存在");
			}else if(stuDao.addStudent(stu) > 0){
				rm = ResultMessageFactory.getSuccessResult("添加成功");
			}else{
				rm = ResultMessageFactory.getErrorResult("添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请稍后重试");
		}
		return rm;
	}
	@Override
	public boolean isExist(Student stu) {
		try {
			return stuDao.isExist(stu.getNum());
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	@Override
	public PageContent<Student> showStudents(String conditions,
			Object[] params, PageInfo page) {
		try {
			return stuDao.getStudents(conditions, params, page);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public ResultMessage deleteStudent(int stuId) {
		ResultMessage rm = null;
		try {
			if(stuDao.deleteStudent(stuId) > 0){
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
	public ResultMessage modifyStudent(Student stu) {
		ResultMessage rm = null;
		try {
			if(stuDao.modifyStudent(stu) > 0){
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
