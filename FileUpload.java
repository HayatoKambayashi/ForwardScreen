package jp.co.akkodis.training.multipart;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/upload_file")
@MultipartConfig
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("message", "画像アップロードテスト");
		RequestDispatcher rd = request.getRequestDispatcher("fileUpload.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//
		Part part = req.getPart("imgFile");
		
		String filename = part.getSubmittedFileName(); // ファイル名を取得するメソッド
		System.out.println(filename);
		
		String path = getServletContext().getRealPath("/upload"); /* サーバー上
											で動作しているPathは、workspaceと別にある */
		System.out.println(path);
		
		// 書き込み
		part.write(path + File.separator + filename);
		
		// アップロードしたファイルを転送先で表示する設定
		req.setAttribute("message", "画像アップ完了。ご確認ください。");
		req.setAttribute("img", filename);
		
		RequestDispatcher rd = req.getRequestDispatcher("fileUpload.jsp");
		rd.forward(req, resp);
		
	}

}
