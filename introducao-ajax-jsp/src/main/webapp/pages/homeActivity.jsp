<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 6/15/2021
  Time: 12:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div style="margin-left: auto; width: fit-content; margin-right: 50px; margin-top: 20px;">
        <a href="#" style="font-size: 25px; text-decoration: none; margin-right: 30px;">Home</a>
        <a href="login?action=deslogar" style="font-size: 25px; text-decoration: none;">Deslogar</a>
    </div>
    <h1>Home Activity - Usuario Logado</h1>
    <h4>Login: ${sessionScope.usuarioSession.login}</h4>
    <h4>Password: ${sessionScope.usuarioSession.password}</h4>
</body>
</html>
