package com.elearning.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.regex.util.RegexUtil;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.elearning.domain.Clas;
import com.elearning.domain.Course;
import com.elearning.domain.Department;
import com.elearning.domain.Student;
import com.elearning.domain.User;
import com.elearning.service.ICourseService;
import com.elearning.service.IDepartService;
import com.elearning.service.IStudentService;
import com.elearning.service.impl.CourseServiceImpl;
import com.elearning.service.impl.DepartServiceImpl;
import com.elearning.service.impl.StudentServiceImpl;

public class StudentServlet extends BaseServlet {

	private static final long serialVersionUID = -7057584899664197419L;
	private IStudentService stuSev = new StudentServiceImpl();
	private IDepartService deptSev = new DepartServiceImpl();
	private ICourseService courSev = new CourseServiceImpl();
	@Override
	protected void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(null == method){	//go to student index page
			this.gotoIndex(request, response);
		}else if ("showPage".equals(method)) {
			this.showPage(request, response);
		} else if ("addStudent".equals(method)) {
			this.addStudent(request, response);
		} else if ("deleteStudent".equals(method)) {
			this.deleteStudent(request, response);
		} else if ("modifyStudent".equals(method)) {
			this.modifyStudent(request, response);
		} else if ("addStudents".equals(method)) {
			this.addStudents(request, response);
		} else if("coursePage".equals(method)){
			this.gotoCoursePage(request, response);
		}
	}

	private void gotoCoursePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String strCourseId = request.getParameter("courseId");
		String item = request.getParameter("item");
		
		String includeJsp = "courseIndex.jsp";
		if ("outline".equals(item)) {

		} else if ("courseware".equals(item)) {

		} else if ("video".equals(item)) {

		} else if ("task".equals(item)) {

		} else if ("comunicate".equals(item)) {

		} else if ("FAQ".equals(item)) {

		} else{
			
		}
		int courseId = RegexUtil.isNumStr(strCourseId) ? Integer.parseInt(strCourseId) : -1;
		Course c = courSev.getCourseById(courseId);
		request.setAttribute("course", c);
		request.setAttribute("include", includeJsp);
		super.gotoTemplatePage(request, response, "page_stu/template.jsp");
	}

	private void gotoIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("user");
		int userId = u.getId();
		List<Course> courses = courSev.getCourseByStuId(userId);
		request.setAttribute("courses", courses);
		request.getRequestDispatcher("page_stu/index.jsp").forward(request, response);
	}

	private void addStudents(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO
	}

	private void modifyStudent(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strStuId = request.getParameter("stuId");
		String stuName = request.getParameter("stuName");
		ResultMessage rm = null;
		int stuId = -1;
		if(RegexUtil.isNumStr(strStuId)){
			stuId = Integer.parseInt(strStuId);
		}
		if(stuId <= 0){
			rm = ResultMessageFactory.getWarningResult("学生信息参数有误，请重试");
		}else if(StringUtils.isBlank(stuName)){
			rm = ResultMessageFactory.getWarningResult("学生姓名不能为空");
		}else{
			Student stu = new Student();
			stu.setId(stuId);
			stu.setName(stuName);
			rm = this.stuSev.modifyStudent(stu);
		}
		response.getWriter().print(rm.toJSONObj());
	}

	private void deleteStudent(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String strStuId = request.getParameter("stuId");
		int stuId = -1;
		ResultMessage rm = null;
		if(RegexUtil.isNumStr(strStuId) && (stuId = Integer.parseInt(strStuId)) > 0){
			rm = this.stuSev.deleteStudent(stuId);
		}else{
			rm = ResultMessageFactory.getErrorResult("参数异常，请重试");
		}
		
		response.getWriter().println(rm.toJSONObj());
	}

	private void addStudent(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strClassId = request.getParameter("classId");
		int classId = -1;
		String stuName = request.getParameter("stuName");
		String stuNum = request.getParameter("stuNum");

		ResultMessage rm = null;
		if (RegexUtil.isNumStr(strClassId)) {
			classId = Integer.parseInt(strClassId);
		}
		if (classId <= 0) {
			rm = ResultMessageFactory.getErrorResult("参数异常，请稍后再试");
		} else {
			if (StringUtils.isBlank(stuName)) {
				rm = ResultMessageFactory.getErrorResult("学生姓名不能为空");
			} else if (StringUtils.isBlank(stuNum)) {
				rm = ResultMessageFactory.getErrorResult("学号不能为空");
			} else {
				Student stu = new Student();
				stu.setName(stuName);
				stu.setNum(stuNum);
				stu.setPassword(stuNum);
				stu.setClas(new Clas(classId));
				rm = this.stuSev.addStudent(stu);
			}
		}

		response.getWriter().print(rm.toJSONObj());

	}

	private void showPage(HttpServletRequest request,
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
		String strDeptId = request.getParameter("deptId");
		String strMajorId = request.getParameter("majorId");
		String strClassId = request.getParameter("classId");
		int classId = -1;
		int majorId = -1;
		int deptId = -1;
		String conditions = null;
		Object[] params = null;
		if (RegexUtil.isNumStr(strClassId)
				&& (classId = Integer.parseInt(strClassId)) >= 0) {
			if (classId == 0) {
				if (RegexUtil.isNumStr(strMajorId)
						&& (majorId = Integer.parseInt(strMajorId)) >= 0) {
					if (majorId == 0) {
						if (RegexUtil.isNumStr(strDeptId)
								&& (deptId = Integer.parseInt(strDeptId)) > 0) {
							conditions = " DEPT_ID = ? ";
							params = new Object[] { deptId };
						}
					}else {
						conditions = " MAJOR_ID = ?";
						params = new Object[] { majorId };
					}
				} 
			} else {
				conditions = " CLS_ID = ?";
				params = new Object[] { classId };
			}
		}
		try {

			PageContent<Student> stus = stuSev.showStudents(conditions, params, page);

			String isAjaxPost = request.getParameter("ajaxPost");
			// 翻页采用ajax
			if (null != isAjaxPost && "T".equals(isAjaxPost)) {
				response.getWriter().print(stus.toJSONObj());
			} else {
				 List<Department> depts = deptSev.getDeparts(null, null);
				 request.setAttribute("cata", stus.toJSONObj());
				 request.setAttribute("filter", depts);
				 request.setAttribute("pageInfo", stus.getPage());
				 request.setAttribute("target", "T");
				request.setAttribute("include", "/page_admin/student.jsp");
				super.gotoTemplatePage(request, response,
						"page_admin/template.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
