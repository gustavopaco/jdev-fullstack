<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 5/25/2021
  Time: 10:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!--===============================================================================================-->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" type="text/javascript"></script>
    <script src="js/jquery.maskMoney.js" type="text/javascript"></script>
    <!--===============================================================================================-->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="images/favicon.ico"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" href="fonts/icomoon/style.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="css/table-css/owl.carousel.min.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="css/tabelaresponsiva.css">
    <!--===============================================================================================-->
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <!--===============================================================================================-->
    <!-- Icons font CSS-->
    <link href="resources/vendor/register-vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet"
          media="all">
    <link href="resources/vendor/register-vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet"
          media="all">
    <!-- Vendor CSS-->
    <link href="resources/vendor/register-vendor/select2/select2.min.css" rel="stylesheet" media="all">
    <link href="resources/vendor/register-vendor/datepicker/daterangepicker.css" rel="stylesheet" media="all">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="css/estilo.css">
    <!--===============================================================================================-->
    <link href="css/register-css/mainreg.css" rel="stylesheet" media="all">
    <!--===============================================================================================-->
    <title>Title</title>
</head>
<body class="bg-gra-02">
<div style="display: flex;justify-content: flex-end;margin-top: 30px; margin-right: 25px;">
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
<div class="p-t-100 font-poppins" style="position: relative;">
    <div class="wrapper wrapper--w680">
        <div class="card card-4">
            <div class="card-body">
                <h2 class="title">Products registration</h2>
                <form action="produtos" method="post" onsubmit="return validaCampos()">
                    <input type="hidden" name="action" value="cadastrar">
                    <input type="hidden" name="id" value="${produto.id}">
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Product Name</label>
                                <input class="input--style-4" type="text" name="productName" id="productName"
                                       value="${produto.nomeProduto}">
                                <h6 style="color: red">${msg1}</h6>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Quantity</label>
                                <input class="input--style-4" type="text" name="quantity" id="quantity"
                                       value="${produto.quantidade}"
                                       oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*?)\..*/g, '$1');">
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Price</label>
                                <input class="input--style-4" type="text" id="price" name="price"
                                       value="${produto.precoFormatado}">
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Subject</label>
                                <div class="rs-select2 js-select-simple select--no-search">
                                    <select name="categoria_type" id="categoria_type">
                                        <c:forEach items="${categorias}" var="categoria">
                                            <option value="${categoria.id_categoria}" ${categoria.id_categoria == produto.id_categoria ? 'selected' : ''}>${categoria.nome_categoria}</option>
                                        </c:forEach>
                                    </select>
                                    <div class="select-dropdown"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue" type="submit">Save</button>
                        </div>
                        <div class="p-t-15">
                            <a href="produtos?action=listar">
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
    <div class="card card-4 wrapper" style="max-width: 1140px; min-width: 900px;">
        <div class="card-body">
            <h2 class="title">Products</h2>
            <div class="tabela-responsiva">
                <table class="table table-striped tabelacustomizada2" aria-describedby="Dados de produtos">
                    <thead>
                    <tr>
                        <th scope="col" style="color: #555;">Name</th>
                        <th scope="col" style="color: #555;">Quantity</th>
                        <th scope="col" style="color: #555">Price</th>
                        <th scope="col" style="color: #555">Category</th>
                        <th scope="col" style="color: #555">Delete</th>
                        <th scope="col" style="color: #555">Edit</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${produtos}" var="produtos">
                        <tr scope="row" style="text-align: center;">
                            <td><c:out value="${produtos.nomeProduto}"/></td>
                            <td><c:out value="${produtos.quantidade}"/></td>
                            <td><fmt:formatNumber type="currency" maxFractionDigits="2" value="${produtos.preco}"
                                                  currencySymbol="$"/></td>
                            <c:forEach items="${categorias}" var="cat">
                                <c:if test="${produtos.id_categoria == cat.id_categoria}">
                                    <td><c:out value="${cat.nome_categoria}"/></td>
                                </c:if>
                            </c:forEach>
                            <td><a href="produtos?action=delete&id=${produtos.id}"
                                   onclick="return confirm('Tem certeza que deseja deletar esse produto?');">
                                <button type="button"><img src="resources/images/deleteimg.png" title="Delete" alt="delete"
                                                           class="imagesize"></button>
                            </a></td>
                            <td><a href="produtos?action=edit&id=${produtos.id}">
                                <button type="button"><img src="https://img.icons8.com/dusk/64/000000/edit--v2.png" alt="edit"
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
<script type="text/javascript">
    function setFormat(id) {

        if (document.getElementById('price').value !== "") {
            document.getElementById('price').value = parseFloat(document.getElementById('price').value.replace(/\//g, ""))
                .toString()
                .replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        }
    }
</script>
<script type="text/javascript">
    window.onload = function () {
        var s = '${acao}';
        redirect(s);
    }

    function redirect(s) {
        if (s === 'home') {
            url = '?action=listar';
            window.location.href += url;
        }

        if (s === 'delete') {
            var u = window.location.href;
            u = u.substring(0, '?');
            url = '?action=listar';
            window.location.href = u + url;
        }
    }
</script>
<script type="text/javascript">
    $("#price").maskMoney({prefix: '$ ', allowNegative: true, thousands: '.', decimal: ',', affixesStay: false});
</script>
<script type="text/javascript">
    function validaCampos() {
        var imprime = "";
        var check = true;

        if (document.getElementById("productName").value === "") {
            imprime += "Nome do produto\n";
            check = false;
        }
        if (document.getElementById("quantity").value === "") {
            imprime += "Quantidade de produto\n";
            check = false;
        }
        if (document.getElementById("price").value === "") {
            imprime += "Preco\n";
            check = false;
        }

        if (imprime !== "") {
            var notificacao = "Preencha os campos:\n" + imprime;
            alert(notificacao);
            check = false;
        }
        return check;
    }
</script>
<!-- Jquery JS-->
<script src="resources/vendor/register-vendor/jquery/jquery.min.js"></script>
<!-- Vendor JS-->
<script src="resources/vendor/register-vendor/select2/select2.min.js"></script>
<script src="resources/vendor/register-vendor/datepicker/moment.min.js"></script>
<script src="resources/vendor/register-vendor/datepicker/daterangepicker.js"></script>
<!-- Main JS-->
<script src="resources/js/register-js/globalreg.js"></script>
</body>
</html>
