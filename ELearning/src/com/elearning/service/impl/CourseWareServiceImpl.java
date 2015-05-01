package com.elearning.service.impl;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.elearning.dao.ICourseWareDao;
import com.elearning.dao.impl.CourseWareDaoImpl;
import com.elearning.domain.CourseWare;
import com.elearning.service.ICourseWareService;

public class CourseWareServiceImpl implements ICourseWareService{
	private ICourseWareDao dao = new CourseWareDaoImpl();
	@Override
	public PageContent<CourseWare> showCourseWares(PageInfo page, int courseId) {
		try {
			return dao.query(page, courseId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public ResultMessage addCourseWare(CourseWare cw) {
		ResultMessage rm = null;
		try {
			if(this.dao.add(cw) > 0){
				rm = ResultMessageFactory.getSuccessResult("上传成功");
			}else{
				rm = ResultMessageFactory.getErrorResult("上传失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		};
		return rm;
	}
	@Override
	public CourseWare getCourseWareById(int id) {
		
		try {
			return this.dao.getCourseWareById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public ResultMessage delete(int cwId) {
		ResultMessage rm = null;
		try {
			if(this.dao.delete(cwId)){
				rm = ResultMessageFactory.getSuccessResult("删除成功");
			}else{
				rm = ResultMessageFactory.getErrorResult("删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("网络异常，请重试");
		}
		return rm;
	}

}
