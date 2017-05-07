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

import edu.ecut.entity.User;

@WebServlet( "/explain.do" )
public class ExplainServlet  extends HttpServlet {

	private static final long serialVersionUID = 1598467122671011919L;

	@Override
	protected void service( HttpServletRequest request , HttpServletResponse response ) 
			throws ServletException, IOException {
		
		// 获得那个已经登录的用户 ( 其实就是解答者 ) 的 主键
		HttpSession session = request.getSession();
		User user = ( User )session.getAttribute( "user" );
		if( user != null ) { 
			int userId = user.getId() ;
			// 接受来自页面的数据
			String content = request.getParameter( "content" ); // 获得解答的内容
			String topicId = request.getParameter( "id" ); // 获得被解答的问题的主键
			
			// 获得 客户端 的 IP 地址
			String ip = request.getRemoteAddr();
			System.out.println( "ip : " + ip );
			
			// 获得 从 历元 到当前时刻所经历的毫米数
			long ms = System.currentTimeMillis();
			Timestamp currentTime = new Timestamp( ms ) ;
			
			final String SQL = "INSERT INTO t_explain ( content , explain_time , explain_ip , user_id , topic_id ) VALUES ( ? , ? , ? , ? , ? )" ;
			
			int n = JdbcHelper.insert(SQL, false, content , currentTime , ip , userId , Integer.parseInt( topicId ) );
			
			if( n > 0 ) {
				response.sendRedirect( request.getContextPath() + "/list.do" );
			} else {
				// 咋整?
			}
			
		} else {
			// 咋整?
		}
		
	}

}
