<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
 <c:out value="${'Bem vindo ao JSTL'}"/>

<!------------- IMPORTA TODOS OS DADOS DA PAGINA WEB ------------->
<h3> ------------ Importando todos codigos de dados de uma Pagina Web ------------</h3>
<%--<c:import var="data" url="https://www.globo.com"/>--%>
<%--<c:out value="${data}"/>--%>

<%--<!-- SETAR UM PARAMETRO USANDO JSTL -->--%>
<%--<h3>------------ Setar um Parametro usando JSTL ------------</h3>--%>

<%--<c:set var="data" scope="session" value="${500*6 }"/>--%>
<%--<c:out value="Valor do parametro na tela: ${data}"/>--%>

<%--<!------------- REMOVENDO PARAMETRO ANTES DE IMPRIMIR NA TELA ------------>--%>
<%--<h3>------------ Removendo Parametro antes de Imprimir ------------</h3>--%>

<%--<c:set var="data2" scope="session" value="${500*6}"/>--%>
<%--<c:remove var="data2"/>--%>
<%--<c:out value="Valor do parametro na tela: ${data2}"/>--%>

<%--<!----------- LANCANDO EXCESSAO DE NUMERO NAO DIVISIVEL POR 0 ----------->--%>
<%--<h3>------------ Lancando Excessao numero nao Divisivel por 0 ------------</h3>--%>

<%--<c:catch var="erro">--%>
<%--    <%=100 / 3 %>--%>
<%--</c:catch>--%>

<%--<c:if test="${erro != null }">--%>
<%--    ${erro.message}--%>
<%--</c:if>--%>


<%--<!-- SETANDO UM VALOR E VERIFICANDO SE ELE EH MAIOR OU MENOR E IMPRIMINDO -->--%>
<%--<h3>------------ Verificando se eh menor, maior ou igual(poderia ser uma pagina JSP cada condicao) ------------ </h3>--%>

<%--<c:set var="numero" value="${100/3}"/>--%>

<%--<c:choose>--%>

<%--    <c:when test="${numero > 50 }">--%>
<%--        <c:out value="${'Maior que 50.' }"/>--%>
<%--    </c:when>--%>

<%--    <c:when test="${numero < 50 }">--%>
<%--        <c:out value="${'Menor que 50.' }"/>--%>
<%--    </c:when>--%>

<%--    <c:otherwise>--%>
<%--        <c:out value="${'Igual a 50.' }"/>--%>
<%--    </c:otherwise>--%>

<%--</c:choose>--%>

<%--<!-- FOR EACH -->--%>
<%--<h3>------------ Percorrendo numero em um ForEach ------------ </h3>--%>
<%--<c:forEach var="n" begin="1" end="${numero }">--%>

<%--    <br>Item: ${n}--%>

<%--</c:forEach>--%>

<%--<!-- QUEBRA DE STRING COMO STRING.SPLIT -->--%>
<%--<h3>------------ Quebrando string parecido com string.split ------------</h3>--%>
<%--<c:forTokens items="Gustavo-Dutra-Ramos-Paco" delims="-" var="nome">--%>
<%--    <br>Nome: <c:out value="${nome}"/>--%>
<%--</c:forTokens>--%>

<%--<!-- MONTANDO URL POR PARAMETROS -->--%>
<%--<h3>------------ Montando URL por parametros e setando na variavel - acesso ------------</h3>--%>
<%--<c:url value="/acessoliberado.jsp" var="acesso">--%>
<%--    <c:param name="param1" value="Roberio"/>--%>
<%--    <c:param name="param2" value="Teclados"/>--%>
<%--</c:url>--%>

<%--${acesso}--%>

<%--<!-- REDIRECIONANDO PARA OUTRA PAGINA A PARTIR DE UMA CONDICAO -->--%>
<%--<h3>------------ Redirecionando para outra pagina a partir de uma condicao ------------</h3>--%>
<%--<c:if test="${numero > 50 }">--%>
<%--    <c:redirect url="https://www.google.com"/>--%>
<%--</c:if>--%>

<%--<c:if test="${numero < 50 }">--%>
<%--    <c:redirect url="https://www.uol.com.br"/>--%>
<%--</c:if>--%>


<%--<br>--%>
<%--<%@ page errorPage="acessonegado.jsp" %>--%>
<h1>Bem Vindo, ao sistema em JSP</h1>

<form action="LoginServlet" method="post">
    Email:<input id="email" type="text" name="email">
    <br>
    Senha:<input id="senha" type="password" name="senha">
    <br>
    <button type="submit">Logar</button>
</form>


</body>
</html>