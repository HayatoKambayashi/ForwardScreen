package jp.co.akkodis.syumix;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import jp.co.akkodis.syumix.dao.PostDao;
import jp.co.akkodis.syumix.dto.GenreDto;
import jp.co.akkodis.syumix.dto.PostDto;
import jp.co.akkodis.syumix.dto.UserDto;

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
		UserDto userDto = (UserDto) request.getSession().getAttribute("loginUser");
		String btn = request.getParameter("btn");   // どのボタンを押したかの情報
		 
		//◆以下、入力情報の取得
		int userId = userDto.getUserId();
		
		//imageの定義方法を変更。jspからファイル名を獲得する。データベースにはファイル名を保存する
		Part part = request.getPart("image");
		String image = part.getSubmittedFileName();
		//きちんと回収できているか確認
		System.out.println(image);
		//サーバー上(コード実行してるPC上)に作成した
		//C:\pleiades\2024-12\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps
		//\Syumix\ uploadフォルダに保存する
		String path = getServletContext().getRealPath("/upload");

		// フォルダが存在しない場合は作成
		File uploadDir = new File(path);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs(); 
		}

		part.write(path + File.separator + image);
		
		String genreCd = request.getParameter("genreCd");
		String source = request.getParameter("source");
		String url = request.getParameter("url");  //◆入力情報の取得ここまで
		
		// 今日の日付を取得
		LocalDate today = LocalDate.now();
		// SQLのDate型に変換
		Date sqlDate = Date.valueOf(today);

		String jsp = "/mypage";	 // sprint1では投稿フォーム確認画面にいかずマイページ画面に遷移させる。
		
		try (PostDao post = new PostDao()) {
			ArrayList<GenreDto> allGenreList = post.getAllGenre();
		    request.setAttribute("allGenreList", allGenreList); // ← JSTLに必要！

		     // TODO: 【手塚さん引継ぎ用】
		    if (btn != null && btn.equals("change-post")) {
		    	// 投稿内容最終確認で、投稿を修正するボタンが押された場合  のIFブロック
		    	
		    	 request.setAttribute("genreCd", genreCd);
		    	 request.setAttribute("source", source); // 入力内容の保持（Post.jsp遷移後も）
		    	 request.setAttribute("url", url);
		    	 //TODO: このへん(ifブロックラスト)でpost.jspにリダイレクトする。
		    }

			if(btn != null && btn.equals("post")) { // 投稿ボタンがクリックされた場合
				// 投稿をDBに保存する準備を行う。
				
				PostDto postDto = new PostDto();

				if (genreCd != null && !genreCd.isEmpty()
					&& ((source != null && !source.isEmpty())|| 
						(image != null && !image.isEmpty()) ||
						(url != null && !url.isEmpty()))){ // 入力必須項目 ３つのうち１つが入力済み、かつ投稿タグ指定済み
					
					postDto.setUserId(userId);
					postDto.setGenreCd(genreCd);
					postDto.setSource(source);
					postDto.setImage(image);
					postDto.setUrl(url);
					postDto.setDate(sqlDate);
					if (Boolean.parseBoolean(request.getParameter("anonyFlag")) == true) {
						postDto.setAnonyFlag(true);
					} else {
						postDto.setAnonyFlag(false);
					}
						
					int insertCount = post.insert(postDto);
					
					if(insertCount > 0) {
						request.setAttribute("message", "投稿が完了しました");
					}else {
						request.setAttribute("message", "投稿に失敗しました。");
					} // 
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
		doGet(request, response);
		RequestDispatcher rd = request.getRequestDispatcher(jsp);
		rd.forward(request, response);
	}

}
