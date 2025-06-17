package jp.co.akkodis.syumix;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

import jp.co.akkodis.syumix.dto.PostDao;
import jp.co.akkodis.syumix.dto.GenreDao;
import jp.co.akkodis.syumix.dto.PostDto;
import jp.co.akkodis.syumix.dto.GenreDto;

/**
 * Servlet implementation class postController
 */
@WebServlet("/post")
public class PostController extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GenreDao genreDao = new GenreDao();
		ArrayList<GenreDto> allGenreList = genreDao.getAllGenre(); // ジャンル一覧を取得
		request.setAttribute("allGenreList", allGenreList);
		RequestDispatcher rd = request.getRequestDispatcher("/post.jsp"); // 投稿フォーム入力画面で使うのでpost.jspに送る
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String btn = request.getParameter("btn");   // どのボタンを押したかの情報
		
		int userId = (int) request.getSession().getAttribute("userId");    //入力情報たちの取得
		String genreCd = request.getParameter("genreCd");
		String source = request.getParameter("source");
		String image = request.getParameter("image");
		String url = request.getParameter("url");
		
		// 今日の日付を取得
		LocalDate today = LocalDate.now();
		
		// SQLのDate型に変換
		Date sqlDate = Date.valueOf(today);


		String jsp = "/mypage";	// sprint1では投稿フォーム確認画面にいかずマイページ画面に遷移させる。
		
		try{
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
					
					PostDao post = new PostDao();
						
					int insertCount = post.create(postDto);
					
					if(insertCount >0 ) {
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
			jsp = "/insertError.jsp";	
		}catch (SQLException e) {
			request.setAttribute("errorMessage", "JDBC のエラーです");
			e.printStackTrace();
			jsp = "/postError.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "エラーが発生しました");
			jsp = "/postError.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(jsp);
		rd.forward(request, response);
	}
}
