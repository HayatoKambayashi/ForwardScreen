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
            <label for="userName">ユーザーID:</label><br>
            <input type="text" id="userName" name="userId" required><br>

            <label for="password">パスワード:</label><br>
            <input type="password" id="password" name="password" required><br>
	    <p style="color: green;">
       	      <%= request.getAttribute("infoMessage") != null ? request.getAttribute("infoMessage") : "" %>
   	    </p>
            <input type="submit" value="ログイン">
        </form>
       </div>
        
        <p style="color:red;">
        <%	if (request.getAttribute("infoMessage") != null) {
        	System.out.print(infoMessage);
        }
        %>
        </p>
    
</body>
</html>
