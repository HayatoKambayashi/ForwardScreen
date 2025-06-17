package jp.co.akkodis.syumix.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {
	protected Connection connection;
	
	/**
	 * 共通するコンストラクタ
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @param 引数は不要です。
	 */
	public Dao()  throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost/db";
		String user = "root";
		String password = "VSOLuser123456";
		
		try {
			connection = DriverManager.getConnection(url, user, password);
			// TODO: Drivemanager
		} catch (SQLException e) {
			e.printStackTrace(); // 詳細設計書 記述外
		}
	}
	
	/**
	 * 全DAOで共通するclose処理です。
	 * @throws SQLException
	 */
	public void close() throws SQLException{ 
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
}
