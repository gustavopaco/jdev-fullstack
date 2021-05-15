<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Usuario Logado</h3>
			<label>TABELA DE USUARIOS DO SISTEMA</label>
	<table>
			<c:forEach items="${usuarios}" var="user">
				<tr>
					<td><label>Login:</label></td>
					<td><c:out value="${user.login}"/></td>
					<td><label style="margin-left: 50px;">Password:</label></td>
					<td><c:out value="${user.password}"/></td>

					<td><a href="deletarUser?id=${user.id}"><button type="submit" style="margin-left: 40px;">Deletar</button></a></td>
				</tr>
			</c:forEach>
	</table>
</body>
</html>

