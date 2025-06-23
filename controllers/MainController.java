package jp.co.akkodis.syumix;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.akkodis.syumix.dao.PostDao;
import jp.co.akkodis.syumix.dao.UserDao;
import jp.co.akkodis.syumix.dto.GenreDto;
import jp.co.akkodis.syumix.dto.PostDto; 
/**
 * Servlet implementation class mainController
 */
@WebServlet("/maincontroller")
public class MainController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// セッションチェック
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect("login.jsp"); // ログインページへリダイレクト
			return;
		}
		String link = "main.jsp";
		
		try (PostDao postDao = new PostDao()){
			// pickメソッドを呼び出してランダムに1件のデータをDTOに格納
			PostDto data  = postDao.pick();
			// データをリクエストに送る
			request.setAttribute("data", data);
			
			// 氏名情報をリクエストに送る
			UserDao userDao = new UserDao();
			request.setAttribute("name", userDao.selectById(Integer.toString(data.getUserId())).getUserName());
			userDao.close();
			
			// 投稿のジャンル情報を送る
			ArrayList<GenreDto> allGenre = postDao.getAllGenre();
			String genreName = null;
			for (GenreDto eachGenre : allGenre) {
				if (eachGenre.getGenreCd().equals(data.getGenreCd())) {
					genreName = eachGenre.getGenreName();
				}
			}
			request.setAttribute("genreName", genreName);
			
			// データをmain.jspに送る
			RequestDispatcher dispatcher = request.getRequestDispatcher(link);
			dispatcher.forward(request, response);
		}catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doGetの呼び出し
		doGet(request, response);
	}
}
