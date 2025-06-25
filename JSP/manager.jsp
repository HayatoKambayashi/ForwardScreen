<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jp.co.akkodis.syumix.dto.UserDto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>manager form</title>
<link rel="stylesheet" href="manager.css">
</head>
<body>
<h2>ユーザ一覧</h2>

   <!-- ユーザの一覧表示 -->
   <!-- messageがあった場合表示 -->
    <p style="color: green;">
        <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
    </p>

<table border="1">
    <tr>
        <th>ユーザID</th>
        <th>ユーザ名</th>
        <th>仮パス発行</th>
        <th>在籍管理</th>
    </tr>

	<!-- 一覧を1行ずつ表示 -->
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.userName}</td>
            <td>
                <form action="managerpage" method="post">
                    <input type="hidden" name="userId" value="${user.userId}" />
                    <input type="submit" name="btn" value="発行" />
                </form>
            </td>
            
            <!-- retiredFlagがfalse(在籍中)の場合のみ「退職」ボタンを表示 -->
            <c:if test="${not user.retiredFlag}">
            <td>
              <form action="managerpage" method="post" onsubmit="return confirm('本当に退職処理を行いますか？');">
    			<input type="hidden" name="userId" value="${user.userId}" />
   				 <c:if test="${user.userId != UserDto.MANAGER_ID}">
        			<input type="submit" name="btn" value="退職" />
   				 </c:if>
				</form>
              
            </td>
        </c:if>

        </tr>
    </c:forEach>

</table>
	</form>
	<form action="${pageContext.request.contextPath}/logout" method="get">
    <button type="submit">ログアウト</button>
	</form>

<!--		<form action="main.jsp" method="get">-->
<!--    		<input type="submit" value="戻る">-->
<!--		</form>-->
	
</body>
</html>	
