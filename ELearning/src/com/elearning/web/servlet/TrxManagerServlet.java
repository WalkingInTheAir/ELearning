package com.elearning.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.path.util.WebUtil;

public class TrxManagerServlet extends BaseServlet {

	private static final long serialVersionUID = -9047483917560109366L;

	@Override
	protected void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String trxStatus = request.getParameter("trxStatus");
		
		WebUtil.copyRequestParam2Attr(request, new String[] {"menuIndex", "itemIndex", "tabIndex" });
		
		if(null == request.getAttribute("include")){
			//将request中的Parameter参数拷贝到Attribute中
			WebUtil.copyRequestParam2Attr(request, "include");
		}
		if(null == request.getAttribute("target")){
			WebUtil.copyRequestParam2Attr(request, "target");
		}
		
		String target = (String) request.getAttribute("target");
		String url = (String) request.getAttribute("include");
		String template = "/page_admin/template.jsp";	//TODO
		
		if("S".equalsIgnoreCase(target)){
			super.invokeServlet(request, response, url);
		}else if("P".equalsIgnoreCase(target)){
			super.gotoPage(request, response, url);
		}else if("T".equalsIgnoreCase(target)){
			super.gotoTemplatePage(request, response, template);
		}
	}

}