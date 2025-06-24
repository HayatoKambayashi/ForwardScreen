<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jp.co.akkodis.syumix.dao.UserDao, jp.co.akkodis.syumix.dao.PostDao, jp.co.akkodis.syumix.dto.GenreDto, jp.co.akkodis.syumix.dto.UserDto" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>投稿確認</title>
<link rel="stylesheet" href="main.css">
</head>
<body>
<div class="wrapper">
    <div class="container">
        <h2>次の内容で投稿してよろしいですか？</h2>
		<%
		UserDto userDto = (UserDto) session.getAttribute("loginUser");
			if (userDto == null) {
				userDto = new UserDto();
				// response.sendRedirect("maincontroller");
			}
			
				  String userId = Integer.toString(userDto.getUserId());
		 		  UserDao userDao = new UserDao();
				  String userName = (String) userDao.selectById(userId).getUserName();
				  String genreCd = request.getParameter("genreCd");
				  PostDao postDao = new PostDao();
				  
			ArrayList<GenreDto> allGenre = postDao.getAllGenre();
			String genreName = null;
			for (GenreDto each : allGenre) {
				if (each.getGenreCd().equals(genreCd)) {
					genreName = each.getGenreName();
				}
			}
			String source = request.getParameter("source");
			String url = request.getParameter("url");
			String anonyParam = request.getParameter("anonyFlag");
			String image = request.getParameter("image");
			if ((source == null || source.isEmpty()) ||
					(image == null || image.isEmpty()) ||
					(url == null || url.isEmpty())) {
					request.setAttribute("message", "いずれかの項目を入力してください");
					response.sendRedirect("post");
				}
			boolean anony = "true".equalsIgnoreCase(anonyParam);
		%>
		<% if (anony) { %>
		  <p>氏名: unknown</p>
		<% } else { %>
		  <p>氏名: <%= userName %></p>
		<% } %>
		<p>ジャンル名：<%= genreName %></p>
		<p>コメント：<%= source %></p>
		<p>URL：<%= url %></p>
		<p>画像: <p>

	<div class="button-group">
    <form action="post" method="post" class="button">
 	   <input type="hidden" name="btn" value="post">
 	   <input type="hidden" name="genreCd" value="<%= genreCd %>">
 	   <input type="hidden" name="source" value="<%= source %>">
 	   <input type="hidden" name="url" value="<%= url %>">
 	   <input type="hidden" name="anonyFlag" value="<%= anony ? "true" : "" %>">
        <button type="submit" name="btn" value="post">投稿</button>
        <button type="submit" name="btn" value="change-post">投稿入力画面へ戻る</button>
    </form>
    <form action="post" method="get" class="post-button">
        <%-- 61-63行目削除の可能性あり --%>
    </form>
	</div>
	</div>
	</div>
</body>
</html>
