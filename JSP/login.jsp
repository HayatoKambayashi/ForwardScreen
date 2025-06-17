<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>ログインページ</title>
	<link rel="stylesheet" href="loginStyle.css">
</head>
<body>
    <div class="login-container">
        <h2>ログイン</h2>
        <form action="loginController" method="post">
            <label for="userName">ユーザー名:</label><br>
            <input type="text" id="userName" name="userid" required><br>

            <label for="password">パスワード:</label><br>
            <input type="password" id="password" name="password" required><br>

            <input type="submit" value="ログイン">
        </form>
        <% 
            String infoMessage = (String) request.getAttribute("infoMessage");
            if (infoMessage != null) {
        %>
            <p class="error"><%= errorMessage %></p>
        <% } %>
    </div>
</body>
</html>
