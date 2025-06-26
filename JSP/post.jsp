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
	<script>
	function confirmPost() {
	    const confirmed = confirm("本当に投稿してもよろしいですか？");
	    if (confirmed) {
	        document.querySelector('form').submit();
	    }
	}
	</script>


<body>

	<header>
		<h1>投稿フォーム入力ページ</h1>
	</header>
	<main>
 		<p class="message"><c:out value="${message}" escapeXml="false"/></p>
 		<p><a href="mypage">マイページへ</a></p>
		<form action="post" method="post" enctype="multipart/form-data">   <!-- フォームの入れ子構造を改善 -->
		<table border="1">
				<tr>
					<th>ジャンル</th>
					<td>
					<select name="genreCd" >
					  <c:forEach var="genre" items="${allGenreList}">
					<%-- if () --%>
					    <option value="${genre.genreCd}">${genre.genreName}</option>
					  </c:forEach>
					</select></td>
				</tr>
				<%
					// 投稿最終確認からのリダイレクト→入力内容保持　はここで準備しています。
					String source = (String) request.getAttribute("source");
					String url = (String) request.getAttribute("url");
					if (source == null) {
						source = "";
					}
					if (url == null) {
						url = "";
					}
				%>
				<tr>
					<th>コメント</th>
					<td><textarea name="source"><%=source%></textarea></td>
				</tr>
				<tr>
					<th>画像</th>
					<td>
					<p><input type="file" name="image"></p>
				
				</td>
				</tr>
				<tr>
					<th>url</th>
					<td><input type="text" name="url" value="<%= url %>"/>
					</td>
				</tr>
				<tr>
					<th>匿名で投稿しますか？</th>
					<td>
						<label>
						<input type="checkbox" name="anonyFlag" value="true"> Yes
						</label>
					</td>
				</tr>
				<tr>
				  <td colspan="2" align="center">

				      <input type="hidden" name="btn" value="post">
				      <button type="button" class="button" onclick="confirmPost()">投稿</button>

				  </td>
				</tr>
			</table>
		</form>
		<p><a href="maincontroller">メインページへ</a></p>
	</main>
</body>
</html>
