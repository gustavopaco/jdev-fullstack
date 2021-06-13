<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 6/7/2021
  Time: 1:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Colorlib Templates">
    <meta name="author" content="Colorlib">
    <meta name="keywords" content="Colorlib Templates">

    <link rel="icon" type="image/png" href="resources/images/favicon.ico"/>
    <!-- Font special for pages-->
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
    <!-- Icons font CSS-->
    <link href="resources/vendor/register-vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet"
          media="all">
    <link href="resources/vendor/register-vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet"
          media="all">
    <link rel="stylesheet" href="resources/fonts/icomoon/style.css">
    <!-- Vendor CSS-->
    <link href="resources/vendor/register-vendor/select2/select2.min.css" rel="stylesheet" media="all">
    <link href="resources/vendor/register-vendor/datepicker/daterangepicker.css" rel="stylesheet" media="all">
    <!-- Main CSS-->
    <link rel="stylesheet" href="resources/css/table-css/owl.carousel.min.css">
    <link rel="stylesheet" href="resources/css/tabelaresponsiva.css">
    <link rel="stylesheet" href="resources/css/estilo.css">
    <link href="resources/css/register-css/mainreg.css" rel="stylesheet" media="all">
</head>
<body class="bg-gra-02">
<div style="display: flex;justify-content: flex-end;margin-top: 30px; margin-right: 25px;">
    <form action="login" method="post">
        <input type="hidden" name="login" id="sessionLogin" value="${sessionScope.usuarioSession.login}">
        <input type="hidden" name="password" id="sessionPassword" value="${sessionScope.usuarioSession.password}">
        <button type="submit"
                style="background-color: transparent; border: none; color: white; font-size: 20px; margin-right: 30px;">
            Home
        </button>
    </form>
    <a href="index.jsp">
        <button style="background-color: transparent; border: none; color: white; font-size: 20px;">Logout</button>
    </a>
</div>
<div class="p-t-100 font-poppins" style="position: relative;">
    <div class="wrapper wrapper--w680">
        <div class="card card-4">
            <div class="card-body">
                <h2 class="title">Phone Registration</h2>
                <form action="phone" method="post" autocomplete="off" onsubmit="return validaCamposTelefone();">
                    <input type="hidden" name="action" value="cadastrar">
                    <input type="hidden" name="TOKEN" value="${sessionScope.TOKEN}">
                    <input type="hidden" name="id_telefone" value="${telefone.id_telefone}">
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Phone</label>
                                <input class="input--style-4" type="text" name="phone" id="phone"
                                       value="${telefone.tel_numero}">
                                <h6 style="color: red" id="invalid-phone">${msg1}</h6>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Subject</label>
                                <div class="rs-select2 js-select-simple select--no-search">
                                    <select name="phone_type" id="phone_type">
                                        <option ${telefone.tel_tipo == 'Cell' ? 'selected' : ''}>Cell</option>
                                        <option ${telefone.tel_tipo == 'Home' ? 'selected' : ''}>Home</option>
                                        <option ${telefone.tel_tipo == 'Work' ? 'selected' : ''}>Work</option>
                                    </select>
                                        <div class="select-dropdown"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue" type="submit">Submit</button>
                        </div>
                        <div class="p-t-15">
                            <a href="phone?action=listar">
                                <button class="btn btn--radius-2 btn--blue" type="button">Cancel</button>
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="p-t-100" style="display: block; background-color: transparent; position:relative;">
    <div class="card card-4 wrapper" style="max-width:1140px; min-width: 900px;">
        <div class="card-body">
            <h2 class="title">Phone</h2>
            <div class="tabela-responsiva">
                <table class="table table-striped tabelacustomizada2">
                    <thead>
                    <tr>
                        <th scope="col" style="color: #555;">Phone</th>
                        <th scope="col" style="color: #555;">Type</th>
                        <th scope="col" style="color: #555;">Edit</th>
                        <th scope="col" style="color: #555">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${telefones}" var="telefone">
                        <tr scope="row" style="text-align: center;">
                            <td><c:out value="${telefone.tel_numero}"/></td>
                            <td><c:out value="${telefone.tel_tipo}"/></td>
                            <td><a href="phone?action=delete&id_telefone=${telefone.id_telefone}"  onclick="return confirm('Tem certeza que deseja deletar esse Telefone?');">
                                <button type="button"><img src="resources/images/deleteimg.png" title="Delete"
                                                           class="imagesize"></button>
                            </a></td>
                            <td><a href="phone?action=edit&id_telefone=${telefone.id_telefone}">
                                <button type="button"><img src="https://img.icons8.com/dusk/64/000000/edit--v2.png"
                                                           title="Edit" class="imagesize"></button>
                            </a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Jquery JS-->
<script src="resources/vendor/register-vendor/jquery/jquery.min.js"></script>
<!-- Vendor JS-->
<script src="resources/vendor/register-vendor/select2/select2.min.js"></script>
<script src="resources/vendor/register-vendor/datepicker/moment.min.js"></script>
<script src="resources/vendor/register-vendor/datepicker/daterangepicker.js"></script>
<!-- Main JS-->
<script src="resources/js/register-js/globalreg.js"></script>
<script type="text/javascript">
    document.getElementById('phone').addEventListener('input', function (e) {
        var x = e.target.value.replace(/\D/g, '').match(/(\d{0,2})(\d{0,5})(\d{0,4})/);
        e.target.value = !x[2] ? x[1] : '(' + x[1] + ') ' + x[2] + (x[3] ? '-' + x[3] : '');
    });
</script>
<script type="text/javascript">
    function validaCamposTelefone() {
        var check = true;
        if (document.getElementById("phone").value === ""){
            document.getElementById("invalid-phone").innerHTML = "Inform your phone number.";
            check = false;
        }
        return check;
    }
</script>
</body>
</html>
