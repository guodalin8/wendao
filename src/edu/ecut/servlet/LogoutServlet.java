package edu.ecut.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet( "/logout.do" )
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 929888180451471816L;

	@Override
	protected void service( HttpServletRequest request , HttpServletResponse response ) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		// 将 当前会话废弃 ( 其中的数据也就丢失了 )
		session.invalidate();
		
		// 注销后去往 列表页面 ( list.html 只负责显示，list.do 负责查询 )
		response.sendRedirect( request.getContextPath() + "/list.do" );
		
	}

}
