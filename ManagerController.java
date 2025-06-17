package jp.co.akkodis.syumix;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class ManagerController
 */
@WebServlet("/managerpage")
public class ManagerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		UserDao userDao = new UserDao();
		ArrayList<UserDto> userList = userDao.getAllGenre(); // ユーザ一覧を取得
		request.setAttribute("allGenreList", userList);
		 // ユーザ情報をセットする
	    request.setAttribute("userList", userList);
	    request.getRequestDispatcher("/manager.jsp").forward(request, response); // JSPへ遷移

		
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String userId = request.getParameter("userId");

	    // userId を使ってパスワード発行処理を行う
	   
	    //DAOのメソッドで「仮パスフラグ」を立てる(DAOを使う？)
	    
	    // 処理後に画面遷移やメッセージ表示など
	    request.setAttribute("message", "ユーザー " + userId + " のパスワードを発行しました。");
	}


}
