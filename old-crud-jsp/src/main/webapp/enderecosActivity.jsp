<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 6/4/2021
  Time: 7:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <!-- Adicionando JQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
            crossorigin="anonymous"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!--===============================================================================================-->

    <!--===============================================================================================-->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" type="text/javascript"></script>
    <script src="resources/js/jquery.maskMoney.js" type="text/javascript"></script>
    <!--===============================================================================================-->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="resources/images/favicon.ico"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/fonts/icomoon/style.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/table-css/owl.carousel.min.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="resources/css/tabelaresponsiva.css">
    <!--===============================================================================================-->
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/estilo.css">
    <!--===============================================================================================-->
    <link href="resources/css/register-css/mainreg.css" rel="stylesheet" media="all">
    <!--===============================================================================================-->
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
                <h2 class="title">Address Registration</h2>
                <form action="address" method="post" onsubmit="return validaEnderecos()" autocomplete="off">
                    <input type="hidden" name="action" value="cadastrar">
                    <input type="hidden" name="TOKEN" value="${sessionScope.TOKEN}">
                    <input type="hidden" name="id_address" value="${endereco.id_endereco}">
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label>Zip-Code</label>
                                <input type="text" class="input--style-4" name="zipcode" id="zipcode" maxlength="8" value="${endereco.end_cep}"
                                       onblur="preencheCEP()" oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*?)\..*/g, '$1');">
                                <h6 style="color: red" id="invalid-zip-code">${msg1}</h6>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Address</label>
                                <input type="text" class="input--style-4" name="address" id="address" value="${endereco.end_rua}" readonly style="width: 400px;" autocomplete="">
                            </div>
                        </div>
                        <div>
                            <div class="input-group">
                                <label class="label">Number</label>
                                <input type="text" class="input--style-4" name="number" id="number" maxlength="4" value="${endereco.end_numero}"
                                       style="width: 100px;">
                                <h6 style="color: red" id="invalid-number"></h6>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Address 2</label>
                                <input type="text" class="input--style-4" style="width: 400px;" name="address2" value="${endereco.end_complemento}"
                                       id="address2">
                            </div>
                        </div>
                        <div>
                            <div class="input-group">
                                <label class="label">State</label>
                                <input type="text" class="input--style-4" name="state" id="state" style="width: 100px;" value="${endereco.end_estado}"
                                      readonly>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Neighborhood</label>
                                <input type="text" class="input--style-4" name="neighborhood" id="neighborhood" value="${endereco.end_bairro}"
                                      readonly>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">City</label>
                                <input type="text" class="input--style-4" name="city" id="city" value="${endereco.end_cidade}" readonly>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="p-t-15">
                            <button class="btn btn--radius-2 btn--blue" type="submit">Submit</button>
                        </div>
                        <div class="p-t-15">
                            <a href="address?action=listar"><button class="btn btn--radius-2 btn--blue" type="button" onclick="">Cancel</button></a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="p-t-100" style="display: block; background-color: transparent; position:relative;">
    <div class="card card-4 wrapper" style="max-width: 1440px; min-width: 1140px;">
        <div class="card-body">
            <h2 class="title">Address</h2>
            <div class="tabela-responsiva">
                <table class="table table-striped tabelacustomizada2" aria-describedby="tabela-enderecos" style="min-width: 100%;">
                    <thead>
                    <tr>
                        <th scope="col" style="color: #555;">Zip-Code</th>
                        <th scope="col" style="color: #555;">Address</th>
                        <th scope="col" style="color: #555">Number</th>
                        <th scope="col" style="color: #555;">Address 2</th>
                        <th scope="col" style="color: #555">Neighborhood</th>
                        <th scope="col" style="color: #555;">City</th>
                        <th scope="col" style="color: #555">State</th>
                        <th scope="col" style="color: #555;">Edit</th>
                        <th scope="col" style="color: #555">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${enderecos}" var="endereco">
                        <tr scope="row" style="text-align: center;">
                            <td><c:out value="${endereco.end_cep}"/></td>
                            <td><c:out value="${endereco.end_rua}"/></td>
                            <td><c:out value="${endereco.end_numero}"/></td>
                            <td><c:out value="${endereco.end_complemento}"/></td>
                            <td><c:out value="${endereco.end_bairro}"/></td>
                            <td><c:out value="${endereco.end_cidade}"/></td>
                            <td><c:out value="${endereco.end_estado}"/></td>
                            <td><a href="address?action=delete&id_endereco=${endereco.id_endereco}"  onclick="return confirm('Tem certeza que deseja deletar esse Endereco?');">
                                <button type="button"><img src="resources/images/deleteimg.png" title="Delete" alt="delete"
                                                           class="imagesize"></button>
                            </a></td>
                            <td><a href="address?action=edit&id_endereco=${endereco.id_endereco}">
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
<script>
        function limpa_formulario_cep() {
            // Limpa valores do formulário de cep.
            $("#address").val("");
            $("#neighborhood").val("");
            $("#city").val("");
            $("#state").val("");
        }

        //Quando o campo cep perde o foco.
        function preencheCEP() {
            //Nova variável "cep" somente com dígitos.
            var cep = $("#zipcode").val().replace(/\D/g, '');

            //Verifica se campo cep possui valor informado.
            if (cep !== "") {

                //Expressão regular para validar o CEP.
                var validacep = /^[0-9]{8}$/;

                //Valida o formato do CEP.
                if (validacep.test(cep)) {

                    //Preenche os campos com "..." enquanto consulta webservice.
                    $("#address").val("...");
                    $("#neighborhood").val("...");
                    $("#city").val("...");
                    $("#state").val("...");

                    //Consulta o webservice viacep.com.br/
                    $.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?", function (dados) {
                        document.getElementById("invalid-zip-code").innerHTML = "";
                        if (!("erro" in dados)) {
                            //Atualiza os campos com os valores da consulta.
                            $("#address").val(dados.logradouro);
                            $("#neighborhood").val(dados.bairro);
                            $("#city").val(dados.localidade);
                            $("#state").val(dados.uf);
                            $("#ibge").val(dados.ibge);
                        } //end if.
                        else {
                            //CEP pesquisado não foi encontrado.
                            limpa_formulario_cep();
                            document.getElementById("invalid-zip-code").innerHTML = "Zip not found";
                        }
                    });
                } //end if.
                else {
                    //cep é inválido.
                    limpa_formulario_cep();
                    document.getElementById("invalid-zip-code").innerHTML = "Zip invalid";
                }
            } //end if.
            else {
                //cep sem valor, limpa formulário.
                limpa_formulario_cep();
            }
        }
</script>
<script type="text/javascript">
    function validaEnderecos() {
        var check = true;
        var zip = (document.getElementById("zipcode").value).replace("-", "");
        if (zip === "" || zip.length !== 8) {
            document.getElementById("invalid-zip-code").innerHTML = "Zip invalid";
            check = false;
        }
        if (document.getElementById("number").value === "") {
            document.getElementById("invalid-number").innerHTML = "Invalid";
            check = false;
        }
        if (document.getElementById("address").value === "") {
            check = false;
        }
        if (document.getElementById("state").value === "") {
            check = false;
        }
        if (document.getElementById("neighborhood").value === "") {
            check = false;
        }
        if (document.getElementById("city").value === "") {
            check = false;
        }
        if (check === false) {
            alert("Favor preencher os campos em branco");
        }
        return check;
    }
</script>
<script type="text/javascript">
</script>
</body>
</html>
