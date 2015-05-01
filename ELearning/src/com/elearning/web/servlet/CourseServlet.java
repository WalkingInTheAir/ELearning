package com.elearning.web.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.core.showmsg.bean.ResultType;
import com.core.util.regex.RegexUtil;
import com.elearning.domain.Course;
import com.elearning.domain.CourseType;
import com.elearning.domain.Department;
import com.elearning.service.ICourseService;
import com.elearning.service.ICourseTypeService;
import com.elearning.service.impl.CourseServiceImpl;
import com.elearning.service.impl.CourseTypeServiceImpl;

public class CourseServlet extends BaseServlet {

	private static final long serialVersionUID = -8403892901312764484L;
	private ICourseService cSer = new CourseServiceImpl();
	private ICourseTypeService ctSer = new CourseTypeServiceImpl();
	@Override
	protected void perfom(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if ("addCourse".equals(method)) {
			addCourse(request, response);
		} else if ("addCourses".equals(method)) {
			addCourses(request, response);
		} else if ("showPage".equals(method)) {
			showCourses(request, response);
		} else if ("deleteCourse".equals(method)) {
			deleteCourse(request, response);
		} else if ("modifyCourse".equals(method)) {
			modifyCourse(request, response);
		} else if ("cdtClasses".equals(method)) {
			getCdtClasses(request, response);
		} else if ("asnTeacher".equals(method)) {
			asnTeacher(request, response);
		} else if("classCourse".equals(method)){
			getClassCourse(request, response);
		}else if("changeTeacher".equals(method)){
			changeCourseTeacher(request, response);
		}
		return;
	}

	private void changeCourseTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String strClassId = request.getParameter("classId");
		String strCourseId = request.getParameter("courseId");
		String strTeacherId = request.getParameter("teacherId");
		int classId = RegexUtil.isNumStr(strClassId) ? Integer.parseInt(strClassId) : -1;
		int courseId = RegexUtil.isNumStr(strCourseId) ? Integer.parseInt(strCourseId) : -1;
		int teacherId = RegexUtil.isNumStr(strTeacherId) ? Integer.parseInt(strTeacherId) : -1;
		
		ResultMessage rm = null;
		if(classId < 1 || courseId < 1 || teacherId < 1){
			rm = ResultMessageFactory.getErrorResult("参数异常，请重试");
		}else{
			rm = this.cSer.changeCourseTeacher(classId, courseId, teacherId);
		}
		
		response.getWriter().println(rm.toJSONObj());
	}

	private void getClassCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PageInfo page = new PageInfo();
		String currPageStr = request.getParameter("currPage");
		String pageSizeStr = request.getParameter("pageSize");
		if (RegexUtil.isNumStr(currPageStr)) {
			page.setCurrPage(Integer.parseInt(currPageStr));
		}
		if (RegexUtil.isNumStr(pageSizeStr)) {
			page.setPageSize(Integer.parseInt(pageSizeStr));
		}
		String strClassId = request.getParameter("classId");
		int clsId = RegexUtil.isNumStr(strClassId) ? Integer.parseInt(strClassId) : -1;
		PageContent<Course> courses = this.cSer.getClassCourses(clsId, page);
		response.getWriter().println(courses.toJSONObj());
	}

	private void asnTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String strCourseId = request.getParameter("courseId");
		String strClassIds = request.getParameter("classIds");
		String strTeacherId = request.getParameter("teacherId");
		
		ResultMessage rm = null;
		int courseId = RegexUtil.isNumStr(strCourseId) ? Integer.parseInt(strCourseId) : -1;
		if(courseId < 1){
			rm = ResultMessageFactory.getErrorResult("请选择课程");
			response.getWriter().println(rm.toJSONObj());
			return;
		}
		Set<Integer> classIdList = new HashSet<Integer>();
		if(StringUtils.isBlank(strClassIds)){
			rm = ResultMessageFactory.getErrorResult("请选择班级");
		}else{
			String[] buf = strClassIds.split(",");
			for(int i=0,len=buf.length; i<len; i++){
				if(RegexUtil.isNumStr(buf[i])){
					classIdList.add(Integer.parseInt(buf[i]));
				}else{
					rm = ResultMessageFactory.getErrorResult("参数异常，请重试");
					break;
				}
			}
		}
		if(null != rm){
			response.getWriter().println(rm.toJSONObj());
			return;
		}
		int teacherId = RegexUtil.isNumStr(strTeacherId)? Integer.parseInt(strTeacherId) : -1;
		if(teacherId < 1){
			rm = ResultMessageFactory.getErrorResult("请选择教师");
		}else{
			rm = this.cSer.assignTeacher(courseId, teacherId, classIdList);
		}
		
		response.getWriter().println(rm.toJSONObj());
		return;
	}

	private void getCdtClasses(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String strCourseId = request.getParameter("courseId");
		ResultMessage rm = null;
		int courseId = RegexUtil.isNumStr(strCourseId) ? Integer.parseInt(strCourseId) : -1;

		if (courseId < 1) {
			rm = ResultMessageFactory.getErrorResult("参数异常，请重试");
		} else {
			List<Department> depts = this.cSer.getCdtClasses(courseId);
			if (null != depts) {
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[]{"dept", "major"});
				JSONArray jsons = JSONArray.fromObject(depts, config);
				response.getWriter().println(jsons);
				return;
			} else {
				rm = ResultMessageFactory.getErrorResult("系统错误，请联系我们");
			}
		}
		if (null != rm) {
			response.getWriter().println(rm.toJSONObj());
		}
	}

	private void modifyCourse(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String strCourseId = request.getParameter("courseId");
		String courseName = request.getParameter("courseName");
		String courseDesc = request.getParameter("courseDesc");
		String courseOutline = request.getParameter("courseOutline");

		ResultMessage rm = null;
		int courseId = RegexUtil.isNumStr(strCourseId) ? Integer.parseInt(strCourseId) : -1;

		if (courseId <= 0) {
			rm = ResultMessageFactory.getErrorResult("参数异常，请重试");
		} else if (StringUtils.isBlank(courseName) && StringUtils.isBlank(courseDesc) && StringUtils.isBlank(courseOutline)) {
			rm = ResultMessageFactory.getErrorResult("课程名称不能为空");
		} else {
			Course c = new Course();
			c.setId(courseId);
			c.setName(courseName);
			c.setDesc(courseDesc);
			c.setOutline(courseOutline);
			rm = this.cSer.mdfCourse(c);
		}
		if(rm.getType().equals(ResultType.SUCCESS)){
			//跟新session
			HttpSession session = request.getSession(false);
			@SuppressWarnings("unchecked")
			List<Course> cs = (List<Course>) session.getAttribute("courses");
			if(null != cs && !cs.isEmpty()){
				for(Course c : cs){
					if(c.getId() == courseId){
						if(null != courseDesc){
							c.setDesc(courseDesc);
						}
						if(null != courseOutline){
							c.setOutline(courseOutline);
						}
						break;
					}
				}
			}
		}
		response.getWriter().println(rm.toJSONObj());
	}

	private void deleteCourse(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String strCourseId = request.getParameter("courseId");
		ResultMessage rm = null;
		int courseId = RegexUtil.isNumStr(strCourseId) ? Integer.parseInt(strCourseId) : -1;

		if (courseId <= 0) {
			rm = ResultMessageFactory.getErrorResult("参数有误，请重试");
		} else {
			rm = this.cSer.deleteCourseById(courseId);
		}
		response.getWriter().println(rm.toJSONObj());
	}

	private void showCourses(HttpServletRequest request, HttpServletResponse response) {
		PageInfo page = new PageInfo();
		String currPageStr = request.getParameter("currPage");
		String pageSizeStr = request.getParameter("pageSize");
		if (RegexUtil.isNumStr(currPageStr)) {
			page.setCurrPage(Integer.parseInt(currPageStr));
		}
		if (RegexUtil.isNumStr(pageSizeStr)) {
			page.setPageSize(Integer.parseInt(pageSizeStr));
		}
		String strTypeId = request.getParameter("typeId");
		String strRelativeId = request.getParameter("relativeId");
		String courseName = request.getParameter("courseName");
		try {
			String isAjaxPost = request.getParameter("ajaxPost");
			// 翻页采用ajax
			if (null != isAjaxPost && "T".equals(isAjaxPost)) {
				String req4List = request.getParameter("forList");
				if (RegexUtil.isTrue(req4List)) {
					page.setPageSize(100);
				}
				PageContent<Course> courses = this.cSer.showCourses(page, strTypeId, strRelativeId,
						courseName);
				response.getWriter().print(courses.toJSONObj());
			} else {
				List<CourseType> cts = this.ctSer.getTypes(null, null);
				request.setAttribute("filter", cts);
				request.setAttribute("include", "/page_admin/course.jsp");
				super.gotoTemplatePage(request, response, "page_admin/template.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addCourses(HttpServletRequest request, HttpServletResponse response) {
	}

	private void addCourse(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String strTypeId = request.getParameter("typeId");
		String strRelativeId = request.getParameter("relativeId");

		ResultMessage rm = null;
		int typeId = RegexUtil.isNumStr(strTypeId) ? Integer.parseInt(strTypeId) : 0;

		String courseName = request.getParameter("courseName");
		Course c = null;
		String conditions = null;
		Object[] params = null;
		if (typeId <= 0) {
			rm = ResultMessageFactory.getErrorResult("课程类型数据异常，请重试");
		} else if (StringUtils.isBlank(courseName)) {
			rm = ResultMessageFactory.getErrorResult("课程名成不能为空");
		} else {
			c = new Course();
			c.setName(courseName);
			c.setCt(new CourseType(typeId));
			if (typeId == 1) {
				c.setRelativeId(null);
			} else {
				if (RegexUtil.isNumStr(strRelativeId) && Integer.parseInt(strRelativeId) > 0) {
					c.setRelativeId(strRelativeId);
					switch (typeId) {
						case 2 :
							conditions = "DEPT_ID = ?";
							break;
						case 3 :
							conditions = "MAJOR_ID = ?";
							break;
						case 4 :
							conditions = "CLS_ID = ?";
							break;
						default :
							rm = ResultMessageFactory.getErrorResult("参数异常，请重试");
							break;
					}
					if (null != conditions) {
						params = new Object[]{Integer.parseInt(strRelativeId)};
					}
				} else {
					rm = ResultMessageFactory.getErrorResult("参数异常，请重试");
				}
			}
		}
		if (rm == null) {
			rm = this.cSer.addCourse(c, conditions, params);
		}

		response.getWriter().print(rm.toJSONObj());

	}

}
