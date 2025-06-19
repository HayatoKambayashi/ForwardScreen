package jp.co.akkodis.syumix;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import jp.co.akkodis.syumix.dao.PostDao; 
import jp.co.akkodis.syumix.dto.PostDto; 
/**
 * Servlet implementation class mainController
 */
@WebServlet("/maincontroller")
public class MainController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String link = "main.jsp";
		
		try (PostDao postDao = new PostDao()){
			// pickメソッドを呼び出してランダムに1件のデータをDTOに格納
			PostDto data  = postDao.pick();
			// データをリクエストに送る
			request.setAttribute("data", data);
			
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