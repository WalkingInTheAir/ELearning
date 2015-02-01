package com.elearning.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
/**
 * 自定义标签顶级父类，包含了一些通用属性和方法
 * 所有自定义标签必须继承自此类
 * @author Administrator
 *
 */
public abstract class BaseTag extends BodyTagSupport {
	private static final long serialVersionUID = 5135796841946585397L;
	
	protected PageContext pageContext;
	protected HttpServletRequest req;
	protected HttpServletResponse res;
	
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
		this.req = (HttpServletRequest) pageContext.getRequest();
		this.res = (HttpServletResponse) pageContext.getResponse();
	}

}
