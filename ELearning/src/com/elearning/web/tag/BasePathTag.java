package com.elearning.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.core.path.util.PathUtil;

/**
 * 默认路径标签
 * 生成<base />标签，规定链接默认路径(绝对路径: http://xxx:8080/xxx/)
 * @author Administrator
 *
 */
public class BasePathTag extends BaseTag {

	private static final long serialVersionUID = -9161260205377123325L;
	@Override
	public int doStartTag() throws JspException {
		String bathPath = PathUtil.getBasePath(super.req);
		JspWriter out = super.pageContext.getOut();
		try {
			out.print("<base href='" + bathPath + "' />");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	
	
}
