<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jp.co.akkodis.syumix.dto.UserDto" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>password change</title>
<link rel="stylesheet" href="passChange.css">
</head>

<!--<%-->
<!--	//セッションチェック：ログイン状況が取得できない場合、login.jspに飛ばす-->
<!--	session = request.getSession(false);-->
<!--	if (session == null || session.getAttribute("loginUser") == null || session.getAttribute("manager") == null) {-->
<!--		response.sendRedirect("login.jsp");-->
<!--		return;-->
<!--	}-->
<!--%>-->

<body>
    <div class="login-container">
        <h2>パスワード変更</h2>
        <form action="login" method="post">
        <input type="hidden" name="id" value="1"> 
        
        <%-- userNameを表示 --%>
        <% 	UserDto user = (UserDto) session.getAttribute("loginUser");
        	String userName = user.getUserName();%>
        <p class="userName"><%= "ユーザー名 : "+userName %></p><br>
        
        <%-- エラーメッセージがあれば表示 --%>
        <% String msg = (String) request.getAttribute("message");%>
        <% if (msg != null) { %>
            <p class="infoMessage", style="color:red;"><%= msg %></p>
        <% } %>
        
        <input type="hidden" name="userId" value="<%=request.getAttribute("userId")%>"> <%-- getAttribute("userId")は元々は("username")である。userNameを画面表示するために新たにgetAttribute("userName")を使用できるよう変更(17行目と関連) --%>
        
            <label for="currentPassword">新規パスワード</label>
            <input type="password" id="currentPassword" name="password" required><br>
            <label for="checkCurrentPassword">新規パスワード確認</label>
            <input type="password" id="checkCurrentPassword" name="checkPassword" required>
            <button type="submit" value="変更する">変更する</button>
        </form>
<!--        <form action="${pageContext.request.contextPath}/logout" method="get">-->
<!--    		<button type="submit" value="ログイン画面へ戻る">ログイン画面へ戻る</button>-->
			<p><a href="${pageContext.request.contextPath}/login.jsp">ログイン画面へ</a></p>
<!--    		<p><a href="login">ログイン画面へ戻る</a></p>-->
		</form>
    </div>
</body>
</html>
