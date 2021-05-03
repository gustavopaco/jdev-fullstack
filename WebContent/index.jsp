<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%-- <%@ page errorPage="acessonegado.jsp" %> --%>
	<h1>Bem Vindo, ao sistema em JSP</h1>
	
	<form action="LoginServlet" method="post">
		Email:<input id="email" type="text"  name="email">
		<br>
		Senha:<input id="senha" type="password" name="senha" >
		<br>
		<button type="submit">Logar</button>
	</form>
	
	
</body>
</html>