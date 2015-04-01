package com.elearning.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.regex.util.RegexUtil;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.elearning.domain.Clas;
import com.elearning.domain.Department;
import com.elearning.domain.Major;
import com.elearning.service.IClasService;
import com.elearning.service.IDepartService;
import com.elearning.service.impl.ClasServiceImpl;
import com.elearning.service.impl.DepartServiceImpl;

public class ClassServlet extends BaseServlet {

	private static final long serialVersionUID = -201581832077483347L;
	private IClasService clasServ = new ClasServiceImpl();
	private IDepartService deptServ = new DepartServiceImpl();
	
	@Override 
	protected void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if ("showPage".equals(method)) {
			this.showPage(request, response);
		}else if("addClass".equals(method)){
			this.addClass(request, response);
		}else if("deleteClass".equals(method)){
			this.deleteClass(request, response);
		}else if("mdfClass".equals(method)){
			this.mdfClass(request, response);
		}
	}

	
	private void mdfClass(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String strDeptId = request.getParameter("deptId");
		String strMajorId = request.getParameter("majorId");
		String strClassId =  request.getParameter("classId");
		String className = request.getParameter("className");
		
		int classId = RegexUtil.isNumStr(strClassId)? Integer.parseInt(strClassId) : -1;
		int deptId = RegexUtil.isNumStr(strDeptId)? Integer.parseInt(strDeptId) : -1;;
		int majorId = RegexUtil.isNumStr(strMajorId)? Integer.parseInt(strMajorId) : -1;
		
		ResultMessage rm = null;
		if(classId == -1 || deptId == -1 || majorId == -1){
			rm = ResultMessageFactory.getErrorResult("参数异常，请重试");
		}
		if(null == className || className.trim().length() == 0){
			rm = ResultMessageFactory.getErrorResult("班级名称不能为空");
		}
		
		if(null != rm){
			response.getWriter().println(rm.toJSONObj());
			return;
		}
		
		boolean isExist = this.clasServ.isExist(majorId, className);
		if(isExist){
			rm = ResultMessageFactory.getWarningResult("班级名称已经存在，请修改后重试");
		}else{
			Clas clas = new Clas();
			clas.setId(classId);
			clas.setName(className);
			rm = this.clasServ.mdfClass(clas);
		}
		response.getWriter().println(rm.toJSONObj());
	}


	private void deleteClass(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String strClassId = request.getParameter("classId");
		int classId = -1;
		ResultMessage rm = null;
		if(RegexUtil.isNumStr(strClassId)){
			classId = Integer.parseInt(strClassId);
		}
		
		if(classId < 0){
			rm = ResultMessageFactory.getErrorResult("参数异常，请稍后再试!");
		}else{
			rm = this.clasServ.deleteClass(classId);
		}
		
		response.getWriter().print(rm.toJSONObj());;
	}


	private void addClass(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String strMajorId = request.getParameter("majorId");
		String className = request.getParameter("className");

		ResultMessage result = null;
		int majorId = -1;
		try {
			if (RegexUtil.isNumStr(strMajorId)) {
				majorId = Integer.parseInt(strMajorId);
			}
			if (majorId == -1) {
				result = ResultMessageFactory.getErrorResult("参数异常，请稍后再试");
			} else if (StringUtils.isBlank(className)) {
				result = ResultMessageFactory.getWarningResult("班级名称不能为空");
			} else {
				boolean isExist = clasServ.isExist(majorId, className);
				if(isExist){
					result = ResultMessageFactory.getWarningResult("此班级已存在");
				}else{
					Clas clas = new Clas();
					clas.setName(className);
					clas.setMajor(new Major(majorId));
					result = clasServ.addClass(clas);
				}
			}
		} catch (Exception e) {
			result = ResultMessageFactory.getErrorResult("网络异常，请稍后再试");
			e.printStackTrace();
		}
		response.getWriter().println(result.toJSONObj());
	}
	/**
	 * 显示班级信息
	 * 
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
		String strMajorId = request.getParameter("majorId");
		String strDeptId = request.getParameter("deptId");
		String conditions = null;
		Object[] params = null;
		int majorId = -1;
		int deptId = -1;
		if(RegexUtil.isNumStr(strMajorId)){
			majorId = Integer.parseInt(strMajorId);
		}
		if(RegexUtil.isNumStr(strDeptId)){
			deptId = Integer.parseInt(strDeptId);
		}
		if(majorId == 0){
			conditions = " DEPT_ID = ?";
			params = new Object[]{deptId};
		}else if(majorId > 0){
			conditions = " MAJOR_ID = ?";
			params = new Object[]{majorId};
		}
		try {
			PageContent<Clas> clasz = this.clasServ.showClasz(conditions, params, page);
			
			String isAjaxPost = request.getParameter("ajaxPost");
			//翻页采用ajax
			if(null != isAjaxPost && "T".equals(isAjaxPost)){
				response.getWriter().print(clasz.toJSONObj());
			}else{
				List<Department> depts = this.deptServ.getDeparts(null, null);
				request.setAttribute("cata", clasz);
				request.setAttribute("filter", depts);
				request.setAttribute("pageInfo", clasz.getPage());
				request.setAttribute("include", "/page_admin/classes.jsp");
				super.gotoTemplatePage(request, response, "page_admin/template.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
