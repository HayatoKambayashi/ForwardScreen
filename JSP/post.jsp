<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jp.co.akkodis.syumix.dto.PostDto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>投稿フォーム入力ページ</title>
<link rel="stylesheet" href="postStyle.css">
</head>
<%
	//セッションチェック：ログイン状況が取得できない場合、login.jspに飛ばす
	session = request.getSession(false);
	if (session == null || session.getAttribute("loginUser") == null) {
		response.sendRedirect("login.jsp");
		return;
	}
%>
<body>
	<header>
		<h1>投稿フォーム入力ページ</h1>
	</header>
	<main>
 		<p class="message"><c:out value="${message}"/></p>
 		<p><a href="mypage">マイページへ</a></p>
		<form action="post" method="post" enctype="multipart/form-data">   <!-- フォームの入れ子構造を改善 -->
			<table border="1">
				<tr>
					<td>ジャンル</td>
					<td>
					<select name="genreCd" id="genre">
					  <c:forEach var="genre" items="${allGenreList}">
					    <option value="${genre.genreCd}">${genre.genreName}</option>
					  </c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>コメント</td>
					<td><textarea name="source"></textarea></td>
				</tr>
				<tr>
					<td>画像</td>
					<td>
					<p><input type="file" name="image"></p>
				
				</td>
				</tr>
				<tr>
					<td>url</td>
					<td><input type="text" name="url" />
					</td>
				</tr>
				<tr>
					<td>匿名で投稿しますか？</td>
					<td>
						<label>
						<input type="checkbox" name="anonyFlag" value="false"> Yes
						</label>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="hidden" name="btn" value="post">
						<button type="submit" class="button">投稿</button>
					</td>
				</tr>
			</table>
		</form>
		<p><a href="maincontroller">メインページへ</a></p>
	</main>
</body>
</html>
