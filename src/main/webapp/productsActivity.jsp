<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 5/25/2021
  Time: 10:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
                <form method="post">
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Product Name</label>
                                <input class="input--style-4" type="text" name="product_name">
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Quantity</label>
                                <input class="input--style-4" type="text" name="product_name">
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Price</label>
                                <input class="input--style-4" type="text" name="product_name">
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
        <div class="card-body" >
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
                    <tbody >
                        <tr scope="row" style="text-align: center;">
                            <td>Tom</td>
                            <td>2</td>
                            <td>1</td>
                            <td><a><img src="resources/images/deleteimg.png" title="Delete" class="imagesize"></a></td>
                            <td><a><img src="https://img.icons8.com/dusk/64/000000/edit--v2.png" title="Edit" class="imagesize"></a></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


</body>
</html>
