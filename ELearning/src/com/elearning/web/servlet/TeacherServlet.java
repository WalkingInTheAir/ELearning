package com.elearning.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.core.util.regex.RegexUtil;
import com.elearning.domain.Course;
import com.elearning.domain.CourseWare;
import com.elearning.domain.Department;
import com.elearning.domain.FAQ;
import com.elearning.domain.Teacher;
import com.elearning.domain.User;
import com.elearning.service.ICourseService;
import com.elearning.service.ICourseWareService;
import com.elearning.service.IDepartService;
import com.elearning.service.IFAQService;
import com.elearning.service.ITeacherService;
import com.elearning.service.impl.CourseServiceImpl;
import com.elearning.service.impl.CourseWareServiceImpl;
import com.elearning.service.impl.DepartServiceImpl;
import com.elearning.service.impl.FAQServiceImpl;
import com.elearning.service.impl.TeacherServiceImpl;

public class TeacherServlet extends BaseServlet{

	private static final long serialVersionUID = -1589142886934834337L;
	private ITeacherService teaSer = new TeacherServiceImpl();
	private IDepartService deptSer = new DepartServiceImpl();
	private ICourseService couSer = new CourseServiceImpl();
	private IFAQService faqSer = new FAQServiceImpl();
	private ICourseWareService cwSer = new CourseWareServiceImpl();
	
	@Override
	protected void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(null == method){
			gotoIndex(request, response);
		}else if ("addTeacher".equals(method)) {
			addTeacher(request, response);
		} else if ("addTeachers".equals(method)) {
			addTeachers(request, response);
		} else if ("showPage".equals(method)) {
			showTeachers(request, response);
		} else if ("deleteTeacher".equals(method)) {
			deleteTeacher(request, response);
		} else if ("modifyTeacher".equals(method)) {
			modifyTeacher(request, response);
		}else if("backOperate".equals(method)){
			backOperate(request, response);
		}
		return;
	}


	private void backOperate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String item = request.getParameter("item");
		int courseId = RegexUtil.isNumStr(request.getParameter("courseId")) ? Integer.parseInt(request.getParameter("courseId")) : -1;
		String isAjaxPost = request.getParameter("ajaxPost");
		String include = "courseInfo.jsp";
		if("courseInfo".equals(item)){
			
		}else if("courseware".equals(item)){
			this.getCourseWare(request, response);
			include = "courseware.jsp";
		}else if("video".equals(item)){
			include = "video.jsp";
		}else if("task".equals(item)){
			include = "task.jsp";
		}else if("comunicate".equals(item)){
			include = "comunicate.jsp";
		}else if("FAQ".equals(item)){
			this.getFAQS(request, response);
			include = "FAQ.jsp";
		}else{
			
		}
		if(null != isAjaxPost && "T".equals(isAjaxPost)){
			return;
		}
		request.setAttribute("include", include);
		HttpSession session = request.getSession(false);
		@SuppressWarnings("unchecked")
		List<Course> cs = (List<Course>) session.getAttribute("courses");
		if (courseId > 0) {
			for (Course c : cs) {
				if (c.getId() == courseId) {
					request.setAttribute("course", c);
					break;
				}
			}
		}
		this.gotoTemplate(request, response);
	}


	private void getCourseWare(HttpServletRequest request, HttpServletResponse response) {
		PageInfo page = new PageInfo();
		String currPageStr = request.getParameter("currPage");
		String pageSizeStr = request.getParameter("pageSize");
		if(RegexUtil.isNumStr(currPageStr)){
			page.setCurrPage(Integer.parseInt(currPageStr));
		}
		if(RegexUtil.isNumStr(pageSizeStr)){
			page.setPageSize(Integer.parseInt(pageSizeStr));
		}
		page.setPageSize(100);	//不需要分页
		int courseId = RegexUtil.isNumStr(request.getParameter("courseId")) ? Integer.parseInt(request.getParameter("courseId")) : -1;
		try {
			PageContent<CourseWare> cws = this.cwSer.showCourseWares(page, courseId);
			String isAjaxPost = request.getParameter("ajaxPost");
			//翻页采用ajax
			if(null != isAjaxPost && "T".equals(isAjaxPost)){
				response.getWriter().print(cws.toJSONObj());
			}else{
				request.setAttribute("cata", cws);
				request.setAttribute("pageInfo", cws.getPage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}


	private void getFAQS(HttpServletRequest request, HttpServletResponse response) {
		PageInfo page = new PageInfo();
		String currPageStr = request.getParameter("currPage");
		String pageSizeStr = request.getParameter("pageSize");
		if(RegexUtil.isNumStr(currPageStr)){
			page.setCurrPage(Integer.parseInt(currPageStr));
		}
		if(RegexUtil.isNumStr(pageSizeStr)){
			page.setPageSize(Integer.parseInt(pageSizeStr));
		}
		int courseId = RegexUtil.isNumStr(request.getParameter("courseId")) ? Integer.parseInt(request.getParameter("courseId")) : -1;
		try {
			PageContent<FAQ> FAQS = this.faqSer.showFAQs(page, courseId);
			String isAjaxPost = request.getParameter("ajaxPost");
			//翻页采用ajax
			if(null != isAjaxPost && "T".equals(isAjaxPost)){
				response.getWriter().print(FAQS.toJSONObj());
			}else{
				request.setAttribute("cata", FAQS);
				request.setAttribute("pageInfo", FAQS.getPage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}


	private void gotoIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		User teacher = (User) session.getAttribute("user");
		List<Course> cs = couSer.getCoursesByTeacherId(teacher.getId());
		session.setAttribute("courses", cs);
		this.gotoTemplate(request, response);
	}

	private void modifyTeacher(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strTeacherId = request.getParameter("teacherId");
		String teacherName = request.getParameter("teacherName");
		ResultMessage rm = null;
		int teacherId = -1;
		if(RegexUtil.isNumStr(strTeacherId)){
			teacherId = Integer.parseInt(strTeacherId);
		}
		if(teacherId <= 0){
			rm = ResultMessageFactory.getErrorResult("参数有误，请重试");
		}else if(StringUtils.isBlank(teacherName)){
			rm = ResultMessageFactory.getErrorResult("教师姓名不能为空");
		}else{
			Teacher t = new Teacher();
			t.setId(teacherId);
			t.setName(teacherName.trim());
			rm = this.teaSer.modifyTeacher(t);
		}
		response.getWriter().print(rm.toJSONObj());
	}

	private void deleteTeacher(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strTeacherId = request.getParameter("teacherId");
		int teacherId = -1;
		if (RegexUtil.isNumStr(strTeacherId)) {
			teacherId = Integer.parseInt(strTeacherId);
		}
		ResultMessage rm = null;
		if (teacherId < 0) {
			rm = ResultMessageFactory.getErrorResult("参数异常，请稍后重试");
		} else {
			rm = this.teaSer.delteTeacher(teacherId);
		}

		response.getWriter().print(rm.toJSONObj());
	}

	private void showTeachers(HttpServletRequest request,
			HttpServletResponse response) {
		PageInfo page = new PageInfo();
		String currPageStr = request.getParameter("currPage");
		String pageSizeStr = request.getParameter("pageSize");
		if (RegexUtil.isNumStr(currPageStr)) {
			page.setCurrPage(Integer.parseInt(currPageStr));
		}
		if (RegexUtil.isNumStr(pageSizeStr)) {
			page.setPageSize(Integer.parseInt(pageSizeStr));
		}
		String conditions = null;
		Object[] params = null;
		String strDeptId = request.getParameter("deptId");
		int deptId = -1;
		if(RegexUtil.isNumStr(strDeptId) && (deptId = Integer.parseInt(strDeptId)) > 0){
			conditions = " F_DEPT_ID = ?";
			params = new Object[]{deptId};
		}
		try {
			String req4List = request.getParameter("forList");
			if(RegexUtil.isTrue(req4List)){
				page.setPageSize(100);
			}
			PageContent<Teacher> teachers = this.teaSer.getTeachers(conditions, params, page);
			String isAjaxPost = request.getParameter("ajaxPost");
			// 翻页采用ajax
			if (null != isAjaxPost && "T".equals(isAjaxPost)) {
				response.getWriter().print(teachers.toJSONObj());
			} else {
				List<Department> depts = this.deptSer.getDeparts(null, null);
				request.setAttribute("filter", depts);
				request.setAttribute("cata", teachers);
				request.setAttribute("pageInfo", teachers.getPage());
				request.setAttribute("include", "/page_admin/teacher.jsp");
				super.gotoTemplatePage(request, response,"page_admin/template.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addTeachers(HttpServletRequest request,
			HttpServletResponse response) {
		//TODO
	}

	private void addTeacher(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ResultMessage rm = null;
		String teacherName = request.getParameter("teacherName");
		String teacherNum = request.getParameter("teacherNum");
		String strDeptId = request.getParameter("deptId");
		int deptId = -1;
		try{
		if(StringUtils.isBlank(teacherName)){
			rm = ResultMessageFactory.getWarningResult("教师名称不能为空");
		}else if(StringUtils.isBlank(teacherNum)){
			rm = ResultMessageFactory.getWarningResult("教师工号不能为空");
		}else if(!RegexUtil.isNumStr(strDeptId) || (deptId = Integer.parseInt(strDeptId)) == -1){
			rm = ResultMessageFactory.getWarningResult("部门信息有误");
		}
		if(null == rm){
			Teacher t = new Teacher();
			t.setName(teacherName);
			t.setNum(teacherNum);
			t.setDept(new Department(deptId));
			rm = this.teaSer.addTeacher(t);
		}
		}catch(Exception e){
			e.printStackTrace();
			rm = ResultMessageFactory.getWarningResult("系统异常，请联系我们");
		}
		response.getWriter().println(rm.toJSONObj());
	}

	private void gotoTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		super.gotoTemplatePage(request, response, "page_teacher/template.jsp");
	}
}
