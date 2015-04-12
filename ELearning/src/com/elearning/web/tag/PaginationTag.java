package com.elearning.web.tag;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;


public class PaginationTag extends BaseTag {
	private static final long serialVersionUID = 1L;
	//用户自定义样式
	private String style;
	
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}


	@Override
	public int doEndTag() throws JspException {
		int currPage = 1;
		int totalPage = 10;
		StringBuffer out = new StringBuffer();
		try {
			super.pageContext.include("../template" + File.separator + "Pagination.jsp");
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}
	
	
}
