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
	
	<!-- OBJETO IMPLICITO -> 	"request"				Tem uma infinidade de metodos, RECEBER PARAMETRO DE OUTRA TELA -->
	
	<!-- OBJETO IMPLICITO -> 	"response"				Tem uma infinidade de metodos, DIRECIONAR PARA OUTRA TELA -->


	<%
	String nome = "Nome recebido: " + request.getParameter("nome");
	out.print(nome);
	%>

	</br></br>
	<%="Nome recebido: " + request.getParameter("nome")%>
	
	</br>
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
<%-- 	</br></br>
	<% response.sendError(404, "OCORREU UM ERRO INESPERADO."); %> --%>



	<!-- REDIRECIONANDO PARA PAGINA DA GLOBO -->
<%-- 	</br>
	<% response.sendRedirect("https://www.globo.com/"); %> --%>
	
	<!-- ------------Objeto Implicito Session, Obtendo atributo da Session ------------------------ -->
	
	</br></br>
	<%= "Valor do atributo da Session: " + session.getAttribute("idCurso") %>
	
	</br>
	<%= "Valor da ID da Session: " + session.getId() %>
	
	<form action="terceiraActivity.jsp" id="form_id" style="margin: 20px;">
		<button id="btn_enviar" type="submit">Ir para Proxima Tela</button>
	</form>

</body>
</html>