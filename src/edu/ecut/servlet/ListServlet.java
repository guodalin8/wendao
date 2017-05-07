package edu.ecut.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.malajava.util.JdbcHelper;

import edu.ecut.entity.Topic;
import edu.ecut.entity.User;

@WebServlet( "/list.do" )
public class ListServlet  extends HttpServlet {

	private static final long serialVersionUID = 810339694607399128L;

	@Override
	protected void service( HttpServletRequest request , HttpServletResponse response ) 
			throws ServletException, IOException {
		
		/**** 查询数据库中的所有问题  ***********************************/
		final String SQL = "SELECT id , title , publish_time , publish_ip , user_id FROM t_topic ORDER BY publish_time DESC" ;
		ResultSet rs = JdbcHelper.query( SQL );
		
		// 创建一个 List 对象，用来保存一批 Topic 对象
		final List<Topic> topics = new ArrayList<>();
		
		try {
			// 每循环一次，光标下移一行，如果该行有数据返回 true
			while( rs.next() ){
				
				Topic t = new Topic(); // 创建对象
				
				t.setId( rs.getInt( 1 ) ); // 将 结果集 中的 该行数据 封装到 t 对象的 id 属性中
				t.setTitle( rs.getString( 2 ) );
				t.setPublishTime( rs.getTimestamp( 3 ));
				t.setPublishIp( rs.getString( 4 ) );
				
				User u = new User(); // 创建 一个 User 对象
				u.setId( rs.getInt( 5 ) ); // 将 t_topic 表中的 user_id 放入到 User 对象的 id 属性中
				t.setUser( u );  // 将 User 对象 设置到 Topic 对象上
				
				/** 将 本次循环 创建的对象(已经封装数据) 添加到 List 集合中 */
				topics.add( t );
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JdbcHelper.release( rs ); // 关闭 结果集，释放相关的资源
		
		/**** 为每个问题寻找提问者  ***********************************/
		//for( int i = 0 ; i < topics.size() ; i++ ){
		for( int i = 0 , len = topics.size() ; i < len ; i++ ){
			Topic t = topics.get( i ) ; // 获得 题目
			User u = t.getUser(); // 获得当前题目的User对象 ( 该对象中只有 id 没有其它数据 )
			
			// 根据 用户对象的 id 来查询 用户的信息
			String querySQL = "SELECT id , username , password FROM t_user WHERE id = ? " ;
			ResultSet userRs = JdbcHelper.query( querySQL , u.getId() );
			try {
				if( userRs.next() ) { // 如果查询到用户信息
					// 注意，这里应该使用 userRs
					u.setUsername( userRs.getString( 2 ) ); // 将 username 列的值设置到 用户对象的 username 属性中
					u.setPassword( userRs.getString( 3 )); // 将 password 列的值设置到 用户对象的 password 属性中
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			JdbcHelper.release( userRs ); // 关闭 结果集，释放相关的资源
			
		}
		
		
		ServletContext application = request.getServletContext();
		/** 将这些数据保存到 application **/
		application.setAttribute( "topics" , topics );
		
		System.out.println( "问题列表: " + topics );
		
		// 去 list.html 页面
		response.sendRedirect( request.getContextPath() + "/list.html");
		
	}

}
