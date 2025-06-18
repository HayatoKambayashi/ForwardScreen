<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>password change</title>
<link rel="stylesheet" href="loginStyle.css">
</head>
<body>
    <div class="login-container">
        <h2>パスワード変更</h2>
        <form action="login" method="post">
        <input type="hidden" name="id" value="1"> 
        <input type="hidden" name="userId" value="<%=request.getAttribute("username");%>">
        
            <label for="currentPassword">新規パスワード:</label>
            <input type="text" id="currentPassword" name="password" required>
            <input type="submit" value="変更する">
        </form>

        <%-- エラーメッセージがあれば表示 --%>
        <% String msg = (String) request.getAttribute("message"); %>
        <% if (msg != null) { %>
            <p class="message"><%= msg %></p>
        <% } %>
    </div>
</body>
</html>