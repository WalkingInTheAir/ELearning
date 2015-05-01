package com.elearning.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.core.util.date.DateUtil;
import com.core.util.path.PathUtil;
import com.core.util.regex.RegexUtil;
import com.elearning.domain.CourseWare;
import com.elearning.domain.User;
import com.elearning.service.ICourseWareService;
import com.elearning.service.impl.CourseWareServiceImpl;

public class CourseWareServlet extends BaseServlet {

	private static final long serialVersionUID = -4846184935357194287L;
	private ICourseWareService cwService = new CourseWareServiceImpl();
	@Override
	protected void perfom(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if("addCourseWare".equals(method)){
			this.addCourseWare(request, response);
		}else if("download".equals(method)){
			this.download(request, response);
		}else if("delete".equals(method)){
			this.delete(request, response);
		}
		

	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String strCWId = request.getParameter("courseWareId");
		int cwId = RegexUtil.isNumStr(strCWId) ? Integer.parseInt(strCWId) : -1;
		ResultMessage rm = null;
		if(cwId < 1){
			rm = ResultMessageFactory.getErrorResult("参数异常，请重试");
		}else{
			rm = this.cwService.delete(cwId);
		}
		
		response.getWriter().println(rm.toJSONObj());
	}

	private void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String strCWId = request.getParameter("courseWareId");
		int cwId = RegexUtil.isNumStr(strCWId) ? Integer.parseInt(strCWId) : -1;
		CourseWare cw = this.cwService.getCourseWareById(cwId);
		try{
			File f = new File(PathUtil.getUploadPath(PathUtil.UploadPathKey.COURSEWARE)
					+ File.separator + cw.getCourseId() + File.separator + cw.getName());
			
			response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(cw.getOrigName().getBytes("UTF-8"), "ISO8859-1") + "\""); 
			
			FileInputStream in = new FileInputStream(f);
			OutputStream out = response.getOutputStream();
			byte []buff = new byte[1024];
			int n = -1;
			while((n=in.read(buff)) > 0){
				out.write(buff);
			}
			out.flush();
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void addCourseWare(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		PrintWriter out = response.getWriter();
		ResultMessage rm = null;
		try {
			String strCourseId = null;
			InputStream in = null;
			String origFileName = null;
			if (isMultipart) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// 设置文件的下限
				factory.setSizeThreshold(4096);

				ServletFileUpload upload = new ServletFileUpload(factory);
				// 设置上传文件的上限
				upload.setSizeMax(1000000 * 20);
				List<FileItem> fileItems = upload.parseRequest(request);
				Iterator<FileItem> iters = fileItems.iterator();
				while(iters.hasNext()){
					FileItem item = iters.next();
					if(item.isFormField()){
						String fieldName = item.getFieldName();
						if("courseId".equals(fieldName)){
							strCourseId = item.getString();
						}
					}else{
						System.out.println("file name: " + item.getName());
						origFileName = item.getName();
						in = item.getInputStream();
					}
				}
				
				if (strCourseId != null && null != in && null != origFileName) {
					int courseId = RegexUtil.isNumStr(strCourseId)
							? Integer.parseInt(strCourseId)
							: -1;
					String suffix = origFileName.substring(origFileName.lastIndexOf("."));
					long timemills = System.currentTimeMillis();
					String fileName = timemills + suffix;
					File location = new File(PathUtil.getUploadPath(PathUtil.UploadPathKey.COURSEWARE) + File.separator +courseId);
					if(!location.exists()){
						location.mkdirs();
					}
					File file = new File(location, fileName);
					FileOutputStream fos = new FileOutputStream(file);
					byte[] b = new byte[1024];
					int n = 0;
					while ((n = in.read(b)) != -1) {
						fos.write(b, 0, n);
					}
					fos.close();
					in.close();
					CourseWare cw = new CourseWare();
					cw.setCourseId(courseId);
					cw.setName(fileName);
					cw.setTime(DateUtil.getTime());
					cw.setOrigName(origFileName);
					int teacherId = ((User) request.getSession(false).getAttribute("user")).getId();
					cw.setTeacherId(teacherId);
					rm = this.cwService.addCourseWare(cw);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("上传失败");
		}
		out.print(rm.toJSONObj());
	}

}
