package jp.co.akkodis.syumix;

import jakarta.servlet.http.HttpServlet;

//!!!!add import
import java.io.PrintWriter;
import jp.co.akkodis.syumix.Dao.UserDao;
import jp.co.akkodis.syumix.Dto.UserDto;
//!!!!

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class loginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private String inputUser = null;
	private String inputPass = null;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//新パス発行の真偽
		
		//リクエストスコープのパラメータを取得する。
		inputUser = request.getParameter("userid");
		inputPass = request.getParameter("password");
		
		//[IDまたはパスワードが未入力の場合]
		if (inputUser == null || inputUser.isEmpty() ||
			inputPass == null || inputPass.isEmpty() ) {
			// リクエストにエラーメッセージを設定
			request.setAttribute("infoMessage", "入力に誤りがあります。");
			// login.jsp へフォワード
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
		}
		
		UserDto user = userDao.selectById(inputUser);
		response.setContentType("text/html; charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			if ((user != null)) {
				//[認証エラーがある場合]
				if ((user.getUserId() != inputUser) || (user.getPass() != inputPass)) {
					request.setAttribute("infoMessage", "従業員番号またはパスワードに誤りがあります。");
			        request.getRequestDispatcher("/login.jsp").forward(request, response);
			        return;
				}
				//[認証エラーがない場合]
				if ((user.getUserId() == inputUser) && (user.getPass() == inputPass)
					&& (user.isRetiredFlag() == false) && (user.isPassFlag() == false)) {
					// 取得したユーザ情報をセッションスコープに登録する。
					HttpSession session = request.getSession();
					session.setAttribute("loginUser", user);
			        request.getRequestDispatcher("/main.jsp").forward(request, response);
			        return;
				}
				//[認証エラーがなく、UserDto内のisRetiredFlagがtrueの場合]
				if ((user.getUserId() == inputUser) && (user.getPass() == inputPass)
					&& (user.isRetiredFlag() == true)) {
					request.setAttribute("infoMessage", "既に退職済みです。");
		            request.getRequestDispatcher("/login.jsp").forward(request, response);
			        return;
				}
				//[認証エラーがなく、UserDto内のisPassFlagがtrueの場合]
				if ((user.getUserId() == inputUser) && (user.getPass() == inputPass)
					&& (user.isPassFlag() == true)) {
					HttpSession session = request.getSession();
					session.setAttribute("loginUser", user);
		            request.getRequestDispatcher("/passChange.jsp").forward(request, response);
			        return;
				}
			} else {
				//!!!![データベーステーブルから取得したデータがnullもしくは不正な値の場合]
				request.setAttribute("infoMessage", "ユーザテーブル、またはユーザDTOに問題があります。");
		        request.getRequestDispatcher("/login.jsp").forward(request, response);
		        return;
			}
		}
	}
}