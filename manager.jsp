<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, jp.co.akkodis.syumix.UserDto" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>manager form</title>
</head>
<body>


    <h1>ユーザー一覧</h1>
    
    <!-- ユーザの一覧表示 -->
    <p style="color: green;">
        <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
    </p>
    
    <table border="1">
        <tr>
            <th>ユーザーID</th>
            <th>ユーザー名</th>
            <th>パスワード発行</th>
        </tr>
        
        <!-- サーブレットからユーザ情報を受け取る -->
        <%
            List<UserDto> userList = (List<UserDto>) request.getAttribute("userList");
            for (UserDto user : userList) {
        %>
        
        <!-- 人数分の「ユーザID」「ユーザ名」「仮パス発行ボタン」を表示 -->
        <tr>
            <td><%= user.getUserId() %></td>
            <td><%= user.getUserName() %></td>
            
            <!-- 発行ボタンを押すと「ユーザID」をサーブレットに渡す -->
            <td>
                <form action="managerpage" method="post">
                    <input type="hidden" name="userid" value="<%= user.getUserId() %>" />
                    <input type="submit" value="発行" />
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>

		<!-- メインページに飛ぶボタン（戻るページ名は未定） -->
		  
		<form action="****" method="get">
    		<input type="submit" value="戻る">
		</form>
	
</body>
</html>	
