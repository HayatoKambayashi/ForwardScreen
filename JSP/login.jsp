<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>ログインページ</title>
<link rel="stylesheet" href="loginStyle.css">
</head>
<body>
    <div class="login-container">
        <h1>ログイン</h1>
        <form action="login" method="post">
            <label for="userName">ユーザーID:</label><br>
            <input type="text" id="userName" name="userId" required><br>

            <label for="password">パスワード:</label><br>
            <input type="password" id="password" name="password" required><br>

            <p style="color:red;">
                ${infoMessage}
            </p>

            <button type="submit">ログイン</button>
        </form>
    </div>
</body>
</html>