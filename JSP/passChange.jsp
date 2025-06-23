<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>password change</title>
<link rel="stylesheet" href="loginStyle.css">
</head>

<%
	//セッションチェック：ログイン状況が取得できない場合、login.jspに飛ばす
	session = request.getSession(false);
	if (session == null || session.getAttribute("loginUser") == null || session.getAttribute("manager") == null) {
		response.sendRedirect("login.jsp");
		return;
	}
%>

<body>
    <div class="login-container">
        <h2>パスワード変更</h2>
        <form action="login" method="post">
        <input type="hidden" name="id" value="1"> 
        
        <%-- userNameを表示 --%>
        <% String userName = (String) request.getAttribute("userName");%>
        <p class="userName"><%= "ユーザー名 : "+userName %></p>
        
        <%-- エラーメッセージがあれば表示 --%>
        <% String msg = (String) request.getAttribute("message");%>
        <% if (msg != null) { %>
            <p class="infoMessage", style="color:red;"><%= msg %></p>
        <% } %>
        
        <input type="hidden" name="userId" value="<%=request.getAttribute("userId")%>"> <%-- getAttribute("userId")は元々は("username")である。userNameを画面表示するために新たにgetAttribute("userName")を使用できるよう変更(17行目と関連) --%>
        
            <label for="currentPassword">新規パスワード:</label>
            <input type="password" id="currentPassword" name="password" required>
            <label for="checkCurrentPassword">新規パスワード確認:</label>
            <input type="password" id="checkCurrentPassword" name="checkPassword" required>
            <input type="submit" value="変更する">
        </form>
        <form action="${pageContext.request.contextPath}/logout" method="get">
    		<button type="submit" value="ログイン画面へ戻る">ログイン画面へ戻る</button>
		</form>
    </div>
</body>
</html>
