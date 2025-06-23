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
	private String inputCheckPass = null; // passchangeページでの新パスワード確認用String
	private String userName = null; // passchangeページで表示するユーザー名

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//新パス発行の真偽

		//リクエストスコープのパラメータを取得する。
		inputUser = request.getParameter("userId");
		inputPass = request.getParameter("password");
		inputCheckPass = request.getParameter("checkPassword");
		
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
				
				// 新パスワードが入力されていない、または空白のみ入力されている時
				if (inputPass==null || inputPass.isEmpty()) {
					request.setAttribute("message", "新しいパスワードを入力してください。");
					request.setAttribute("userId", inputUser); // 入力値を保持
					request.setAttribute("userName", userName);
					request.getRequestDispatcher("/passChange.jsp").forward(request, response);
					return;
				// 新パスワードが入力されているが、新パスワード確認と値が一致しない時
				} else if(!(inputPass.equals(inputCheckPass))) {
					request.setAttribute("message", "入力されたパスワードが一致しません。");
					request.setAttribute("userId", inputUser); // 入力値を保持
					request.setAttribute("userName", userName);
					request.getRequestDispatcher("/passChange.jsp").forward(request, response);
					return;
				}
				// 新パスワードが入力されており、新パスワード確認と値が一致した時
				// パスワードの長さチェック
				if (inputPass.length() > 20) {
				request.setAttribute("message", "パスワードは20文字以内で入力してください。");
				request.setAttribute("userId", inputUser); // 入力値を保持
				request.setAttribute("userName", userName);
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
			if (user.getUserId() == UserDto.MANAGER_ID && user.getPass().equals(inputPass)) { 
				if (user.getPassFlag()) { // 仮パス設定フラグがtrueの場合
					request.setAttribute("username", inputUser);
					HttpSession session = request.getSession();
					session.setAttribute("manager", inputUser);
					request.getRequestDispatcher("/passChange.jsp").forward(request, response);
					return;
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("manager", inputUser);
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
					request.setAttribute("userId", inputUser);  // 追加事項B
					userName = user.getUserName();
					request.setAttribute("userName", userName);
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
		} catch (Exception e) {
			e.printStackTrace();
			//!!!![データベーステーブルから取得したデータがnullもしくは不正な値の場合]
			request.setAttribute("infoMessage", "ユーザテーブル、またはユーザDTOに問題があります。");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			//System.out.println("debug: userDaoのコンストラクタがうまくいきませんでした");
			return;
		}

	}
	
	
}
