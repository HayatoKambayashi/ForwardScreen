<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メイン</title>
<link rel="stylesheet" href="main.css">
</head>
<body>
<div class="wrapper">
    <div class="container">
        <h2>投稿</h2>
        <p>UserID: ${data.userID}</p>
        <p>URL: <a href="${data.url}" target="_blank">${data.url}</a></p>
        <p>Source: ${data.source}</p>
        <c:if test="${not empty data.image}">
            <img src="${data.image}" alt="Image" class="image"/>
        </c:if>
	<div class="button-group">
    <form action="/maincontroller" method="get" class="button">
        <button type="submit">投稿を表示</button>
    </form>
    <form action="/managercontroller" method="get" class="admin-button">
        <button type="submit">管理者画面へ</button>
    </form>
    <form action="/post" method="get" class="post-button">
        <button type="submit">投稿入力画面へ</button>
    </form>
	</div>
	</div>
	</div>
</body>
</html>
