<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jp.co.akkodis.syumix.dto.UserDto, jp.co.akkodis.syumix.dto.PostDto, jp.co.akkodis.syumix.dto.GenreDto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPage</title>
<link rel="stylesheet" href="mypageStyle.css">

<script>
function showConfirm(postId) {
    const confirmBox = document.getElementById("confirm-" + postId);
    confirmBox.style.display = "block";
}

function hideConfirm(postId) {
    const confirmBox = document.getElementById("confirm-" + postId);
    confirmBox.style.display = "none";
}
</script>

</head>
<%
	//セッションチェック：ログイン状況が取得できない場合、login.jspに飛ばす
	session = request.getSession(false);
	if (session == null || session.getAttribute("loginUser") == null) {
		response.sendRedirect("login.jsp");
		return;
	}
%>

<div class="wrapper">
<div class="container">
        <h1>マイページ</h2>
<body>
<div>
<form action="main.jsp" method="get">
    <input type="submit" value="メインメニューへ">
</form>
</div>

<div>
<table class="styled-table">
  <thead>
  	<%
	 // String temp = (String) request.getAttribute("deleteflug");
	 // boolean flug = Boolean.parseboolean(temp);
	 //boolean flug = (boolean) request.getAttribute("deleteflug");
	 //out.println(flug);
	 //if (flug == true) {
	Boolean flugObj = (Boolean) request.getAttribute("deleteflug");
	boolean flug = (flugObj != null) ? flugObj : false;
	if (flug) {
	
	%>
	
	<p><h3>削除しました。</h3></p>
	
	<% 
		}
	%>
  	
  	<tr>
      <th>投稿日時</th>
      <th>ジャンル</th>
      <th>コメント</th>
      <th>URL</th>
      <th>画像</th>
      <th class="col-mypSousa">操作</th>
      <th class="col-mypHiddenSousa"></th>
    </tr>
  </thead>
  <tbody>
    <%-- 投稿データ表示 --%>
    
<!--    <c:forEach var="post" items="\{postList}">-->
<%
Object obj = request.getAttribute("userPosts");
ArrayList<PostDto> list = (ArrayList<PostDto>) obj;
ArrayList<GenreDto> allGenre = (ArrayList<GenreDto>) request.getAttribute("allGenre");
String genreName;
if (list.size() > 0) {
	Iterator<PostDto> it = list.iterator();
	while (it.hasNext()) {
		PostDto post = it.next();
		for (GenreDto eachGenre : allGenre) {
			if (eachGenre.getGenreCd().equals(post.getGenreCd())) {
				genreName = eachGenre.getGenreName();
%>
      <tr>
		<td><a href="mypageDetail.jsp?pid=<%= post.getPostId() %>"><%=post.getDate() %></a></td>

        <td><%= genreName %></td>
        <td><%=post.getSource() %></td>
        <%
        	if (post.getUrl().length() <= 50) {
        %>
        <td><a href="<%=post.getUrl() %>"><%=post.getUrl() %></a></td>
        <% } else { %>
       	<td><a href="<%=post.getUrl() %>"><%=post.getUrl().substring(0,36) + "…" %></a></td>
       	<% } %>
        <td> <!-- 画像系表示のための追加要素 -->     	
		 <% String image = post.getImage(); %>
		<% if (image != null && (image.endsWith(".png") || image.endsWith(".jpg") || image.endsWith(".jpeg"))) { %>
		  <img src="upload/<%= image %>" alt="画像" class="img">
		<% } else if (image != null && image.endsWith(".gif")) { %>
		  <img src="upload/<%= image %>" alt="GIF画像" class="img">
		<% } else if (image != null && (image.endsWith(".mp4") || image.endsWith(".webm"))) { %>
		  <video style="max-height: 60px; border-radius: 4px;" controls class="img">
		    <source src="upload/<%= image %>" type="video/mp4">
		  </video>
		<% } %>
		<!-- 画像系表示のための追加要素 --></td>
		<td>
		  <!-- 削除ボタン（確認表示用） -->
		  <form class="remove-button">
		  <button type="button" onclick="showConfirm(<%=post.getPostId()%>)">削除</button>
		  </form>
		</td>
		<td>
		  <!-- 確認エリア（初期は非表示） -->
		  <div id="confirm-<%=post.getPostId()%>" style="display: none; margin-top: 5px;">
		    <form action="mypage" method="post" style="display: inline;">
		      <input type="hidden" name="postId" value="<%=post.getPostId()%>">
		      <input type="submit" name="action" value="はい、削除します">
		    </form>
		    <form class="remove-button">
		    <button type="button" onclick="hideConfirm(<%=post.getPostId()%>)">キャンセル</button>
		    </form>
		  </div>
		</td>

      </tr>
      
<%
			} // if文
		} // 拡張for文
	} // whileループ
} // if
%>
<!--    </c:forEach>-->
  </tbody>
</table>
</div>
</div>
</div>

</html>
