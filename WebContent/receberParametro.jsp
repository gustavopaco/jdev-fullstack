<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Tela de Parametro</h1>

	<!-- Comando -> 		"request.getParameter"		SERVE PARA PEGAR PARAMETRO EM JSP -->

	<!-- Comando	-> 		"out.print"					Nao eh mais usado, Serve para imprimir na Tela JSP -->
	
	<!-- OBJETO IMPLICITO -> 	"request"				Tem uma infinidade de metodos para ser utilizados -->	
	
	<!-- OBJETO IMPLICITO -> 	"response"				Tem uma infinidade de metodos para ser utilizados -->	


	<%
	String nome = "Nome recebido: " + request.getParameter("nome");
	out.print(nome);
	%>

	</br>
	<%="Nome recebido: " + request.getParameter("nome")%>
	
	</br></br>
	<%= "Context path: " + request.getContextPath() %>
	
	</br>
	<%= "Local Name: " + request.getLocalName() %>
	
	</br>
	<%= "Protocolo: " + request.getProtocol() %>
	
	</br>
	<%= "Servlet Path: " + request.getServletPath() %>
	
	</br>
	<%= "Sessao: " + request.getSession() %>
	
	</br>
	<%= "Cookies: " + request.getCookies().toString() %>
	
	</br>
	<%= "Nome dos Parametros: " + request.getParameterNames().nextElement() %>
	
	<!-- -----------------------RESPONSE AGORA------------------------------------- -->

	
	<!-- ENVIANDO PARA PAGINA DE ERRO 404, por exemplo -->
<%-- 	</br>
	<% response.sendError(404, "OCORREU UM ERRO INESPERADO."); %> --%>



	<!-- REDIRECIONANDO PARA PAGINA DA GLOBO -->
<%-- 	</br>
	<% response.sendRedirect("https://www.globo.com/"); %> --%>

</body>
</html>