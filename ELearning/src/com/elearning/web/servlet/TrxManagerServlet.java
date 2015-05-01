package com.elearning.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.util.path.WebUtil;
import com.elearning.domain.User;

public class TrxManagerServlet extends BaseServlet {

	private static final long serialVersionUID = -9047483917560109366L;

	@Override
	protected void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
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
		
		if("S".equalsIgnoreCase(target)){
			super.invokeServlet(request, response, url);
		}else if("P".equalsIgnoreCase(target)){
			super.gotoPage(request, response, url);
		}else{
			User u = (User) request.getSession().getAttribute("user");
			if("A".equals(u.getRole())){
				super.gotoTemplatePage(request, response, "/page_admin/template.jsp");
			}else if("T".equals(u.getRole())){
				super.invokeServlet(request, response, "/TeacherServlet");
			}else{
				super.invokeServlet(request, response, "/StudentServlet");
			}
		}
	}

}
