package com.elearning.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * Servlet顶级父类 所有Servlet都必须继承该类，并实现抽象方法
 * 
 * @author Administrator
 *
 */
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected String method;
	public BaseServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		this.method = request.getParameter("method");
		if(StringUtils.isBlank(method)){
			//GOTO ERROR PAGE;
		}
		try {
			this.perfom(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected abstract void perfom(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	protected void invokeServlet(HttpServletRequest req,
			HttpServletResponse res, String url) throws Exception {
		req.setAttribute("target", "S");
		req.getSession().getServletContext().getRequestDispatcher(url).forward(req, res);
		
	}

	protected void gotoPage(HttpServletRequest req, HttpServletResponse res,
			String url) throws Exception {
		req.setAttribute("target", "P");
		req.getRequestDispatcher(url).forward(req, res);
	}

	protected void gotoTemplatePage(HttpServletRequest req,
			HttpServletResponse res, String template) throws Exception {
		req.setAttribute("target", "T");
		req.getRequestDispatcher(template).forward(req, res);
	}
}
