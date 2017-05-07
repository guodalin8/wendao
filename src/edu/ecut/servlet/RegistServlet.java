package edu.ecut.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.malajava.util.JdbcHelper;
import org.malajava.util.StringHelper;

@WebServlet("/regist.do")
public class RegistServlet extends HttpServlet {

	private static final long serialVersionUID = 7493633832455111617L;

	@Override
	protected void service( HttpServletRequest request , HttpServletResponse response ) 
			throws ServletException, IOException {
		
		// 获得来自 页面 表单上的数据
		String verifyCode = request.getParameter( "verifyCode" ) ; // 获得由用户输入的那个验证码
		String username = request.getParameter( "username" ) ;
		String password = request.getParameter( "password" ) ;
		String confirm = request.getParameter( "confirm" ) ;
		
		System.out.println( "username : " + username );
		System.out.println( "password : " + password );
		System.out.println( "confirm : " + confirm );
		System.out.println( "verifyCode : " + verifyCode );
		
		HttpSession session = request.getSession();
		// 获得 在 会话 中存储的那个 为登录进行验证的 验证码
		final String code = (String)session.getAttribute( "/wendao/verify/regist.do" );
		System.out.println( "session code : " + code );
		
		// 比较验证码
		if( StringHelper.equals( verifyCode , code ) ){
			// 要保证 用户名 不为空 、密码不能为空 、两次输入的密码必须一致
			if( StringHelper.notEmpty( username ) 
					&& StringHelper.notEmpty( password ) 
					&& StringHelper.equals( password , confirm) ) {
				// 可以保存了 
				String SQL = "INSERT INTO t_user ( username , password ) VALUES ( ? , ? ) " ;
				int n = JdbcHelper.insert( SQL , false , username , password );
				if( n > 0 ) { // 如果 insert 返回 大于 0 的数字 ， 则表示 插入成功
					// 保存成功以后，应该去一个新的页面 ( 比如去 登录页面 )
					response.sendRedirect( request.getContextPath() + "/login.html" );
				} else {
					// 回到注册页面去
					session.setAttribute( "registFail" , "注册失败，可能是用户名被占用了" );
					response.sendRedirect( request.getContextPath() + "/regist.html" );
				}
			} else {
				// 回到注册页面去
				session.setAttribute( "registFail" , "用户名或密码为空，或者密码不一致" );
				response.sendRedirect( request.getContextPath() + "/regist.html" );
			}
		} else {
			// 如果验证码不一致，设置提示信息后回到注册页面去
			session.setAttribute( "registFail" , "验证码输入错误，请重新输入" );
			response.sendRedirect( request.getContextPath() + "/regist.html" );
		}
		
	}
       
}
