<jsp:useBean id="usuarioSession" scope="session" type="models.Usuario"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" %>
<html>
<head>
    <!--===============================================================================================-->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/fonts/icomoon/style.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/table-css/owl.carousel.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/table-css/style.css">
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="resources/images/favicon.ico"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/register-css/mainreg.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/tabelaresponsiva.css">
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/estilo.css">
    <!--===============================================================================================-->
    <title>Insert title here</title>
</head>
<body class="bg-gra-02">
<div style="display: flex; justify-content: flex-end; margin-top: 30px; margin-right: 25px;">
    <form action="login" method="post">
        <input type="hidden" name="login" id="sessionLogin" value="${sessionScope.usuarioSession.login}">
        <input type="hidden" name="password" id="sessionPassword" value="${sessionScope.usuarioSession.password}">
        <button type="submit" style="background-color: transparent; border: none; color: white; font-size: 20px;">Home
        </button>
    </form>
    <a href="produtos?action=listar">
        <button style="background-color: transparent; margin-left: 30px; margin-right: 30px; border: none; color: white; font-size: 20px;">
            Products
        </button>
    </a>
    <a href="index.jsp">
        <button style="background-color: transparent; border: none; color: white; font-size: 20px;">Logout</button>
    </a>
</div>


<div class="p-t-100" style="display: block; background-color: transparent; position: relative;">
    <div class="card card-4 wrapper" style="max-width: 1440px; min-width: 1440px;">
        <div class="card-body">
            <h1 class="title">Usuarios</h1>
            <div class="tabela-responsiva">
                <table class="tabelacustomizada2" style="min-width: 100%;">
                    <thead>
                    <tr>
                        <th scope="col" style="color: #555;">Name</th>
                        <th scope="col" style="color: #555;">Birthday</th>
                        <th scope="col" style="color: #555;">Gender</th>
                        <th scope="col" style="color: #555;">CPF</th>
                        <th scope="col" style="color: #555;">Email</th>
                        <th scope="col" style="color: #555;">Address</th>
                        <th scope="col" style="color: #555;">Phone</th>
                        <th scope="col" style="color: #555;">Delete</th>
                        <th scope="col" style="color: #555;">Edit</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${usuarios}">
                        <tr scope="row" style="text-align: center;">
                            <td><c:out value="${user.name}"/></td>
                            <td>${user.birthday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}</td>
                            <td><c:out value="${user.gender}"/></td>
                            <td><c:out value="${user.cpf}"/></td>
                            <td><c:out value="${user.login}"/></td>
                            <td><a href="address?action=ownerAddress&id_usuario=${user.id}"><img class="imagesize" src="resources/images/address.png" alt="Address"
                                                title="Address"></a></td>
                            <td><a href="phone?action=ownerPhone&id_usuario=${user.id}"><img class="imagesize" src="resources/images/telephone.png" alt="Telephone"
                                                title="Telephone"></a></td>
                            <td><a href="cadastroCtl?action=delete&id=${user.id}"><img class="imagesize"
                                                                                       src="resources/images/deleteimg.png"
                                                                                       title="Delete"/></a></td>
                            <td><a href="cadastroCtl?action=edit&id=${user.id}"><img class="imagesize"
                                                                                     src="https://img.icons8.com/dusk/64/000000/edit--v2.png"
                                                                                     title="Edit"/></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="resources/js/table-js/jquery-3.3.1.min.js"></script>
<script src="resources/js/table-js/popper.min.js"></script>
<script src="resources/js/table-js/bootstrap.min.js"></script>
<script src="resources/js/table-js/main.js"></script>

</body>
</html>

