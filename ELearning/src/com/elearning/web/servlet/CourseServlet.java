package com.elearning.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.jdbc.bean.PageInfo;
import com.core.regex.util.RegexUtil;

public class CourseServlet extends BaseServlet {

	private static final long serialVersionUID = -8403892901312764484L;

	@Override
	protected void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
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
		}
		return;
	}

	private void modifyCourse(HttpServletRequest request,
			HttpServletResponse response) {
		
	}

	private void deleteCourse(HttpServletRequest request,
			HttpServletResponse response) {
		
	}

	private void showCourses(HttpServletRequest request,
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
		try {

			//PageContent<CourseType> types = this.ctSev.showTypes(page);
			String isAjaxPost = request.getParameter("ajaxPost");
			// 翻页采用ajax
			if (null != isAjaxPost && "T".equals(isAjaxPost)) {
				//response.getWriter().print(types.toJSONObj());
			} else {
				//request.setAttribute("cata", types);
				//request.setAttribute("pageInfo", types.getPage());
				request.setAttribute("include", "/page_admin/course.jsp");
				super.gotoTemplatePage(request, response,
						"page_admin/template.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addCourses(HttpServletRequest request,
			HttpServletResponse response) {
	}

	private void addCourse(HttpServletRequest request,
			HttpServletResponse response) {
	}

}
