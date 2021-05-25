<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <!--===============================================================================================-->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/fonts/css-table-16/icomoon/style.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/css-table-16/owl.carousel.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/css-table-16/bootstrap.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/css-table-16/style.css">
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="resources/images/icons/favicon.ico"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/estilo.css">
    <!--===============================================================================================-->
    <title>Insert title here</title>
</head>
<body class="body-background-color">

<div style="display: flex;justify-content: flex-end;margin-top: 30px; margin-right: 25px;">
    <button style="background-color: transparent;border: none;color: white;">Home</button>
    <a href="productsActivity.jsp">
        <button style="background-color: transparent;margin-left: 30px;margin-right: 30px;border: none;color: white;">
            Products
        </button>
    </a>
    <button style="background-color: transparent;border: none;color: white;">Logout</button>
</div>

<div class="divtablesize">

    <div class="">
        <h1 class="mb-5 textstyle ">Usuarios</h1>

        <div class="table-responsive">

            <table class="table table-striped tabelacustomizada">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Birthday</th>
                    <th scope="col">Gender</th>
                    <th scope="col">CPF</th>
                    <th scope="col">Phone</th>
                    <th scope="col">Email</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${usuarios}">
                    <tr scope="row">
                        <td><c:out value="${user.name}"/></td>
                        <td>${user.birthday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}</td>
                        <td><c:out value="${user.gender}"/></td>
                        <td><c:out value="${user.cpf}"/></td>
                        <td><c:out value="${user.phone}"/></td>
                        <td><c:out value="${user.login}"/></td>
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
<script src="resources/js/css-table-16/jquery-3.3.1.min.js"></script>
<script src="resources/js/css-table-16/popper.min.js"></script>
<script src="resources/js/css-table-16/bootstrap.min.js"></script>
<script src="resources/js/css-table-16/main.js"></script>
</body>
</html>

