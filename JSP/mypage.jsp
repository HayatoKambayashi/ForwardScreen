<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPage</title>
</head>
<body>
<table>
  <thead>
  <%
  if (flug == true;) {
  %>
  
  <p><h3>削除しました。</h3>
  
  <% 
  }
  %>
  
    <tr>
      <th>投稿ID</th>
      <th>ジャンル</th>
      <th>コメント</th>
      <th>URL</th>
      <th>画像</th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody>
    <%-- 投稿データ表示 --%>
    <c:forEach var="post" items="\{postList}">
      <tr>
        <td>${post.postId}</td>
        <td>${post.genreCd}</td>
        <td>${post.source}</td>
        <td><a href="${post.url}">${post.url}</a></td>
        <td><img src="${post.image}" alt="投稿画像" /></td>
        <td>
          <form action="mypage" method="post">
            <input type="hidden" name="postId" value="${post.postId}" />
            <input type="submit" name="action" value="delete" />
          </form>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>

</body>
</html>