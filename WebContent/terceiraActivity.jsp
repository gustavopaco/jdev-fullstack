<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<!-- ------------Objeto Implicito Session, Obtendo atributo da Session ------------------------ -->
	
	<%= "Nome do Curso na Session: " + session.getAttribute("idCurso") %>
	
	
	<!-- -----------------------Tag Forwards------------------------------------- -->
	</br>
	<%= request.getParameter("paramforward") %>
	
</body>
</html>