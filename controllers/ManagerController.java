package jp.co.akkodis.syumix;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.akkodis.syumix.dao.UserDao;
import jp.co.akkodis.syumix.dto.UserDto;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Servlet implementation class ManagerController
 */
@WebServlet("/managerpage")
public class ManagerController extends HttpServlet {
	//private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		UserDao userDao = null;
		try {
			userDao = new UserDao();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<UserDto> userList = null;
		try {
			userList = userDao.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ユーザ一覧を取得
		 // ユーザ情報をセットする
	    request.setAttribute("userList", userList);
	    request.getRequestDispatcher("/manager.jsp").forward(request, response); // JSPへ遷移

		
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String userId = request.getParameter("userId");
	    
	    System.out.println(userId);
	    
	    System.out.println("1");
	    int test;

	    // userId を使ってパスワード発行処理を行う
	    UserDao userDao = null;
		try {
			userDao = new UserDao();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			test = userDao.setKariPassword(userId);
			 System.out.println(test);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    // 処理後に画面遷移やメッセージ表示など
	    request.setAttribute("message", "ユーザー " + userId + " のパスワードを発行しました。");
	    doGet(request,response);
	    request.getRequestDispatcher("/manager.jsp").forward(request, response);
	}


}
