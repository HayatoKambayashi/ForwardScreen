package jp.co.akkodis.syumix;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet implementation class ManagerController
 */
@WebServlet("/managerpage")
public class ManagerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//データベースに接続するための情報
		String url = "jdbc:mysql://localhost:3306/syumixDB"; //データベース名(未定）
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String user = "****"; //データベースのユーザ名
		String password = "*****";//データベースのパスワード
		
		//ユーザデータを格納するList
		List<UserDto> userList = new ArrayList<UserDto>();	
		
		   //データベースに接続
	       	try (Connection con = DriverManager.getConnection(url, user, password);
	                Statement st = con.createStatement();){

	            // SQLを実行して、テーブルからユーザIDとユーザーネームを取得する（未定）
	            ResultSet rs = st.executeQuery("SELECT userid, username FROM usertable");

	            // 取得した件数分繰り返す
	            while(rs.next()) {
	            	UserDto dto = new UserDto();
	            	dto.setUserId(rs.getInt("userid"));
                    dto.setUserName(rs.getString("username"));
                    userList.add(dto);
	            }
			
	        } catch(SQLException e) {
	            e.printStackTrace();
	        }
	
		 // ユーザ情報をセットする
	    request.setAttribute("userList", userList);
	    request.getRequestDispatcher("/manager.jsp").forward(request, response); // JSPへ遷移

		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String userId = request.getParameter("userId");

	    // userId を使ってパスワード発行処理を行う
	   
	    //DAOのメソッドで「仮パスフラグ」を立てる(DAOを使う？)
	    
	    // 処理後に画面遷移やメッセージ表示など
	    request.setAttribute("message", "ユーザー " + userId + " のパスワードを発行しました。");
	}


}
