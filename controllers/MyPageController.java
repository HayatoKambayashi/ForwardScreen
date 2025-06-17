package jp.co.akkodis.syumix;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import jp.co.akkodis.syumix.dao.PostDao;
import jp.co.akkodis.syumix.dto.PostDto;

/**
 * Servlet implementation class MyPageController
 */
@WebServlet("/mypage")
public class MyPageController extends HttpServlet {
	
	//投稿内容を取得
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException {
    	PostDao postDao = new PostDao();
    	ArrayList userPosts = postDao.selectByUser(request.getSession().getAttribute("userId"));
    	
    	//JSPに渡す
		request.setAttribute("userPosts", userPosts);
		request.getRequestDispatcher("/mypage.jsp").forward(request, response);

		
	}
    
    //投稿を削除
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException {
		String action = request.getParameter("action");
		boolean flug = true;
		
		if ("削除".equals(action)) { //削除ボタンを押した場合
			int postID = Integer.parseInt(request.getParameter("postId"));
	        PostDao postDao = new PostDao();
	        postDao.delete(postID);
	        request.setAttribute("deleteflug", flug);
	        
	        request.getRequestDispatcher("/mypage.jsp").forward(request, response);
	        
	    } else if ("メインページへ".equals(action)) { // メインページへボタンを押した場合
	        request.getRequestDispatcher("/main.jsp").forward(request, response);
	    }
	        
	        
			
		}
				
	}


