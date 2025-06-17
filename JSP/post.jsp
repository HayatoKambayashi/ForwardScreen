<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>投稿フォーム入力ページ</title>
</head>
<body>
	<header>
		<h1>投稿フォーム入力ページ</h1>
	</header>
	<main>
 		<p class="message"><c:out value="${message}"/></p>
		<form action="post" method="post">
			<table border="1">
				<tr>
					<td>ジャンル</td>
					<td>
					<select name="genre" id="genre">
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
						<form action="upload_file" method="post" enctype="multipart/form-data">
							<p><input type="file" name="image"></p>
						</form>
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
						<form action="insert" method="post">
						<label>
						<input type="checkbox" name="anonyFlag" value="false"> Yes
						</label>
						</form>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="hidden" name="btn" value="post">
						<input type="submit" value="投稿">
					</td>
				</tr>
			</table>
		</form>
		<p><a href="main">メインページへ</a></p>
	</main>
</body>
</html>
