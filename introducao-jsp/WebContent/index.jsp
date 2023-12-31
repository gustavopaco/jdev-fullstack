<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" %>

<%@ taglib prefix="myprefix" uri="/WEB-INF/testetag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="cabecalhoActivity.jsp" />

	<!-- -----------------------SITE PARA ESTUDO JSP COMPLETO------------------------------------- -->
	<h2><a href="https://www.tutorialspoint.com/jsp/index.htm">SITE PARA ESTUDO DE JSP COMPLETO</a></h2>
	
	
	<h1>Bem vindo ao projeto de JSP</h1>


	<!-- Tag	->			"< %  % >"					SERVE PARA ESCREVER EM JAVA DENTRO DO JSP	-->
	<!-- Tag	-> 			"< %! % >"					SERVE PARA DECLARAR VARIAVEIS OU METODOS JAVA EM JSP -->
	<!-- Tag	->			"< %=  % >"					SERVE PARA IMPRIMIR EM JAVA NA TELA JSP  -->

	<!-- Tag	->			"< %@  % >"					Directivas, SERVE PARA IMPORTAR Classes JAVA, 
														SETAR INFORMACAO QUE PODE SER RECUPERADA EM OUTRA PAGINA  -->

	<!-- Tag	->			"< %@  % >"					Include, SERVE colocar um Fragmento
														de outra pagina em um pagina -->

	<!-- Tag	->			"< jsp: forwards>				Serve para direcionar para outra pagina,
														e tambem passar um parametro que sera recebido por request  -->

	<!-- OBJETO IMPLICITO -> 	"request"				Tem uma infinidade de metodos, RECEBER PARAMETRO DE OUTRA TELA -->

	<!-- OBJETO IMPLICITO -> 	"response"				Tem uma infinidade de metodos, DIRECIONAR PARA OUTRA TELA -->

	<!-- OBJETO IMPLICITO -> 	"application"			Tem uma infinidade de metodos, usado para receber parametro, 
														vindo do arquivo de configuracao WEB.xml -->

	<!-- OBJETO IMPLICITO -> 	"session"				Tem uma infinidade de metodos, SETAR E RECEBER OBJETOS SALVOS NA SESSION,
														como Dados de Login do Usuario, como SharedPreference do Android -->

	<!-- Comando	-> 			"out.print"				Nao eh mais usado, Serve para imprimir na Tela JSP -->

	<%
	out.print("Escrevendo em Java no JSP, seu sucesso garantido");
	%>

	<%=" -> Segundo Jeito de imprimir em Java no JSP"%>

	<form action="segundaActivity.jsp" id="form_id" style="margin: 20px;">
		<input id="nomePessoa" type="text" name="nomePessoa" />
		<button id="btn_salvar" type="submit">Salvar</button>
	</form>
	<!-- -----------------------Criando codigo JAVA dentro do JSP------------------------------------- -->

	<%!int cont = 2;

	public int retornaValor(int numero) {
		return numero * 3;
	}%>

	<!-- -----------------------Imprimindo na Tela------------------------------------- -->

	<h3>-----Imprimindo:-----</h3>
	<%="Valor da variavel declarada: " + cont%>

	<br>
	<%="Chamando metodo que multiplica * 3 valor passado como parametro: " + retornaValor(8)%>

	<!-- -----------------------Objeto Implicito Application------------------------------------- -->

	<h3>-----Application:-----</h3>
	<%=application.getInitParameterNames().nextElement()%>
	<br>
	<%=application.getInitParameter("estado")%>

	<!-- -----------------------Objeto Implicito Session------------------------------------- -->

	<h3>-----Session:-----</h3>
	<%
		request.getSession().setAttribute("idCurso", "Curso de JSP");
	%>

	<!-- -----------------------Directivas------------------------------------- -->

	<h3>-----Directivas:-----</h3>
	<%@ page import="java.util.Date"%>

	<%="Data de Hoje: " + new Date()%>

	<%@ page info="Directivas, Pagina do Curso de Java JSP."%>

	<br>
	<%=application.getServerInfo()%>
<%--	<%=.getServletInfo()%>--%>

	<%@ page errorPage="errorActivity.jsp"%>

	<br>
	<%=" -Setar AQUI- Calculo 100/0 para causar erro e enviar para Pagina errorActivity: " + 100 / 2%>

	<!-- -----------------------Include(Fragment)------------------------------------- -->

	<h3>-----Include-fragment:-----</h3>
	<%@ include file="fragmentInclude.jsp"%>

	<!-- -----------------------Tag Customizada------------------------------------- -->

	<h3>-----Tag Customizada:-----</h3>
	<myprefix:minhatag />

	<!-- -----------------------Tag JSP Forwards------------------------------------- -->

	<%-- 	<jsp:forward page="terceiraActivity.jsp">
		<jsp:param value="Aplicacao JSP, site Java avancado.com" name="paramforward"/>
	</jsp:forward> --%>

	<!-- -----------------------Tag JSP Beans, Processamento em Tempo de Execucao------------------------ -->

	<h3>-----Java Beans:-----</h3>
	<jsp:useBean id="beanProjetoJSP" class="beans.BeanProjetoJSP"
		scope="session" />

	<%=beanProjetoJSP.getImprime()%>

	<br>
	<%="Metodo de Calculo por Java Beans: " + beanProjetoJSP.getCalculaNumero(50)%>

	<form action="terceiraActivity.jsp" method="post" style="margin: 10px;">
		<table>
			<tr>
				<td><label>Nome:</label>
				<td><input id="nomePessoa1" type="text" name="nome"
					style="margin-left: 10px;" />
			</tr>

			<tr>
				<td><label>Data:</label>
				<td><input id="anoPessoa1" type="text" name="ano"
					style="margin-left: 10px;" />
			</tr>

			<tr>
				<td><label>Sexo:</label>
				<td><input id="sexo" type="text" name="sexo"
					style="margin-left: 10px;" />
			</tr>

			<tr>
				<td>
				<td><button id="btn_enviarPessoa1" type="submit" name="btn_enviar"
						style="margin: 10px;">Enviar</button>
			</tr>
		</table>
	</form>

	<!-- -----------------------Tag JSP Expression Language------------------------------------- -->

	<h3>-----Expression Language:-----</h3>
	<form action="quartaActivity.jsp" method="post" style="margin: 20px;">
		<table>
			<tr>
				<td><label>Nome:</label> <td><input id="nome" type="text" name="nome"
					style="margin-left: 10px;" />
			
			</tr>
			
			<tr>
				<td><label>Ano:</label>
				
				<td><input id="ano" type="text" name="ano"
					style="margin-left: 10px;" />
			
			</tr>

			<tr>
				<td>
				<td><button id="btn_enviar" type="submit" name="btn_enviar"
						style="margin: 10px;">Enviar</button>
			
			</tr>
		</table>
	</form>

	<% request.getSession().setAttribute("user", "gustavopaco@gmail.com");
		request.getSession().setAttribute("password", "123456");
	%>
		

	<!-- -----------------------Tag JSP Include------------------------------------- -->

					<jsp:include page="rodapeActivity.jsp" />


				</body>
</html>