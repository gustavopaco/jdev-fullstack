<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Tela de Cadastro</h2>
	<form action="cadastro" method="post">
		<label>Login:</label>
		<input type="text" id="login" name="login" />
		<br>
		<label>Password</label>
		<input type="password" id="password" name="password"/>
		<br>
		<button type="submit">Cadastrar</button>
	</form>
</body>
</html>