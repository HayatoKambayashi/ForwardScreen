package jp.co.akkodis.syumix.dto;

import java.sql.Date;

public class PostDto {

	/*
	 *  このソースコード内では以下の略称を定義します。＿制作: 矢島
	 *  DD：詳細設計書(detail design)
	 */
	private int postId; //DDﾌｨｰﾙﾄﾞ表 項番1
	private int userId; //DDﾌｨｰﾙﾄﾞ表 項番2
	private String source;  //DDﾌｨｰﾙﾄﾞ表 項番3
	private String url; //DDﾌｨｰﾙﾄﾞ表 項番4
	private String genreCd;       //DDﾌｨｰﾙﾄﾞ表 項番5
	private String image;          //DDﾌｨｰﾙﾄﾞ表 項番6
	private boolean anonyFlag; //DDﾌｨｰﾙﾄﾞ表 項番7
	private Date date;              //DDﾌｨｰﾙﾄﾞ表 項番8
	
	// getter, setter
	public int getPostId() { //DDﾒｿｯﾄﾞ表 項番1
		return postId;
	}
	public void setPostId(int postId) { //DDﾒｿｯﾄﾞ表 項番2
		this.postId = postId;
	}
	public int getUserId() { //DDﾒｿｯﾄﾞ表 項番3
		return userId;
	}
	public void setUserId(int userId) { //DDﾒｿｯﾄﾞ表 項番4
		this.userId = userId;
	}
	public String getSource() { //DDﾒｿｯﾄﾞ表 項番5
		return source;
	}
	public void setSource(String source) { //DDﾒｿｯﾄﾞ表 項番6
		this.source = source;
	}
	public String getUrl() { //DDﾒｿｯﾄﾞ表 項番7
		return url;
	}
	public void setUrl(String url) { //DDﾒｿｯﾄﾞ表 項番8
		this.url = url;
	}
	public String getGenreCd() { //DDﾒｿｯﾄﾞ表 項番9
		return genreCd;
	}
	public void setGenreCd(String genreCd) { //DDﾒｿｯﾄﾞ表 項番10
		this.genreCd = genreCd;
	}
	public String getImage() { //DDﾒｿｯﾄﾞ表 項番11
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean isAnonyFlag() {
		return anonyFlag;
	}
	public void setAnonyFlag(boolean anonyFlag) {
		this.anonyFlag = anonyFlag;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
