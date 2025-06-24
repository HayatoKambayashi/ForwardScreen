<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
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
<div class="button-group">
        <h2>投稿</h2>
		<c:if test="${data.anonyFlag}">
    		<p>氏名: unknown</p>
		</c:if>
		<c:if test="${!data.anonyFlag}">
    		<p>氏名: ${name}</p>
		</c:if>
    	<p>ジャンル: ${genreName}</p>

<!-- Youtubeだけ埋め込みになるように調整 -->
		<c:if test="${not empty data.url}">
    	<c:choose>
        <c:when test="${fn:contains(data.url, 'youtube.com/watch?v=')}">
            <c:set var="videoId" value="${fn:substringAfter(data.url, 'v=')}" />
            <iframe width="560" height="315"
                    src="https://www.youtube.com/embed/${videoId}"
                    frameborder="0"
                    allowfullscreen>
            </iframe>
        </c:when>
        <c:otherwise>
            <p>URL: <a href="${data.url}" target="_blank">${data.url}</a></p>
        </c:otherwise>
    	</c:choose>
		</c:if>


		<c:if test="${not empty data.source}">
    		<p>Source: ${data.source}</p>
		</c:if>
<!--画像、動画を表示できるように  -->
		<c:choose>
		
    	<c:when test="${not empty data.image and (fn:endsWith(data.image, '.png') or fn:endsWith(data.image, '.jpg') or fn:endsWith(data.image, '.jpeg'))}">
        <img src="upload/${data.image}" alt="画像" class="image">
    	</c:when>
    	<c:when test="${not empty data.image and fn:endsWith(data.image, '.gif')}">
        <img src="upload/${data.image}" alt="GIF画像" class="image">
    	</c:when>
    	<c:when test="${not empty data.image and (fn:endsWith(data.image, '.mp4') or fn:endsWith(data.image, '.webm'))}">
        <video width="480" height="270" controls>
            <source src="upload/${data.image}" type="video/mp4" >
        </video>
    	</c:when>
    	<c:otherwise>
        
    	</c:otherwise>
		</c:choose>

	
    <form action="maincontroller" method="get" class="button">
        <button type="submit">投稿を表示</button>
    </form>
    <form action="post" method="get" class="post-button">
        <button type="submit">投稿入力画面へ</button>
    </form>
    <form action="mypage" method="get" class="mypage-button">
    <button type="submit">マイページへ</button>
	</form>
	<form action="${pageContext.request.contextPath}/logout" method="get" class="logout-button">
    <button type="submit">ログアウト</button>
	</form>
	</div>
	</div>
	</div>
</body>
</html>