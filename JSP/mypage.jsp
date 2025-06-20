<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jp.co.akkodis.syumix.dto.UserDto, jp.co.akkodis.syumix.dto.PostDto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPage</title>
<link rel="stylesheet" href="mypageStyle.css">
</head>
<body>

<form action="main.jsp" method="get">
    <input type="submit" value="メインメニューへ">
</form>

<table>
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
if (list.size() > 0) {
	Iterator<PostDto> it = list.iterator();
	while (it.hasNext()) {
		PostDto post = it.next();
%>
      <tr>
        <td><%=post.getPostId() %></td>
        <td><%=post.getGenreCd() %></td>
        <td><%=post.getSource() %></td>
        <td><a href="<%=post.getUrl() %>"><%=post.getUrl() %></a></td>
        <td><img src="<%=post.getImage() %>" alt="投稿画像" /></td>
        <td>
          <form action="mypage" method="post">
            <input type="hidden" name="postId" value=<%=post.getPostId()%>>
			 <input type="submit" name="action" value="削除">
          </form>
        </td>
      </tr>
      
<%
	} // whileループ
} // if
%>
<!--    </c:forEach>-->
  </tbody>
</table>

</body>
</html>
