package com.elearning.web.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.regex.util.RegexUtil;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.elearning.domain.CourseType;
import com.elearning.service.ICourseTypeService;
import com.elearning.service.impl.CourseTypeServiceImpl;

public class CourseTypeServlet extends BaseServlet {
	private static final long serialVersionUID = 2333960522472959317L;

	private ICourseTypeService ctSev = new CourseTypeServiceImpl();
	
	@Override
	protected void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String method = request.getParameter("method");
		if ("addType".equals(method)) {
			addType(request, response);
		} else if ("addTypes".equals(method)) {
			addTypes(request, response);
		} else if ("showPage".equals(method)) {
			showTypes(request, response);
		} else if ("deleteType".equals(method)) {
			deleteDept(request, response);
		} else if ("modifyType".equals(method)) {
			modifyType(request, response);
		}
		return;
	}

	private void modifyType(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strTypeId = request.getParameter("typeId");
		String typeName = request.getParameter("typeName");
		
		ResultMessage msg = null;
		int typeId = -1;
		if(RegexUtil.isNumStr(strTypeId)){
			typeId = Integer.parseInt(strTypeId);
		}
		try {
			if (typeId > -1) {
				CourseType type = new CourseType(typeId, typeName);
				msg = this.ctSev.modifyType(type);
				
			} else {
				msg = ResultMessageFactory.getErrorResult("请求异常，请稍后再试！");
			}
		} catch (Exception e) {
			msg = ResultMessageFactory.getErrorResult("请求异常，请稍后再试！");
			e.printStackTrace();
		}
		response.getWriter().print(msg.toJSONObj());
	}

	private void deleteDept(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String strTypeId = request.getParameter("typeId");
		int typeId = -1;
		if(RegexUtil.isNumStr(strTypeId)){
			typeId = Integer.parseInt(strTypeId);
		}
		ResultMessage result = null;
		try {
			if(typeId > -1){
				result = this.ctSev.deleteType(typeId);
			}else{
				result = ResultMessageFactory.getErrorResult("请求异常，请稍后再试！");
			}
		} catch (Exception e) {
			result = ResultMessageFactory.getErrorResult("系统错误，请联系我们");
			e.printStackTrace();
		}
		
		response.getWriter().print(result.toJSONObj());
	}

	private void showTypes(HttpServletRequest request,
			HttpServletResponse response) {

		PageInfo page = new PageInfo();
		String currPageStr = request.getParameter("currPage");
		String pageSizeStr = request.getParameter("pageSize");
		if(RegexUtil.isNumStr(currPageStr)){
			page.setCurrPage(Integer.parseInt(currPageStr));
		}
		if(RegexUtil.isNumStr(pageSizeStr)){
			page.setPageSize(Integer.parseInt(pageSizeStr));
		}
		try {
			
			PageContent<CourseType> types = this.ctSev.showTypes(page);
			String isAjaxPost = request.getParameter("ajaxPost");
			//翻页采用ajax
			if(null != isAjaxPost && "T".equals(isAjaxPost)){
				response.getWriter().print(types.toJSONObj());
			}else{
				request.setAttribute("cata", types);
				request.setAttribute("pageInfo", types.getPage());
				request.setAttribute("include", "/page_admin/courseType.jsp");
				super.gotoTemplatePage(request, response, "page_admin/template.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	private void addTypes(HttpServletRequest request,
			HttpServletResponse response) {

	}

	private void addType(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String typeName = request.getParameter("typeName");
		ResultMessage rm = null;

		if (null == typeName || typeName.trim().length() == 0) {
			rm = ResultMessageFactory.getErrorResult("课程类型名称不能为空");
		} else {
			CourseType type = new CourseType();
			type.setName(typeName);
			rm = ctSev.addType(type);
		}

		response.getWriter().print(rm.toJSONObj());

	}

}
