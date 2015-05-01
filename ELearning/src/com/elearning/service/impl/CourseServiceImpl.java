package com.elearning.service.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.jdbc.converter.IResultSetConverter;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.core.util.regex.RegexUtil;
import com.elearning.dao.IClasDao;
import com.elearning.dao.ICourseDao;
import com.elearning.dao.impl.ClasDaoImpl;
import com.elearning.dao.impl.CourseDaoImpl;
import com.elearning.domain.Course;
import com.elearning.domain.Department;
import com.elearning.service.ICourseService;

public class CourseServiceImpl implements ICourseService {

	private ICourseDao cDao = new CourseDaoImpl();
	private IClasDao clDao = new ClasDaoImpl();
	@Override
	public ResultMessage addCourse(Course c, String conditions, Object[] params) {
		ResultMessage rm = null;
		try {
			if (!this.isExist(c)) {
				List<Integer> clsIds = clDao.getClsIdsToList(conditions, params);
				rm = cDao.addCourse(c, clsIds) > 0
						? ResultMessageFactory.getSuccessResult("添加成功")
						: ResultMessageFactory.getErrorResult("添加失败");
			} else {
				rm = ResultMessageFactory.getErrorResult("该课程已存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		return rm;
	}

	public boolean isExist(Course c) throws Exception {
		return cDao.isExist(c);
	}

	@Override
	public PageContent<Course> showCourses(PageInfo page, String strTypeId, String strRelativeId,
			String courseName) {
		int typeId = RegexUtil.isNumStr(strTypeId) ? Integer.parseInt(strTypeId) : -1;
		int relativeId = RegexUtil.isNumStr(strRelativeId) ? Integer.parseInt(strRelativeId) : -1;
		String rltTabName = null;
		String rltColPre = null; // 列名前缀
		StringBuffer sb = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		if (typeId > 0) {
			sb.append(" F_CT_ID = ? AND F_CT_ID = CT_ID ");
			paraList.add(typeId);
			switch (typeId) {
				case 2 :
					rltTabName = "TB_DEPARTMENT";
					rltColPre = "DEPT";
					break;
				case 3 :
					rltTabName = "TB_MAJOR";
					rltColPre = "MAJOR";
					break;
				case 4 :
					rltTabName = "TB_CLASS";
					rltColPre = "CLS";
					break;
				default :
					;
					break;
			}
			if (relativeId > 0) {
				sb.append(" AND F_RELATIVE_ID = ? ");
				paraList.add(relativeId);
			}
			if (!StringUtils.isBlank(rltTabName)) {
				sb.append(" AND F_RELATIVE_ID = " + rltColPre + "_ID");
			}
		}

		if (!StringUtils.isBlank(courseName)) {
			if (!sb.toString().isEmpty()) {
				sb.append(" AND ");
			}
			sb.append(" COURSE_NAME LIKE ?");
			paraList.add("%" + courseName + "%");
		}

		try {
			return this.cDao.queryCourses(rltTabName, rltColPre, sb.toString(), paraList.toArray(),
					page);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public ResultMessage deleteCourseById(int courseId) {
		ResultMessage rm = null;
		try {
			if (this.cDao.deleteCourse(courseId) > 0) {
				rm = ResultMessageFactory.getSuccessResult("删除成功");
			} else {
				rm = ResultMessageFactory.getErrorResult("删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("该课程已被其他业务关联，无法删除");
		}
		return rm;
	}

	@Override
	public ResultMessage mdfCourse(Course c) {
		ResultMessage rm = null;
		try {
			List<Object> params = new ArrayList<Object>();
			StringBuffer updateCol = new StringBuffer();
			if(null != c.getName()){
				updateCol.append(" COURSE_NAME = ?,");
				params.add(c.getName());
			}
			if(null != c.getDesc()){
				updateCol.append(" COURSE_DESC = ?,");
				params.add(c.getDesc());
			}
			if(null != c.getOutline()){
				updateCol.append(" COURSE_OUTLINE = ?,");
				params.add(c.getOutline());
			}
			params.add(c.getId());
			updateCol.deleteCharAt(updateCol.lastIndexOf(","));
			if (this.cDao.updateCourse(updateCol.toString(), params.toArray()) > 0) {
				rm = ResultMessageFactory.getSuccessResult("修改成功");
			} else {
				rm = ResultMessageFactory.getErrorResult("修改失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		return rm;
	}

	@Override
	public List<Department> getCdtClasses(int courseId) {
		
		try {
			return this.cDao.getCdtClasses(courseId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ResultMessage assignTeacher(int courseId, int teacherId, Set<Integer> classIdList) {
		ResultMessage rm = null;
		try {
			if(this.cDao.updateClassCourseTeacher(courseId, teacherId, classIdList) >= 0){
				rm = ResultMessageFactory.getSuccessResult("教师分配成功");
			}else{
				rm = ResultMessageFactory.getErrorResult("教师分配失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		return rm;
	
	}

	@Override
	public PageContent<Course> getClassCourses(int clsId, PageInfo p) {
		
		try {
			return this.cDao.queryClassCourses(clsId, p);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ResultMessage changeCourseTeacher(int classId, int courseId, int teacherId) {
		ResultMessage rm = null;
		try {
			if(this.cDao.updateCourseTeacher(classId, courseId, teacherId) > 0){
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
	public List<Course> getCourseByStuId(int stuId) {
		String sql = "select course_id, course_name from tb_teacher_course_class tc, tb_course c"
				+ " where tc.f_cls_id = (select f_cls_id from tb_student where stu_id = ?) and f_course_id = c.course_id";
		try {
			return this.cDao.queryCourses(sql, new Object[]{stuId}, new IResultSetConverter<Course>(){

				@Override
				public Course conver(ResultSet rs) throws Exception {
					Course c = new Course();
					c.setId(rs.getInt("course_id"));
					c.setName(rs.getString("course_name"));
					return c;
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Course getCourseById(int courseId) {
		try {
			return this.cDao.getCourseById(courseId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Course> getCoursesByTeacherId(int teacherId) {
		String sql = "select DISTINCT course_id, course_name, course_desc, course_outline"
				+ " from tb_teacher t, tb_teacher_course_class tc, tb_course c "
				+ " where t.teacher_id = ? and t.teacher_id = tc.f_teacher_id and c.course_id = tc.f_course_id";
		try {
			return this.cDao.queryCourses(sql, new Object[]{teacherId}, new IResultSetConverter<Course>(){
				@Override
				public Course conver(ResultSet rs) throws Exception {
					Course c = new Course();
					c.setId(rs.getInt("course_id"));
					c.setName(rs.getString("course_name"));
					c.setDesc(rs.getString("course_desc"));
					c.setOutline(rs.getString("course_outline"));
					return c;
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
