package com.elearning.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.core.util.regex.RegexUtil;
import com.elearning.domain.Course;
import com.elearning.domain.FAQ;
import com.elearning.service.IFAQService;
import com.elearning.service.impl.FAQServiceImpl;

public class FAQServlet extends BaseServlet {

	private static final long serialVersionUID = 3473267858243858830L;
	private IFAQService service = new FAQServiceImpl();
	@Override
	protected void perfom(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if("addFAQ".equals(method)){
			addFAQ(request, response);
		}else if("showPage".equals(method)){
			showPageFAQs(request, response);
		}else if("deleteFAQ".equals(method)){
			deleteFAQ(request, response);
		}else if("modifyFAQ".equals(method)){
			modifyFAQ(request, response);
		}
		return;
	}

	private void modifyFAQ(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String strFaqId = request.getParameter("faqId");
		String faqName = request.getParameter("faqName");
		String faqAnswer = request.getParameter("faqAnswer");
		
		ResultMessage msg = null;
		int faqId = -1;
		if(RegexUtil.isNumStr(strFaqId)){
			faqId = Integer.parseInt(strFaqId);
		}
		try {
			if (faqId > -1) {
				FAQ f = new FAQ();
				f.setId(faqId);
				f.setName(faqName);
				f.setAnswer(faqAnswer);
				msg = service.modifyFAQ(f);
				
			} else {
				msg = ResultMessageFactory.getErrorResult("请求异常，请稍后再试！");
			}
		} catch (Exception e) {
			msg = ResultMessageFactory.getErrorResult("请求异常，请稍后再试！");
			e.printStackTrace();
		}
		response.getWriter().print(msg.toJSONObj());
		
	}

	private void deleteFAQ(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String strFAQId = request.getParameter("faqId");
		int faqId = -1;
		if(RegexUtil.isNumStr(strFAQId)){
			faqId = Integer.parseInt(strFAQId);
		}
		ResultMessage result = null;
		try {
			if(faqId > -1){
				result = service.deleteFAQ(faqId);
			}else{
				result = ResultMessageFactory.getErrorResult("请求异常，请稍后再试！");
			}
		} catch (Exception e) {
			result = ResultMessageFactory.getErrorResult("系统错误，请联系我们");
			e.printStackTrace();
		}
		
		response.getWriter().print(result.toJSONObj());
		
	}

	private void showPageFAQs(HttpServletRequest request, HttpServletResponse response) {
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
			PageContent<FAQ> FAQS = service.showFAQs(page, courseId);
			String isAjaxPost = request.getParameter("ajaxPost");
			//翻页采用ajax
			if(null != isAjaxPost && "T".equals(isAjaxPost)){
				response.getWriter().print(FAQS.toJSONObj());
			}else{
				request.setAttribute("cata", FAQS);
				request.setAttribute("pageInfo", FAQS.getPage());
				super.gotoTemplatePage(request, response, (String)request.getAttribute("template"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	private void addFAQ(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String strCourseId = request.getParameter("courseId");
		int courseId = RegexUtil.isNumStr(strCourseId) ? Integer.parseInt(strCourseId) : -1;
		String faqName = request.getParameter("faqName");
		String faqAnswer = request.getParameter("faqAnswer");
		ResultMessage msg;
		if(courseId < 1){
			msg = ResultMessageFactory.getWarningResult("参数异常，请重试");
		}
		if(StringUtils.isBlank(faqName)){
			msg = ResultMessageFactory.getWarningResult("问题名称不能为空");
		}else if(StringUtils.isBlank(faqAnswer)){
			msg = ResultMessageFactory.getWarningResult("问题答案不能为空");
		}else{
			FAQ f = new FAQ();
			f.setName(faqName);
			f.setAnswer(faqAnswer);
			Course c = new Course();
			c.setId(courseId);
			f.setCourse(c);
			msg = service.addFAQ(f);
		}
		out.println(msg.toJSONObj());
	}

}
