<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アップロードテスト</title>
</head>
<body>
	<p><%= request.getAttribute("message") %> </p>
	
	<form action="upload_file" method="post" enctype="multipart/form-data">
		<p><input type="file" name="imgFile"></p>
		<p><input type="submit" value="送信" /> <input type="reset" value="リセット" /></p>
	</form>
	<%
		String img = (String)request.getAttribute("img");
		if (img != null) {
	%>
	<p><img src="upload/<%= img %>"></p>
	<%}%>
</body>
</html>