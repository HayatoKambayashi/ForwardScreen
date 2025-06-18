package jp.co.akkodis.syumix.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import jp.co.akkodis.syumix.dto.GenreDto;
import jp.co.akkodis.syumix.dto.PostDto;

public class PostDao extends Dao{
	
	public PostDao() throws ClassNotFoundException, SQLException {
		super();
	}
	
	/**
	 * マイページ経由で投稿を削除するためのメソッドです。
	 * @param postId
	 * @return 削除された件数。エラーの場合は 0。
	 */
	public int delete(int postId) {
		
		String sql = "DELETE FROM post WHERE postId = ?" ; 
		int result =0;
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
		    ps.setInt(1, postId);
		    result = ps.executeUpdate();
		    
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	/**
	 * 投稿をデータベースに挿入するメソッドです。
	 * @param postDto（投稿内容が既に入れ込まれたJavaBean）
	 * @return DBに反映された件数。エラーの場合は 0。
	 */
	public int insert (PostDto postDto) {
		String sql = "INSERT INTO post (userId, source, url, gendercd,"
				+ "image, anonyFlag, date) VALUES"
				+ "(?, ?, ?, ?," // prepare ? 1～4個目 userId, 投稿本文, URL, genderCd
				+ " ?, ?, ?)"; // 5～7個目 画像ソース(image), 匿名フラグ, 投稿時刻
		
		int result = 0;
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, ""+postDto.getUserId());
			
			if (postDto.getSource() != null) {
				ps.setString(2, postDto.getSource());
			} else {
				ps.setNull(2, java.sql.Types.VARCHAR);
			}
			
			if (postDto.getUrl() != null) {
				ps.setString(3, postDto.getUrl());
			} else {
				ps.setNull(3, java.sql.Types.VARCHAR);
			}
			
			ps.setString(4, postDto.getGenreCd());
			
			if (postDto.getImage() != null) {
				ps.setString(5, postDto.getImage());
			} else {
				ps.setNull(5, java.sql.Types.VARCHAR);
			}
			
			ps.setBoolean(6, postDto.getAnonyFlag());
			
			ps.setDate(7, postDto.getDate());
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param genreCd
	 * @return List<PostDto>
	 */
	public ArrayList<PostDto> findByGenre (String genreCd) {
		String sql = "SELECT * FROM post WHERE genreCd = ?;" ; // 準備中のSQL
		
		ArrayList<PostDto> list = new ArrayList<PostDto>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, genreCd);
			// todo
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				PostDto post = new PostDto();
				post.setPostId(rs.getInt(1));
				post.setUserId(rs.getInt(2));
				post.setSource(rs.getString(3));
				post.setUrl(rs.getString(4));
				post.setGenreCd(rs.getString(5));
				post.setImage(rs.getString(6));
				post.setAnonyFlag(rs.getBoolean(7));
				post.setDate(rs.getDate(8));
				
				list.add(post);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * マイページに移動する際に、現在ログインされているユーザのIDと一致する
	 * 投稿一覧を新しい順に取得します。
	 * @param userId
	 * @return ArrayList<PostDto> postlist
	 */
	public ArrayList<PostDto> selectByUser(String userId) {
		String sql = "SELECT * FROM post WHERE userId = ? order by PostId DESC ;";
		
		ArrayList<PostDto> postlist = new ArrayList<PostDto>(); // return
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, userId);
			// todo
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				PostDto post = new PostDto();
				post.setPostId(rs.getInt(1));
				post.setUserId(rs.getInt(2));
				post.setSource(rs.getString(3));
				post.setUrl(rs.getString(4));
				post.setGenreCd(rs.getString(5));
				post.setImage(rs.getString(6));
				post.setAnonyFlag(rs.getBoolean(7));
				post.setDate(rs.getDate(8));
				
				postlist.add(post); // TODO: 実装途中です。妥当性検証中
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return postlist;
	}
	
	
public PostDto pick () { // 担当： 上林
		
		String sql = "SELECT * FROM post;" ;
		
		ArrayList<PostDto> list = new ArrayList<PostDto>();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			// todo
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				PostDto post = new PostDto();
				post.setPostId(rs.getInt(1));
				post.setUserId(rs.getInt(2));
				post.setSource(rs.getString(3));
				post.setUrl(rs.getString(4));
				post.setGenreCd(rs.getString(5));
				post.setImage(rs.getString(6));
				post.setAnonyFlag(rs.getBoolean(7));
				post.setDate(rs.getDate(8));
				
				list.add(post); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Random rand = new Random();
		// DB内の投稿数までの数字をランダムに生成
		int randomInt = rand.nextInt(list.size());
		
		return list.get(randomInt);
		
	}
	
	/**
	 * 投稿画面に遷移する際に、投稿できるジャンル一覧を取得するメソッドです。
	 * @return
	 */
	public ArrayList<GenreDto> getAllGenre () { // TODO
		String sql = "SELECT * FROM genre;";
		
		ArrayList<GenreDto> list = new ArrayList<GenreDto>(); // resultで返却されるべきリスト
		
		try {
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				String genreCd = rs.getString("genreCd");
	        		String genreName = rs.getString("genreName");
	            		GenreDto dto = new GenreDto(genreCd, genreName);
	            		list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}