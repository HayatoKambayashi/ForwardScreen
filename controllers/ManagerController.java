package jp.co.akkodis.syumix;

import jakarta.servlet.ServletException;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
		

		// セッションチェック
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
		response.sendRedirect("login.jsp"); // ログインページへリダイレクト
		return;
		}

		
		
		try(UserDao userDao = new UserDao()) {
			// ユーザ一覧を取得
			ArrayList<UserDto> userList = null;
			userList = userDao.findAll();
			
			 // ユーザ情報をセットする
		    request.setAttribute("userList", userList);
		    request.getRequestDispatcher("/manager.jsp").forward(request, response); // JSPへ遷移
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    

		// セッションチェック
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
		response.sendRedirect("login.jsp"); // ログインページへリダイレクト
		return;
		}

		
		String userId = request.getParameter("userId");
	    System.out.println(userId);
	    

	    // userId を使ってパスワード発行処理を行う
		try(UserDao userDao = new UserDao()) {
			userDao.setKariPassword(userId);
			
			   // 処理後に画面遷移やメッセージ表示など
		    request.setAttribute("message", "ユーザー " + userId + " のパスワードを発行しました。");
		    doGet(request,response);
		    request.getRequestDispatcher("/manager.jsp").forward(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}