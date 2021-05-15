<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
<body>
<h2>Tela de Cadastro</h2>
<form action="cadastroCtl" method="post">
    <table>
        <tr>
            <td><label>Login:</label></td>
            <td><input type="text" id="login" name="login"/></td>
        </tr>
        <tr>
            <td><label>Password</label></td>
            <td><input type="password" id="password" name="password"/></td>
        </tr>
        <tr>
            <td>
                <button type="submit">Cadastrar</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>