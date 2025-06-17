package jp.co.akkodis.syumix.dto;

public class GenreDto {
	private String genreCd;
	private String genreName;

	public GenreDto(String genreCd, String name) { //DD項番1
		this.genreCd = genreCd;
		this.genreName = name;
	}

	// getter
	/*
	 *  このソースコード内では以下の略称を定義します。＿制作: 矢島
	 *  DD：詳細設計書(detail design)
	 */
	
	public String getGenreCd() {  //DD項番2
		return genreCd;
	}

	public String getGenreName() { //DD項番3
		return genreName;
	}
	
}
