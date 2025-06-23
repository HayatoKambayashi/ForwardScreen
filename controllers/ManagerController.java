package jp.co.akkodis.syumix;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.akkodis.syumix.dao.UserDao;
import jp.co.akkodis.syumix.dto.UserDto;
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
		if (session == null || session.getAttribute("manager") == null) {
			if (session.getAttribute("loginUser") == null) {
				// ログインユーザ情報がない場合。
				response.sendRedirect("login.jsp");
				return;
			} else { // ある場合
				response.sendRedirect("maincontroller");
				return;
			}
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
	    String userId = request.getParameter("userId");
	    String btn = request.getParameter("btn");
	    System.out.println(userId);
	    

	    // userId を使ってパスワード発行処理を行う
		try(UserDao userDao = new UserDao()) {
			
			if (btn.equals("発行")) {
				userDao.setKariPassword(userId);
			   // 処理後に画面遷移やメッセージ表示など
				request.setAttribute("message", "ユーザー " + userId + " のパスワードを発行しました。");
			} else if (btn.equals("退職")) {
				userDao.updateRetired(userId);
				request.setAttribute("message", "ユーザー " + userId + " の退職処理が完了しました。");
			}
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
