<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 6/15/2021
  Time: 12:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Autenticar Usuario</h1>
<form action="login" method="post">
    <input type="hidden" name="action" id="action" value="logar">
    <input type="hidden" name="urlEscolhida" id="urlEscolhida" value="${urlEscolhida}">
    <table>
        <tr>
            <td>
                <label>Login:</label></td>
            <td><input type="text" id="login" name="login" value="${login}"></td>
        </tr>
        <tr>

            <td><label>Password:</label></td>
            <td><input type="password" id="password" name="password" value="${password}"></td>
        </tr>
        <tr>
            <td>
            <td>
                <button type="submit">Logar</button>
            </td>
            </td>
        </tr>
        <tr>
            <td>
            <td>
                <h6 style="color: red">${msg1}</h6>
            </td>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
