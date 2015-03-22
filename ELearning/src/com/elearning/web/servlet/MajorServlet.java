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
import com.elearning.domain.Department;
import com.elearning.domain.Major;
import com.elearning.service.IDepartService;
import com.elearning.service.IMajorService;
import com.elearning.service.impl.DepartServiceImpl;
import com.elearning.service.impl.MajorServiceImpl;

public class MajorServlet extends BaseServlet {

	private static final long serialVersionUID = -2541205669308588469L;
	private IMajorService majorSev = new MajorServiceImpl();	
	private IDepartService deptSev = new DepartServiceImpl();
	
	@Override
	protected void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String method = request.getParameter("method");
		if("showPage".equals(method)){
			this.showPage(request, response);
		}else if("addMajor".equals(method)){
			this.addMajor(request, response);
		}else if("deleteMajor".equals(method)){
			this.deleteMajor(request, response);
		}
	}
	
	/**
	 * 删除院系
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void deleteMajor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String strMajorId = request.getParameter("majorId");
		int intMajorId = -1;
		if(RegexUtil.isNumStr(strMajorId)){
			intMajorId = Integer.parseInt(strMajorId);
		}
		ResultMessage result = null;
		if (intMajorId == -1) {
			result = ResultMessageFactory.getErrorResult("参数异常，请稍后再试");
		}else{
			result = majorSev.deleteMajor(intMajorId);
		}
		response.getWriter().println(result.toJSONObj());
	}

	/**
	 * 添加专业
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void addMajor(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String deptId = request.getParameter("deptId");
		String majorName = request.getParameter("majorName");

		ResultMessage result = null;
		int intDeptId = -1;
		try {
			if (RegexUtil.isNumStr(deptId)) {
				intDeptId = Integer.parseInt(deptId);
			}
			if (intDeptId == -1) {
				result = ResultMessageFactory.getErrorResult("参数异常，请稍后再试");
			} else if (StringUtils.isBlank(majorName)) {
				result = ResultMessageFactory.getWarningResult("专业名称不能为空");
			} else {
				Major major = new Major();
				major.setName(majorName);
				major.setDept(new Department(intDeptId));
				boolean isExist = majorSev.isExist(intDeptId, majorName);
				if(isExist){
					result = ResultMessageFactory.getWarningResult("此专业已存在");
				}else{
					if(majorSev.addMajor(major)){
						result = ResultMessageFactory.getSuccessResult("添加成功");
					}else{
						result = ResultMessageFactory.getErrorResult("添加失败");
					}
				}
			}
		} catch (Exception e) {
			result = ResultMessageFactory.getErrorResult("网络异常，请稍后再试");
			e.printStackTrace();
		}
		response.getWriter().println(result.toJSONObj());
	}
	/**
	 * 显示专业信息
	 * @param request
	 * @param response
	 */
	private void showPage(HttpServletRequest request,
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
		String strDeptId = request.getParameter("deptId");
		String conditions = null;
		Object[] params = null;
		if(RegexUtil.isNumStr(strDeptId) && Integer.parseInt(strDeptId) >= 0){
			conditions = " F_DEPT_ID = ?";
			params = new Object[]{Integer.parseInt(strDeptId)};
		}
		try {
			
			PageContent<Major> majors = majorSev.showMajors(conditions, params, page);
			List<Department> depts = deptSev.getDeparts(null, null);
			
			String isAjaxPost = request.getParameter("ajaxPost");
			//翻页采用ajax
			if(null != isAjaxPost && "T".equals(isAjaxPost)){
				response.getWriter().print(majors.toJSONObj());
			}else{
				request.setAttribute("cata", majors);
				request.setAttribute("filter", depts);
				request.setAttribute("pageInfo", majors.getPage());
				//request.setAttribute("target", "T");
				request.setAttribute("include", "/page_admin/major.jsp");
				super.gotoTemplatePage(request, response, "page_admin/template.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
