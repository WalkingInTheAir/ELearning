package com.elearning.web.servlet;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.core.util.regex.RegexUtil;
import com.elearning.domain.Department;
import com.elearning.service.IDepartService;
import com.elearning.service.impl.DepartServiceImpl;
import com.elearning.util.upload.UploadExcelToBeanFactory;
import com.elearning.util.upload.UploadExcelUtil;


public class DepartServlet extends BaseServlet{

	private static final long serialVersionUID = -6577785592526776692L;
	private IDepartService service = new DepartServiceImpl();
	@Override
	protected void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if("addDepart".equals(method)){
			addDepart(request, response);
		}else if("addDeparts".equals(method)){
			addDeparts(request, response);
		}else if("showPage".equals(method)){
			showPageDeparts(request, response);
		}else if("deleteDept".equals(method)){
			deleteDept(request, response);
		}else if("modifyDept".equals(method)){
			modifyDept(request, response);
		}
		return;
	}

	/**
	 * 删除部门信息
	 * @param request
	 * @param response
	 * @throws JSONException 
	 * @throws IOException 
	 */
	private void modifyDept(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String strDeptId = request.getParameter("deptId");
		String strDeptName = request.getParameter("deptName");
		
		ResultMessage msg = null;
		int deptId = -1;
		if(RegexUtil.isNumStr(strDeptId)){
			deptId = Integer.parseInt(strDeptId);
		}
		try {
			if (deptId > -1) {
				Department dept = new Department(deptId, strDeptName);
				msg = service.modifyDept(dept);
				
			} else {
				msg = ResultMessageFactory.getErrorResult("请求异常，请稍后再试！");
			}
		} catch (Exception e) {
			msg = ResultMessageFactory.getErrorResult("请求异常，请稍后再试！");
			e.printStackTrace();
		}
		response.getWriter().print(msg.toJSONObj());
	}

	/**
	 * 删除部门
	 * @param request
	 * @param response
	 * @throws JSONException 
	 * @throws IOException 
	 */
	private void deleteDept(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strDeptId = request.getParameter("deptId");
		int deptId = -1;
		if(RegexUtil.isNumStr(strDeptId)){
			deptId = Integer.parseInt(strDeptId);
		}
		ResultMessage result = null;
		try {
			if(deptId > -1){
				result = service.deleteDept(deptId);
			}else{
				result = ResultMessageFactory.getErrorResult("请求异常，请稍后再试！");
			}
		} catch (Exception e) {
			result = ResultMessageFactory.getErrorResult("系统错误，请联系我们");
			e.printStackTrace();
		}
		
		response.getWriter().print(result.toJSONObj());
	}

	/**
	 * 分页显示院系信息
	 * @param request
	 * @param response
	 */
	private void showPageDeparts(HttpServletRequest request,
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
			PageContent<Department> depts = service.showDeparts(page);
			
			String isAjaxPost = request.getParameter("ajaxPost");
			//翻页采用ajax
			if(null != isAjaxPost && "T".equals(isAjaxPost)){
				response.getWriter().print(depts.toJSONObj());
			}else{
				request.setAttribute("cata", depts);
				request.setAttribute("pageInfo", depts.getPage());
				//request.setAttribute("target", "T");
				request.setAttribute("include", "/page_admin/department.jsp");
				super.gotoTemplatePage(request, response, "page_admin/template.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量添加院系信息
	 * @param request
	 * @param response
	 */
	private void addDeparts(HttpServletRequest request,
			HttpServletResponse response) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try {
			PrintWriter out = response.getWriter();
			if (isMultipart) {
				List<List<String>> fileValue = null;
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// 设置文件的下限
				factory.setSizeThreshold(4096);
				// 设置中转目录
				// factory.setRepository(tempPath);

				ServletFileUpload upload = new ServletFileUpload(factory);
				// 设置上传文件的上限
				upload.setSizeMax(1000000 * 20);
				List<FileItem> fileItems = upload.parseRequest(request);
				Iterator<FileItem> iters = fileItems.iterator();
				while(iters.hasNext()){
					FileItem item = iters.next();
					if(item.isFormField()){
						//TODO
						continue;
					}else{
						System.out.println("file name: " + item.getName());
						InputStream in = item.getInputStream();
						fileValue = this.parseFileItem(in);
					}
				}
				if(fileValue != null && !fileValue.isEmpty()){
					List<Department> depts = UploadExcelToBeanFactory.conToDept(fileValue);
					ResultMessage msg = service.addDeparts(depts);
					out.println(msg.toJSONObj());
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加单个院系信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void addDepart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		String departName = request.getParameter("departName");
		System.out.println(departName);
		ResultMessage msg;
		if(StringUtils.isBlank(departName)){
			msg = ResultMessageFactory.getWarningResult("院系名称不能为空");
		}else{
			Department dept = new Department(departName);
			msg = service.addDepart(dept);
		}
		out.println(msg.toJSONObj());
//		request.setAttribute("msg", msg.getJSONResult());
//		request.setAttribute("include", "department.jsp");
//		super.gotoTemplatePage(request, response, "/page_admin/template.jsp");
		return;
	}

	private List<List<String>> parseFileItem(InputStream in) throws Exception {
		Workbook wb = WorkbookFactory.create(in);
		int numOfSheets = wb.getNumberOfSheets();
		List<List<String>> sheetValue = new ArrayList<List<String>>();
		for (int i = 0; i < numOfSheets; i++) {
			Sheet sheet = wb.getSheetAt(i);
			int numOfCols = sheet.getLastRowNum() + 1;
			for (int j = 0; j < numOfCols; j++) {
				Row row = sheet.getRow(j);
				if (row == null) {
					continue;
				}
				int numOfCells = row.getLastCellNum();
				List<String> rowValue = new ArrayList<String>();
				for (int k = 0; k < numOfCells; k++) {
					Cell cell = row.getCell(k);
					if (null == cell) {
						continue;
					}
					String value = UploadExcelUtil.getCellValue(cell);
					rowValue.add(value);
				}
				if (!rowValue.isEmpty()) {
					sheetValue.add(rowValue);
				}
			}
		}
		return sheetValue;
	}
}
