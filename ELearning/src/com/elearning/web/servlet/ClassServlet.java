package com.elearning.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.elearning.domain.Department;

public class ClassServlet extends BaseServlet {
       
	private static final long serialVersionUID = -201581832077483347L;

	@Override
	protected void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String method = request.getParameter("method");
		if("showPage".equals(method)){
			this.showPage(request, response);
		}
	}
	/**
	 * 显示班级信息
	 * @param request
	 * @param response
	 */
	private void showPage(HttpServletRequest request,
			HttpServletResponse response) {
		PageInfo page = new PageInfo();
		String rex = "^[1-9][0-9]*$";
		String currPageStr = request.getParameter("currPage");
		String pageSizeStr = request.getParameter("pageSize");
		if(null != currPageStr && currPageStr.matches(rex)){
			page.setCurrPage(Integer.parseInt(currPageStr));
		}
		if(null != pageSizeStr && pageSizeStr.matches(rex)){
			page.setPageSize(Integer.parseInt(pageSizeStr));
		}
		try {
			PageContent<Department> depts = null;//service.showDeparts(page);
			
			String isAjaxPost = request.getParameter("ajaxPost");
			//翻页采用ajax
			if(null != isAjaxPost && "T".equals(isAjaxPost)){
				response.getWriter().print(depts.toJSONObj());
			}else{
				request.setAttribute("cata", null);
				request.setAttribute("pageInfo", null);
				//request.setAttribute("target", "T");
				request.setAttribute("include", "/page_admin/classes.jsp");
				super.gotoTemplatePage(request, response, "page_admin/template.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
