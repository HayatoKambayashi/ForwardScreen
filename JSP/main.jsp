<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
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
		<c:if test="${data.anonyFlag}">
    		<p>氏名: unknown</p>
		</c:if>
		<c:if test="${!data.anonyFlag}">
    		<p>氏名: ${name}</p>
		</c:if>
    	<p>ジャンル: ${genreName}</p>

		<c:if test="${not empty data.url}">
    		<p>URL: <a href="${data.url}" target="_blank">${data.url}</a></p>
		</c:if>

		<c:if test="${not empty data.source}">
    		<p>Source: ${data.source}</p>
		</c:if>

		<c:if test="${not empty data.image}">
    		<img src="${data.image}" alt="Image" class="image"/>
		</c:if>

	<div class="button-group">
    <form action="maincontroller" method="get" class="button">
        <button type="submit">投稿を表示</button>
    </form>
    <form action="post" method="get" class="post-button">
        <button type="submit">投稿入力画面へ</button>
    </form>
    <form action="mypage" method="get" class="mypage-button">
    <button type="submit">マイページへ</button>
	</form>
	</div>
	</div>
	</div>
</body>
</html>