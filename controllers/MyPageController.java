package jp.co.akkodis.syumix;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.akkodis.syumix.dao.PostDao;
import jp.co.akkodis.syumix.dto.GenreDto;
import jp.co.akkodis.syumix.dto.PostDto;
import jp.co.akkodis.syumix.dto.UserDto;

/**
 * Servlet implementation class MyPageController
 */
@WebServlet("/mypage")
public class MyPageController extends HttpServlet {
	
//投稿内容を取得
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException {

		// セッションチェック
	    	HttpSession session = request.getSession(false);
    		if (session == null || session.getAttribute("loginUser") == null) {
    			response.sendRedirect("login.jsp"); // ログインページへリダイレクト
    			return;
    		}
		
		try (PostDao postDao = new PostDao()) {
//			String userId = (String) request.getSession().getAttribute("userId");
			UserDto userDto = (UserDto) request.getSession().getAttribute("loginUser");
			int userid = userDto.getUserId(); 
			String userId = Integer.toString(userid);
//			System.out.println("debug: " + userId);//debug
			
	    	ArrayList<PostDto> userPosts = postDao.selectByUser(userId);
//			ArrayList<PostDto> userPosts = postDao.selectByUser("100001");
			
			
//	    	System.out.println("DAOおわった！");//debug
	    	//ArrayList userPosts = postDao.selectByUser(request.getSession().getAttribute("userId"));
	    	
	    	//JSPに渡す
			request.setAttribute("userPosts", userPosts);
			
			// 投稿のジャンル名取得
			ArrayList<GenreDto> allGenre = postDao.getAllGenre();
			request.setAttribute("allGenre", allGenre);
			
//			System.out.println(userPosts);//debug
			request.getRequestDispatcher("/mypage.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
    
    //投稿を削除
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException {
		String action = request.getParameter("action");
		boolean flug = true;
		
		if ("削除".equals(action)) { //削除ボタンを押した場合
			int postID = Integer.parseInt(request.getParameter("postId"));
			
			try (PostDao postDao = new PostDao()) {
				postDao.delete(postID);
		        request.setAttribute("deleteflug", flug);

		        UserDto userDto = (UserDto) request.getSession().getAttribute("loginUser");
				int userid = userDto.getUserId(); 
				String userId = Integer.toString(userid);
//				System.out.println("debug: " + userId);//debug
				
		    	ArrayList<PostDto> userPosts = postDao.selectByUser(userId);
//				ArrayList<PostDto> userPosts = postDao.selectByUser("100001");
				
				
//		    	System.out.println("DAOおわった！");//debug
		    	//ArrayList userPosts = postDao.selectByUser(request.getSession().getAttribute("userId"));
		    	
		    	//JSPに渡す
				request.setAttribute("userPosts", userPosts);
				
				// 投稿のジャンル名取得
				ArrayList<GenreDto> allGenre = postDao.getAllGenre();
				request.setAttribute("allGenre", allGenre);

		        request.getRequestDispatcher("/mypage.jsp").forward(request, response);
		        
			} catch (ClassNotFoundException | SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
	        
	    } else if ("メインページへ".equals(action)) { // メインページへボタンを押した場合
	        request.getRequestDispatcher("/main.jsp").forward(request, response);
	    }
	}
}
