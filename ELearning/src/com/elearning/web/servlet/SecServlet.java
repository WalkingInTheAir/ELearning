package com.elearning.web.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.elearning.domain.User;
import com.elearning.service.IUserService;
import com.elearning.service.impl.UserServiceImpl;

public class SecServlet extends BaseServlet {

	private static final long serialVersionUID = 5648566968704748804L;
	private IUserService uSer = new UserServiceImpl();
	@Override
	protected void perfom(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if("login".equals(method)){
			this.login(request, response);
		}else if("logout".equals(method)){
			this.logout(request, response);
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.getWriter().println("<script type=\"text/javascript\">window.location.href='login.html'</script>");
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String num = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		
		User u = new User();
		u.setNum(num);
		u.setPassword(password);
		u.setRole(role);
		if(uSer.isExist(u)){
			HttpSession session = request.getSession();
			session.setAttribute("user", u);
			response.sendRedirect("TrxManager");
		}else{
			response.getWriter().println("<script type='text/javascript'>alert('用户名或密码错误'); window.location.href='login.html'</script>");
		}
	}

}
