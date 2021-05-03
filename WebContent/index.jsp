<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

	<%@ taglib prefix="myprefix" uri="WEB-INF/testetag.tld" %>
	
	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="cabecalhoActivity.jsp"/>

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

	<br>
	<%="Valor da variavel declarada: " + cont%>

	<br>
	<%="Chamando metodo que multiplica * 3 valor passado como parametro: " + retornaValor(8)%>

	<!-- -----------------------Objeto Implicito Application------------------------------------- -->

	<br>
	<%=application.getInitParameterNames().nextElement()%>

	<br>
	<%=application.getInitParameter("estado")%>

	<!-- -----------------------Objeto Implicito Session------------------------------------- -->

	<br>
	<%
	session.setAttribute("idCurso", "Curso de JSP");
	%>

	<!-- -----------------------Directivas------------------------------------- -->

	<%@ page import="java.util.Date"%>

	<br><br>
	<%="Data de Hoje: " + new Date()%>

	<%@ page info="Directivas, Pagina do Curso de Java JSP."%>

	<br >
	<%=getServletInfo()%>

	<%@ page errorPage="errorActivity.jsp" %>
	
	<br>
	<%= " -Setar AQUI- Calculo 100/0 para causar erro e enviar para Pagina errorActivity: " + 100/2 %>
	
	<!-- -----------------------Include(Fragment)------------------------------------- -->
	
	<br>
	<%@ include file="fragmentInclude.jsp" %>
	
	<!-- -----------------------Tag Customizada------------------------------------- -->
	
	<myprefix:minhatag/>
	
	<!-- -----------------------Tag JSP Forwards------------------------------------- -->

<%-- 	<jsp:forward page="terceiraActivity.jsp">
		<jsp:param value="Aplicacao JSP, site Java avancado.com" name="paramforward"/>
	</jsp:forward> --%>
	
	<!-- -----------------------Tag JSP Beans, Processamento em Tempo de Execucao------------------------ -->
	
	<jsp:useBean id="beanProjetoJSP" class="beans.BeanProjetoJSP" scope="session"/>
	
	
	<br><br>
	<%= beanProjetoJSP.getImprime() %>
	
	<br>
	<%= "Metodo de Calculo por Java Beans: " + beanProjetoJSP.getCalculaNumero(50) %>
	
	<form action="terceiraActivity.jsp" method="post" style="margin: 10px;">
	
		<label >Nome:</label>
		<input id="nome" type="text" name="nome"/>
		<br>
		<label >Data:</label>
		<input id="ano" type="text" name="ano"/>
		<br>
		<label>Sexo:</label>
		<input id="sexo" type="text" name="sexo"/>
		<br><br>
		<button id="btn_enviar" type="submit" name="btn_enviar">Enviar</button>
	</form>
	
	
	<!-- -----------------------Tag JSP Include------------------------------------- -->
		
	<jsp:include page="rodapeActivity.jsp"/>
	
</body>
</html>