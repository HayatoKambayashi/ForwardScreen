<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jp.co.akkodis.syumix.dto.UserDto, jp.co.akkodis.syumix.dto.PostDto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  String temp = (String) request.getAttribute("deleteflug");
  Boolean flug = Boolean.parseBoolean(temp);
  if (flug == true) {
  %>
  
  <p><h3>削除しました。</h3></p>
  
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
    
<!--    <c:forEach var="post" items="\{postList}">-->
<%
Object obj = request.getAttribute("userPosts");
ArrayList<PostDto> list = (ArrayList<PostDto>) obj;
for (PostDto post:list) {
%>
      <tr>
        <td><%=post.getPostId() %></td>
        <td><%=post.getGenreCd() %></td>
        <<td><%=post.getSource() %></td>
        <td><%=post.getUrl() %></td>
        <td><%=post.getImage() %></td>
        <td>
          <form action="mypage" method="post">
            <input type="hidden" name="postId" value=<%=post.getPostId()%>
            <input type="submit" name="action" value="メインページへ" />
            <input type="submit" name="action" value="削除" />
          </form>
        </td>
      </tr>
      
<%
}
%>
<!--    </c:forEach>-->
  </tbody>
</table>

</body>
</html>
