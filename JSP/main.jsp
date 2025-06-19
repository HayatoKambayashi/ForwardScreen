<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main</title>
</head>
<body>
	<style>
    .container {
        width: 80%;
        margin: 0 auto;
        font-family: Arial, sans-serif;
    }
    .admin-button {
        margin-top: 10px;
        background-color: #007BFF;
        color: white;
        border: none;
        padding: 8px 16px;
    }
    .image {
        max-width: 100%;
        height: auto;
    }
</style>
<body>
    <div class="container">
        <h1>投稿</h1>
        <p>UserID: ${data.userID}</p>
        <p>URL: <a href="${data.url}" target="_blank">${data.url}</a></p>
        <p>Source: ${data.source}</p>
        <c:if test="${not empty data.image}">
            <img src="${data.image}" alt="Image" class="image"/>
        </c:if>
        <form action="/maincontroller" method="get">
            <button type="submit" class="button">表示</button>
        </form>
        <form action="/managercontroller" method="get" class="admin-button">
            <button type="submit">管理者画面へ</button>
        </form>
		<form action="/post" method="get" class="admin-button">
            <button type="submit">投稿入力画面へ</button>
        </form>
    </div>
</body>
</html>
