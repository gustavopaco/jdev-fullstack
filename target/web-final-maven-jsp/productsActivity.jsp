<%@ page import="java.text.DecimalFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 5/25/2021
  Time: 10:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!--===============================================================================================-->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" type="text/javascript"></script>
    <script src="resources/js/jquery.maskMoney.js" type="text/javascript"></script>
    <!--===============================================================================================-->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="resources/images/icons/favicon.ico"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/fonts/css-table-16/icomoon/style.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/css-table-16/owl.carousel.min.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="resources/css/tabelaresponsiva.css">
    <!--===============================================================================================-->
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/estilo.css">
    <!--===============================================================================================-->
    <link href="resources/css/mainreg.css" rel="stylesheet" media="all">
    <!--===============================================================================================-->
    <title>Title</title>
</head>
<body class="bg-gra-02">
<div class="p-t-100 font-poppins" style="position: relative;">
    <div class="wrapper wrapper--w680">
        <div class="card card-4">
            <div class="card-body">
                <h2 class="title">Products registration</h2>
                <form action="produtos" method="post">
                    <input type="hidden" name="action" value="cadastrar">
                    <input type="hidden" name="id" value="${produto.id}">
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Product Name</label>
                                <input class="input--style-4" type="text" name="productName" value="${produto.nomeProduto}">
                                <h6 style="color: red">${msg1}</h6>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Quantity</label>
                                <input class="input--style-4" type="text" name="quantity" value="${produto.quantidade}">
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Price</label>
                                <input class="input--style-4" type="text" id="price" name="price" value="${produto.preco}">
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue" type="submit">Save</button>
                        </div>
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue" type="button">Cancel</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="p-t-100" style="display: block;background-color: transparent; position:relative;">
    <div class="card card-4 wrapper" style="max-width: 1140px; min-width: 900px;">
        <div class="card-body">
            <h2 class="title">Products</h2>
            <div class="tabela-responsiva">
                <table class="table table-striped tabelacustomizada2">
                    <thead>
                    <tr>
                        <th scope="col" style="color: #555;">Name</th>
                        <th scope="col" style="color: #555;">Quantity</th>
                        <th scope="col" style="color: #555">Price</th>
                        <th scope="col" style="color: #555">Delete</th>
                        <th scope="col" style="color: #555">Edit</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${produtos}" var="produtos">
                        <tr scope="row" style="text-align: center;">
                            <td><c:out value="${produtos.nomeProduto}"/></td>
                            <td><c:out value="${produtos.quantidade}"/></td>
                            <td><c:out value="$${produtos.preco}"/></td>
                            <td><a href="produtos?action=delete&id=${produtos.id}"><button type="button" ><img src="resources/images/deleteimg.png" title="Delete" class="imagesize"></button></a></td>
                            <td><a href="produtos?action=edit&id=${produtos.id}"><button type="button"><img src="https://img.icons8.com/dusk/64/000000/edit--v2.png" title="Edit" class="imagesize"></button></a></td>
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
        redirect(s)
    }

    function redirect(s) {
        if (s === 'home') {
            url = '?action=listar';
            window.location.href += url;
        }

        if(s === 'delete') {
            var u = window.location.href;
             u = u.substring(0,'?');
            url = '?action=listar';
            window.location.href = u + url;
        }
    }
</script>
<script type="text/javascript">
    $("#price").maskMoney({prefix: '$ ', allowNegative: true, thousands: '.', decimal: ',', affixesStay: false});
</script>
</body>
</html>
