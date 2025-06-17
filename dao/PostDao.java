package jp.co.akkodis.syumix.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.akkodis.syumix.dto.PostDto;

public class PostDao extends Dao{
	
	public PostDao() throws ClassNotFoundException, SQLException {
		super();
	}
	
	public int delete(int postId) {
		
		return 0; // karioki
	}
	
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
				
				list.add(post); // TODO: 実装途中です。妥当性検証中
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
