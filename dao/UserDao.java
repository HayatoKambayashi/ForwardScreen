package jp.co.akkodis.syumix.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.co.akkodis.syumix.dto.UserDto;

public class UserDao extends Dao{

	public UserDao() throws ClassNotFoundException, SQLException {
		super();
	}
	
	/**
	 * 管理者画面での利用を想定。
	 * 現在ログイン可能なユーザ一覧を取得するメソッド
	 * 
	 * @return ArrayList<UserDto> 退職フラグがoffな従業員一覧、ただし例外発生によって正常に返却しないリスクあり
	 * @throws SQLException
	 */
	public ArrayList<UserDto> findAll() throws SQLException{
		String sql = "SELECT * FROM user WHERE retiredFlag = FALSE;";
		
		ArrayList<UserDto> list = new ArrayList<UserDto>(); // resultで返却されるべきリスト
		
		try {
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				// TODO: 再利用可能ポイント： user情報の格納。このコメント行はコピペ後消す。
				UserDto user = new UserDto();
				user.setUserId(rs.getInt(1)); // tips: ResultSet型のカラムインデックスは1から始まり
				user.setUserName(rs.getString(2));
				user.setPass(rs.getString(3)); // TODO?
				user.setRetiredFlag(rs.getBoolean(4));
				user.setPassFlag(rs.getBoolean(5));
				
				list.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	/**
	 * 
	 * @param userId(文字列型)
	 * @return
	 */
	public UserDto selectById (String userId) {
		String sql = "SELECT * FROM user WHERE userId = ?;" ; // 準備中のSQL
		
		UserDto arg = new UserDto(); // 返却されるべきUserDtoインスタンス
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, userId); // 引数を現在準備中のSQL文に入れ込んだ。
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				arg.setUserId(rs.getInt(1)); // tips: ResultSet型のカラムインデックスは1から始まり
				arg.setUserName(rs.getString(2));
				arg.setPass(rs.getString(3));
				arg.setRetiredFlag(rs.getBoolean(4));
				arg.setPassFlag(rs.getBoolean(5));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arg;
	}
	
	

}
