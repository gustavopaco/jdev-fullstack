<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
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

<div style="margin-left: auto; margin-right: auto; display: flex;">
    <div class="wrapper">
        <div class="card card-4">
            <div class="card-body">
                <form action="search" method="post" onsubmit="return validaBusca()">
                    <input type="hidden" id="action" name="action" value="searchUser">
                    <table>
                        <div style="position: relative;">
                            <tr>
                                <td><label class="label">Busca Usuarios</label>
                                    <input class="input--style-4" type="text" name="busca_usuario"
                                           placeholder="Informe o nome"
                                           id="busca_usuario">
                                </td>
                                <td>
                                    <button class="btn--radius-2 btn--green" type="submit"
                                            style="width: 120px; height: 50px; margin-left: 10px; margin-top: 25px;">
                                        Enviar
                                    </button>
                                </td>
                            </tr>
                        </div>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="p-t-100" style="display: block; background-color: transparent; position: relative;">
    <div class="card card-4 wrapper" style="max-width: 1440px; min-width: 1440px;">
        <div class="card-body">
            <h1 class="title">Usuarios</h1>
            <div class="tabela-responsiva">
                <table class="tabelacustomizada2" aria-describedby="Dados de usuario" style="min-width: 100%;">
                    <thead>
                    <tr>
                        <th scope="col" style="color: #555;">Image</th>
                        <th scope="col" style="color: #555;">Curriculo</th>
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
                            <c:choose>
                                <c:when test="${!user.miniaturaprofile.isEmpty() && user.miniaturaprofile != null}">
                                    <td><a href="cadastroCtl?action=download&ft=img&id_usuario=${user.id}"><img
                                            src="${user.miniaturaprofile}" width="32px" height="32px"
                                            alt="Imagem Usuario" title="Imagem Usuario"></a></td>
                                </c:when>
                                <c:otherwise>
                                    <td><img src="resources/images/defaultuser.png" alt="Default Image"
                                             title="Default Image" width="32px;" height="32px;"></td>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${!user.curriculo.isEmpty() && user.curriculo != null}">
                                    <td><a href="cadastroCtl?action=download&ft=curriculo&id_usuario=${user.id}"><img
                                            src="resources/images/AttachFile2.png" alt="Curriculo" title="Curriculo"
                                            width="32px" height="32px"></a></td>
                                </c:when>
                                <c:otherwise>
                                    <td><img src="resources/images/emptyFile.png" alt="Empty" title="Empty" width="32px"
                                             height="32px"></td>
                                </c:otherwise>
                            </c:choose>

                            <td><c:out value="${user.name}"/></td>
                            <td>${user.birthday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}</td>
                            <td><c:out value="${user.gender}"/></td>
                            <td><c:out value="${user.cpf}"/></td>
                            <td><c:out value="${user.login}"/></td>
                            <td><a href="address?action=ownerAddress&id_usuario=${user.id}"><img class="imagesize"
                                                                                                 src="resources/images/address.png"
                                                                                                 alt="Address"
                                                                                                 title="Address"></a>
                            </td>
                            <td><a href="phone?action=ownerPhone&id_usuario=${user.id}"><img class="imagesize"
                                                                                             src="resources/images/telephone.png"
                                                                                             alt="Telephone"
                                                                                             title="Telephone"></a></td>
                            <td><a href="cadastroCtl?action=delete&id=${user.id}"
                                   onclick="return confirm('Tem certeza que deseja deletar esse Usuario?');"><img
                                    class="imagesize"
                                    src="resources/images/deleteimg.png" alt="delete"
                                    title="Delete"/></a></td>
                            <td><a href="cadastroCtl?action=edit&id=${user.id}"><img class="imagesize"
                                                                                     src="https://img.icons8.com/dusk/64/000000/edit--v2.png" alt="edit"
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
<script src="resources/js/table-js/main.js"></script>
<script type="text/javascript">
    function validaBusca() {
        var check = true;
        if (document.getElementById("busca_usuario").value === "") {
            alert("Informe qualquer nome antes de enviar");
            check = false;
        }
        return check;
    }
</script>
</body>
</html>

