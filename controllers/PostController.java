package jp.co.akkodis.syumix;

import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import jp.co.akkodis.syumix.dao.PostDao;
import jp.co.akkodis.syumix.dto.PostDto;
import jp.co.akkodis.syumix.dto.UserDto;
import jp.co.akkodis.syumix.dto.GenreDto;

/**
 * Servlet implementation class postController
 */
@WebServlet("/post")
public class PostController extends HttpServlet{
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
		try (PostDao postDao = new PostDao()) {
			ArrayList<GenreDto> allGenreList = postDao.getAllGenre(); // ジャンル一覧を取得
			request.setAttribute("allGenreList", allGenreList);
			RequestDispatcher rd = request.getRequestDispatcher("/post.jsp"); // 投稿フォーム入力画面で使うのでpost.jspに送る
			rd.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// セッションチェック
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("loginUser") == null) {
		response.sendRedirect("login.jsp"); // ログインページへリダイレクト
		return;
		}
		request.setCharacterEncoding("UTF-8");
		String btn = request.getParameter("btn");   // どのボタンを押したかの情報
		UserDto userDto = (UserDto) request.getSession().getAttribute("loginUser");
		int userId = userDto.getUserId();         //入力情報たちの取得
		String genreCd = request.getParameter("genreCd");
		String source = request.getParameter("source");
		String image = request.getParameter("image");
		String url = request.getParameter("url");
		
		// 今日の日付を取得
		LocalDate today = LocalDate.now();
		// SQLのDate型に変換
		Date sqlDate = Date.valueOf(today);

		// sprint1では投稿フォーム確認画面にいかずマイページ画面に遷移させる。
		String jsp = "/mypage";	
		
		try (PostDao post = new PostDao()) {
			ArrayList<GenreDto> allGenreList = post.getAllGenre();
		    request.setAttribute("allGenreList", allGenreList); // ← JSTLに必要！


			if(btn != null && btn.equals("post")) {
				PostDto postDto = new PostDto();

				if (genreCd != null && !genreCd.isEmpty()
					&& ((source != null && !source.isEmpty())|| 
						(image != null && !image.isEmpty()) ||
						(url != null && !url.isEmpty()))){
					
					postDto.setUserId(userId);
					postDto.setGenreCd(genreCd);
					postDto.setSource(source);
					postDto.setImage(image);
					postDto.setUrl(url);
					postDto.setDate(sqlDate);
						
					int insertCount = post.insert(postDto);
					
					if(insertCount > 0) {
						request.setAttribute("message", "投稿が完了しました");
					}else {
						request.setAttribute("message", "投稿に失敗しました。");
					}
				}else {
					request.setAttribute("message", "いずれかの項目を入力してください");
				}
			}else {
				request.setAttribute("errorMessage", " 不正アクセスです");
				jsp = "/postError.jsp";
			}
			
		}catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "入力値が不正です");
			jsp = "/postError.jsp";	
		}catch (SQLException e) {
			request.setAttribute("errorMessage", "JDBC のエラーです");
			e.printStackTrace();
			jsp = "/postError.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "エラーが発生しました");
			jsp = "/postError.jsp";
		}
		System.out.println("OK");
		doGet(request, response);
		RequestDispatcher rd = request.getRequestDispatcher(jsp);
		rd.forward(request, response);
		System.out.println("OK");
	}

}
