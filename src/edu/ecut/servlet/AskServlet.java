package edu.ecut.servlet;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.malajava.util.JdbcHelper;
import org.malajava.util.StringHelper;

import edu.ecut.entity.User;

/**
 *  Servlet 是 运行在 Java EE 服务器上的 Java 小程序，
 *  负责对 HTTP 请求 ( request ) 做出响应 ( response ) 。
 */
@WebServlet( "/ask.do" )
public class AskServlet   extends HttpServlet {

	private static final long serialVersionUID = -4777642507114213231L;

	@Override
	protected void service( HttpServletRequest request , HttpServletResponse response ) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute( "user" ); // 在登录时将 User 对象放入了 会话 中
		
		// 先检查用户是否已经登录 ( 看 会话 中是否 拥有 User 对象 )
		if( user != null ) {
			// 接受来自页面的数据
			String title = request.getParameter( "title" );
			String content = request.getParameter( "content" );
			
			System.out.println( "title : " + title );
			System.out.println( "content : " + content );
			
			// 判断 title 和 content 是否不为 空 ( 不等于 null 不是空白字符串， 也不是空串)
			if( StringHelper.notEmpty( title ) && StringHelper.notEmpty( content ) ){
				// 获得 客户端 的 IP 地址
				String ip = request.getRemoteAddr();
				System.out.println( "ip : " + ip );
				
				// 获得 从 历元 到当前时刻所经历的毫米数
				long ms = System.currentTimeMillis();
				Timestamp currentTime = new Timestamp( ms ) ;
				
				final String SQL = "INSERT INTO t_topic ( title , content , publish_time , publish_ip , user_id ) VALUES ( ? , ? , ? , ? , ? )" ;
				int n = JdbcHelper.insert(SQL, false, title , content , currentTime , ip ,  user.getId() );
				
				if( n > 0 ){
					// 如果提问成功，则去往列表页面
					response.sendRedirect( request.getContextPath() + "/list.do" );
				} else {
					// 用户已经登录了，但是没有填入 title 和 content
					session.setAttribute( "askFail" , "提问失败了" );
					response.sendRedirect( request.getContextPath() + "/ask.html" );
				}
				
			} else {
				// 用户已经登录了，但是没有填入 title 和 content
				session.setAttribute( "askFail" , "标题和内容都不能为空" );
				response.sendRedirect( request.getContextPath() + "/ask.html" );
			}
		
		} else {
			// 用户没有登录，需要给出提示并回到登录页面去
			session.setAttribute( "loginFail" , "没有登录不能提问，请先登录" );
			response.sendRedirect( request.getContextPath() + "/login.html" );
		}
		
	}

}
