package jp.co.akkodis.syumix.dto;

public class UserDto {
	
	private int userId;
	private String userName;
	private String pass;
	private boolean retiredFlag;
	private boolean passFlag;
	private boolean managerFlag;
	
	// getter, setter
	/*
	 *  このソースコード内では以下の略称を定義します。＿制作: 矢島
	 *  DD：詳細設計書(detail design)
	 */
	
	public int getUserId() { // DD項番1
		return userId;
	}
	public void setUserId(int userId) { // DD項番2
		this.userId = userId;
	}
	public String getUserName() { // DD項番3
		return userName;
	}
	public void setUserName(String userName) { // DD項番4
		this.userName = userName;
	}
	public String getPass() { // DD項番5
		return pass;
	}
	public void setPass(String pass) { // DD項番6
		this.pass = pass;
	}
	public boolean getRetiredFlag() { // DD項番7
		return retiredFlag;
	}
	public void setRetiredFlag(boolean retiredFlag) { // DD項番8
		this.retiredFlag = retiredFlag;
	}
	public boolean getPassFlag() {  // DD項番9
		return passFlag;
	}
	public void setPassFlag(boolean passFlag) { // DD項番10
		this.passFlag = passFlag;
	}
	public boolean getManagerFlag() { // DD項番12
		return managerFlag;
	}
	
	

}