package edu.ecut.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.malajava.util.JdbcHelper;
import org.malajava.util.StringHelper;

import edu.ecut.entity.User;

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 18854422651747352L;

	@Override
	protected void service( HttpServletRequest request , HttpServletResponse response ) 
			throws ServletException, IOException {
		
		// 获得来自 页面 表单上的数据
		String verifyCode = request.getParameter( "verifyCode" ) ; // 获得由用户输入的那个验证码
		String username = request.getParameter( "username" ) ;
		String password = request.getParameter( "password" ) ;
		
		System.out.println( "verifyCode : " + verifyCode );
		System.out.println( "username : " + username );
		System.out.println( "password : " + password );
		
		HttpSession session = request.getSession();
		// 获得 在 会话 中存储的那个 为登录进行验证的 验证码
		final String code = (String)session.getAttribute( "/wendao/verify/login.do" );
		System.out.println( "session code : " + code );
		
		// 比较用户已输入的验证码是否正确 ( 就是跟 session 中存储的那个 验证码 进行比较 )
		if( StringHelper.equals( verifyCode , code ) ) {
			
			// 登录 : 根据 用户名 和 密码 从数据库中查询数据，如果都正确，就将这些数据放入到会话中，最后进入到指定页面( list.html )
			String SQL = "SELECT id , username , password FROM t_user WHERE username = ? and password = ? " ;
			ResultSet rs = JdbcHelper.query( SQL,  username , password ) ;
			
			try{
				// 如果查询到数据，就包装到一个对象中
				if( rs.next() ) {
					User user = new User(); // 创建对象
					
					// 封装数据
					user.setId( rs.getInt( 1 ) );
					user.setUsername( rs.getString( 2 ));
					user.setPassword( rs.getString( 3 ) ) ;
					
					/** 将 User 对象 放入到 会话中 **/
					session.setAttribute( "user" , user );
					// 重定向到 list.do ( list.do 会先查询数据后 再 重定向到 list.html )
					response.sendRedirect( request.getContextPath() + "/list.do" );
				} else {
					// 如果 用户名 或 密码 错误，重新返回到 登录页面
					response.sendRedirect( request.getContextPath() + "/login.html" );
				}
			} catch ( SQLException e ){
				e.printStackTrace();
			}
			
		} else {
			// 如果验证码输入错误，重新返回到 登录页面
			response.sendRedirect( request.getContextPath() + "/login.html" );
		}

	}
       
}
