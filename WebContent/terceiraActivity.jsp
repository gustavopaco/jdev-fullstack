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
	<br>
	<%= request.getParameter("paramforward") %>
	
	<!-- -----------------------Tag JSP Beans, Processamento em Tempo de Execucao------------------------ -->
	
	<jsp:useBean id="beanProjetoJSP" class="beans.BeanProjetoJSP" scope="session"/>
	<jsp:setProperty property="*" name="beanProjetoJSP"/>
	
	<br>
	<jsp:getProperty property="nome" name="beanProjetoJSP"/>
	<jsp:getProperty property="ano" name="beanProjetoJSP"/>
	<jsp:getProperty property="sexo" name="beanProjetoJSP"/>
	
	
</body>
</html>