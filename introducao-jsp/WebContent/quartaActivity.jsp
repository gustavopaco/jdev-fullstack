<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Nome: ${param.nome}
	<br>
	Ano: ${param.ano}
	<br>
	Login: ${sessionScope.user }
	<br>
	Senha: ${sessionScope.password }
	
</body>
</html>