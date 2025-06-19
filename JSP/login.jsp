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
        <form action="login" method="post">
            <label for="userName">ユーザーid:</label><br>
            <input type="text" id="userName" name="userId" required><br>

            <label for="password">パスワード:</label><br>
            <input type="password" id="password" name="password" required><br>
			<p style="color:red;">
				${infoMessage}
        	</p>
            <input type="submit" value="ログイン">
        </form>
    </div>
</body>
</html>
