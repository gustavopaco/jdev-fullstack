<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>Bem vindo ao projeto de JSP</h1>


	<!-- Tag	->			"< %  % >"		SERVE PARA ESCREVER EM JAVA DENTRO DO JSP	-->
	<!-- Tag	-> 			"< %! % >"		SERVE PARA DECLARAR VARIAVEIS OU METODOS JAVA EM JSP -->
	<!-- Tag	->			"< %=  % >"		SERVE PARA IMPRIMIR EM JAVA NA TELA JSP  -->

	<!-- Comando	-> 		"out.print"		Nao eh mais usado, Serve para imprimir na Tela JSP -->

	<%
	out.print("Escrevendo em Java no JSP, seu sucesso garantido");
	%>



	<%=" -> Segundo Jeito de imprimir em Java no JSP"%>

	<form action="receberParametro.jsp" id="form_id" style="margin: 20px;">
		<input id="nome" type="text" name="nome" />
		<button id="btn_salvar" type="submit">Salvar</button>
	</form>



	<%!int cont = 2;

	public int retornaValor(int numero) {
		return numero * 3;
	}%>

	</br>
	<%="Valor da variavel declarada: " + cont%>

	</br>
	<%="Chamando metodo que multiplica * 3 valor passado como parametro: " + retornaValor(8)%>

</body>
</html>