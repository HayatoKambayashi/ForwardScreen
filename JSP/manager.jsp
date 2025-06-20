<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jp.co.akkodis.syumix.dto.UserDto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>manager form</title>
</head>
<body>
<h2>ユーザ一覧</h2>

   <!-- ユーザの一覧表示 -->
    <p style="color: green;">
        <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
    </p>

<table border="1">
    <tr>
        <th>ユーザID</th>
        <th>ユーザ名</th>
        <th>仮パス発行</th>
    </tr>

    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.userName}</td>
            <td>
                <form action="managerpage" method="post">
                    <input type="hidden" name="userId" value="${user.userId}" />
                    <input type="submit" value="発行" />
                </form>
            </td>
        </tr>
    </c:forEach>

</table>

		<form action="main.jsp" method="get">
    		<input type="submit" value="戻る">
		</form>
	
</body>
</html>	