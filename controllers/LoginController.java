package jp.co.akkodis.syumix;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.akkodis.syumix.dao.UserDao; //
import jp.co.akkodis.syumix.dto.UserDto;
//!!!!

/**
 * Servlet implementation class loginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private String inputUser = null;
	private String inputPass = null;
	private String passChangeFlag = null; // passchangeページから呼び出されたか確認する変数

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//新パス発行の真偽

		//リクエストスコープのパラメータを取得する。
		inputUser = request.getParameter("userId");
		inputPass = request.getParameter("password");
		
		passChangeFlag = request.getParameter("id");
		if (passChangeFlag == null) { // [42行目付近if文と関連]
			passChangeFlag = "0";
		}
		
		try (UserDao userDao = new UserDao()){
			if ( !(passChangeFlag.equals("0")) ) { // [3７行目付近if文と関連]

				// [不要かも]セッションチェック：ログイン状況が取得できない場合、login.jspに飛ばす
				HttpSession session = request.getSession(false);
				if (session == null || session.getAttribute("loginUser") == null) {
					response.sendRedirect("login.jsp");
					return;
				}
				
				// TODO : パスワード欄二つの入力内容に同一のPWが入っていないとき
				// ↓の箇所にifブロックを設置し、
//				if (false) {
//					request.setAttribute("username", inputUser); // 入力値を保持
//				}

				// パスワードの長さチェック
				if (inputPass.length() > 20) {
				request.setAttribute("message", "パスワードは20文字以内で入力してください。");
				request.setAttribute("username", inputUser); // 入力値を保持
				request.getRequestDispatcher("/passChange.jsp").forward(request, response);
				return;
				}

				userDao.updatePassword(inputPass, inputUser);
				request.getRequestDispatcher("/main.jsp").forward(request, response);
				// mainに遷移する。
				return;
			}

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
			
			// 管理者がログインした場合
				if (user.getUserId() == 100000 && user.getPass().equals(inputPass)) { 
					if (user.getPassFlag()) { // 仮パス設定フラグがtrueの場合
						HttpSession session = request.getSession();
						request.setAttribute("username", inputUser);
						request.getRequestDispatcher("/passChange.jsp").forward(request, response);
						return;
					} else {
						HttpSession session = request.getSession();
						response.sendRedirect("managerpage");
						return;
					}
					
				}
				//[認証エラーがある場合]
				if (!((""+user.getUserId()).equals(inputUser)) || !(user.getPass().equals(inputPass))) {
					request.setAttribute("infoMessage", "従業員番号またはパスワードに誤りがあります。");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return;
				}else {
					//[認証エラーがない場合]
					//[認証エラーがなく、UserDto内の退職者フラグがtrueの場合]
					if(user.getRetiredFlag()) {
						request.setAttribute("infoMessage", "既に退職済みです。");
						request.getRequestDispatcher("/login.jsp").forward(request, response);
						return;
					}
					else if (user.getPassFlag()) {
						//[認証エラーがなく、仮パス設定フラグがtrueの場合]
						HttpSession session = request.getSession();
						session.setAttribute("loginUser", user);
						request.setAttribute("username", inputUser);  // 追加事項B
						request.getRequestDispatcher("/passChange.jsp").forward(request, response);
						return;
					}
					else {
						// 取得したユーザ情報をセッションスコープに登録する。
						HttpSession session = request.getSession();
						session.setAttribute("loginUser", user);
						request.getRequestDispatcher("/main.jsp").forward(request, response);
						return;
					}
				}
			} else {
				//!!!![データベーステーブルから取得したデータがnullもしくは不正な値の場合]
				request.setAttribute("infoMessage", "ユーザテーブル、またはユーザDTOに問題があります。");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("debug: userDaoのコンストラクタがうまくいきませんでした");
		}

	}
	
	
}
