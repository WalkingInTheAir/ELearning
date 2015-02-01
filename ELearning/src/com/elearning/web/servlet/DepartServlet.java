package com.elearning.web.servlet;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.elearning.domain.Department;
import com.elearning.service.IDepartService;
import com.elearning.service.impl.DepartServiceImpl;
import com.elearning.web.bean.ResultMessage;
import com.elearning.web.bean.ResultMessageFactory;


public class DepartServlet extends BaseServlet{

	private static final long serialVersionUID = -6577785592526776692L;
	private IDepartService service = new DepartServiceImpl();
	@Override
	protected void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String method = request.getParameter("method");
		if("addDepart".equals(method)){
			addDepart(request, response);
		}else if("addDeparts".equals(method)){
			addDeparts(request, response);
		}
		
		return;
	}

	private void addDeparts(HttpServletRequest request,
			HttpServletResponse response) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try {
			if (isMultipart) {
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
						
					}else{
						System.out.println("file name: " + item.getName());
						InputStream in = item.getInputStream();
						this.parseFileItem(in);
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addDepart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		String departName = request.getParameter("departName");
		System.out.println(departName);
		ResultMessage msg;
		if(StringUtils.isBlank(departName)){
			msg = ResultMessageFactory.getFailResult("院系名称不能为空");
		}else{
			Department dept = new Department(departName);
			msg = service.addDepart(dept);
		}
		out.println(msg.getJSONResult());
//		request.setAttribute("msg", msg.getJSONResult());
//		request.setAttribute("include", "department.jsp");
//		super.gotoTemplatePage(request, response, "/page_admin/template.jsp");
		return;
	}

	private void parseFileItem(InputStream in) throws Exception {
		Workbook wb = WorkbookFactory.create(in);
		int numOfSheets = wb.getNumberOfSheets();

		for (int i = 0; i < numOfSheets; i++) {

			Sheet sheet = wb.getSheetAt(i);
			int numOfCols = sheet.getLastRowNum() + 1;
			for (int j = 0; j < numOfCols; j++) {
				Row row = sheet.getRow(j);
				if (row == null) {
					continue;
				}
				int numOfCells = row.getLastCellNum();
				for (int k = 0; k < numOfCells; k++) {
					Cell cell = row.getCell(k);
					if (null == cell) {
						continue;
					}
					int cellType = cell.getCellType();
					String value = "";
					switch (cellType) {
					case Cell.CELL_TYPE_BOOLEAN:
						value = String.valueOf(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						
						value = String.valueOf(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_BLANK:
						;
					case Cell.CELL_TYPE_ERROR:
						;
					case Cell.CELL_TYPE_FORMULA:
						;
					default:
						value = "";
						break;
					}
					System.out.print(value + " ");
				}
				System.out.println();
			}

		}
	}
}
