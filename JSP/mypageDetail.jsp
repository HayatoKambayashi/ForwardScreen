<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jp.co.akkodis.syumix.dao.UserDao, jp.co.akkodis.syumix.dao.PostDao, jp.co.akkodis.syumix.dto.PostDto, jp.co.akkodis.syumix.dto.UserDto, jp.co.akkodis.syumix.dto.GenreDto" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<%
	
%>
<head>
<meta charset="UTF-8">
<title>投稿内容ページ</title>
<link rel="stylesheet" href="main.css">
</head>
<body>
<div class="wrapper">
    <div class="container">
        <h2>投稿情報 投稿ID:<%= request.getParameter("pid") %></h2>
		<%
			String postId = (String) request.getParameter("pid");
			PostDao dao = new PostDao();
			
			PostDto post = dao.selectOnePost(postId);
			UserDto loginUser = (UserDto) session.getAttribute("loginUser");
			if (post.getUserId() != loginUser.getUserId()) {
				response.sendRedirect("maincontroller"); // リダイレクト先：ログインまたはメインページ
			}
			
			String genreCd = post.getGenreCd();
			ArrayList<GenreDto> allGenre = dao.getAllGenre();
			String genreName = null;
			for (GenreDto each : allGenre) {
				if (each.getGenreCd().equals(genreCd)) {
					genreName = each.getGenreName();
				}
			}
		%>
		<% if (post.getAnonyFlag() ) { %>
		  <p>氏名: unknown</p>
		<% } else { %>
		  <p>氏名: <%= loginUser.getUserName() %></p>
		<% } %>
		<p>ジャンル名：<%= genreName  %></p>
		<% if ( !(post.getSource().isBlank() || post.getSource().isEmpty()) )
			{ %>
		<p>コメント：<%= post.getSource() %></p>
		<% } %>
		<%
	    String url = post.getUrl();
	    if (url != null && !url.trim().isEmpty()) {
	        String videoId = null;
	        String embedUrl = null;

	        if (url.contains("youtube.com/watch?v=")) {
	            videoId = url.substring(url.indexOf("v=") + 2);
	            int ampIndex = videoId.indexOf("&");
	            if (ampIndex != -1) {
	                videoId = videoId.substring(0, ampIndex);
	            }
	            embedUrl = "https://www.youtube.com/embed/" + videoId;

	        } else if (url.contains("youtu.be/")) {
	            videoId = url.substring(url.indexOf("youtu.be/") + 9);
	            int qIndex = videoId.indexOf("?");
	            if (qIndex != -1) {
	                videoId = videoId.substring(0, qIndex);
	            }
	            embedUrl = "https://www.youtube.com/embed/" + videoId;

	        } else if (url.contains("youtube.com/shorts/")) {
	            videoId = url.substring(url.indexOf("shorts/") + 7);
	            int qIndex = videoId.indexOf("?");
	            if (qIndex != -1) {
	                videoId = videoId.substring(0, qIndex);
	            }
	            embedUrl = "https://www.youtube.com/embed/" + videoId;
	        }

	        if (embedUrl != null) {
	%>
	            <iframe width="560" height="315"
	                    src="<%= embedUrl %>"
	                    frameborder="0"
	                    allowfullscreen>
	            </iframe>
	<%
	        } else if (url.length() > 50) {
	%>
	            <p>URL：<a href="<%= url %>" target="_blank"><%= url.substring(0, 36) + "…" %></a></p>
	<%
	        } else { %>
	            <p>URL：<a href="<%= url %>" target="_blank"><%= url%></a></p>
	<%     } // end of inner if-else
	    } // end of outer if
	%>
		
		<% String image = post.getImage();
		if (image != null && (image.endsWith(".png") || image.endsWith(".jpg") || image.endsWith(".jpeg")))
		{ %>
		  <img src="upload/<%= image %>" alt="画像" class="img">
		<% } else if (image != null && image.endsWith(".gif")) { %>
		  <img src="upload/<%= image %>" alt="GIF画像" class="img">
		<% } else if (image != null && (image.endsWith(".mp4") || image.endsWith(".webm"))) { %>
		  <video style="max-height: 60px; border-radius: 4px;" controls class="img">
		    <source src="upload/<%= image %>" type="video/mp4">
		  </video>
		<% } %>

			<form action="mypage" method="get" class="mypage-button">
				<button type="submit">マイページへ</button>
			</form>
			<form action="mypage" method="post" class="mypage-button" enctype="multipart/form-data" onsubmit="return confirm('本当に削除してよろしいですか？');">
				<input type="hidden" name="postId" value="<%=postId%>">
				<button type="submit" name="action" value="はい、削除します">削除</button>
			</form>

	</div>
	</div>
</body>
</html>
