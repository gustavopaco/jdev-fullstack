<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Colorlib Templates">
    <meta name="author" content="Colorlib">
    <meta name="keywords" content="Colorlib Templates">
    <title>SIGN Up</title>

    <link rel="icon" type="image/png" href="images/favicon.ico"/>
    <!-- Font special for pages-->
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <!-- Adicionando JQuery CEP Correios-->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
            crossorigin="anonymous"></script>
    <!-- Icons font CSS-->
    <link href="resources/vendor/register-vendor/mdi-font/css/material-design-iconic-font.min.css" rel="stylesheet" media="all">
    <link href="resources/vendor/register-vendor/font-awesome-4.7/css/font-awesome.min.css" rel="stylesheet" media="all">
    <!-- Vendor CSS-->
    <link href="resources/vendor/register-vendor/select2/select2.min.css" rel="stylesheet" media="all">
    <link href="resources/vendor/register-vendor/datepicker/daterangepicker.css" rel="stylesheet" media="all">
    <!-- Main CSS-->
    <link href="resources/css/register-css/mainreg.css" rel="stylesheet" media="all">
</head>
<body>
<div class="page-wrapper bg-gra-02 p-t-130 p-b-100 font-poppins">
    <div class="wrapper wrapper--w680">
        <div class="card card-4">
            <div class="card-body">
                <h2 class="title">Sign Up</h2>
                <form action="cadastroCtl" method="post" onsubmit="return validaCampos()">
                    <input type="hidden" id="action" name="action" value="register"/>
                    <input type="hidden" id="ibge" name="ibge">
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">first name</label>
                                <input class="input--style-4" type="text" name="first_name" id="first_name"
                                       value="${user.name.substring(0,user.name.indexOf(" "))}">
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">last name</label>
                                <input class="input--style-4" type="text" name="last_name" id="last_name"
                                       value="${user.name.substring(user.name.indexOf(" ")+1)}">
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Birthday</label>
                                <div class="input-group-icon">
                                    <input class="input--style-4 js-datepicker" type="text" name="birthday"
                                           id="birthday"
                                           value="${user.birthday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}">
                                    <i class="zmdi zmdi-calendar-note input-icon js-btn-calendar"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Gender</label>
                                <div class="p-t-10">
                                    <label class="radio-container m-r-45">Male
                                        <input type="radio" name="gender" id="genderM" value="1">
                                        <span class="checkmark"></span>
                                    </label>
                                    <label class="radio-container">Female
                                        <input type="radio" name="gender" id="genderF" value="2">
                                        <span class="checkmark"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">CPF</label>
                                <input class="input--style-4" type="text" name="cpf" id="cpf" value="${user.cpf}">
                                <h6 style="color: red">${msg2}</h6>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Zip-Code</label>
                                <input type="text" class="input--style-4" name="zipcode" id="zipcode" value="${endereco.end_cep}"
                                       onblur="searchZipCode()">
                                <h6 style="color: red" id="invalid-zip-code">${msg3}</h6>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Phone</label>
                                <input class="input--style-4" type="text" name="phone" id="phone" value="${telefone.tel_numero}">
                                <h6 style="color: red">${msg4}</h6>
                            </div>
                        </div>
                        <div class="input-group">
                            <label class="label" for="phone_type">Subject</label>
                            <div class="rs-select2 select2-container">
                                <select class="rs-select2 input--style-4" name="phone_type" id="phone_type">
                                    <option ${telefone.tel_tipo == 'Cell' ? 'selected' : ''}>Cell</option>
                                    <option ${telefone.tel_tipo == 'Home' ? 'selected' : ''}>Home</option>
                                    <option ${telefone.tel_tipo == 'Work' ? 'selected' : ''}>Work</option>
                                </select>
                                <div class="select-dropdown"></div>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Address</label>
                                <input type="text" class="input--style-4" name="address" id="address" value="${endereco.end_rua}"
                                       style="width: 400px;" readonly>
                            </div>
                        </div>
                        <div>
                            <div class="input-group">
                                <label class="label">Number</label>
                                <input type="text" class="input--style-4" name="number" id="number" maxlength="4" value="${endereco.end_numero}"
                                       style="width: 100px;">
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Address 2</label>
                                <input type="text" class="input--style-4" style="width: 400px;" name="address2" value="${endereco.end_complemento}"
                                       id="address2" placeholder="Apt, Unit, Block">
                            </div>
                        </div>
                        <div>
                            <div class="input-group">
                                <label class="label">State</label>
                                <input type="text" class="input--style-4" name="state" id="state" style="width: 100px;" readonly value="${endereco.end_estado}">
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
                                <input type="text" class="input--style-4" name="city" id="city" readonly value="${endereco.end_cidade}">
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div>
                            <div class="input-group">
                                <label class="label">Email</label>
                                <input class="input--style-4" type="email" name="login" id="login"
                                       value="${user.login}" style="width: 400px;">
                                <h6 style="color: red">${msg1}</h6>
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Password</label>
                                <input class="input--style-4" type="password" name="password" id="password">
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Confirm Password</label>
                                <input class="input--style-4" type="password" name="password2" id="password2">
                            </div>
                        </div>
                    </div>
                    <div class="p-t-15">
                        <button class="btn btn--radius-2 btn--blue" type="submit">Submit
                        </button>
                    </div>
                </form>
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
<script src="js/register-js/globalreg.js"></script>
<script type="text/javascript">
    window.onload = function () {
        sexo()
    }

    function sexo() {
        var sexo = '${user.gender}';
        if (sexo === 'Masculino') {
            document.getElementById("genderM").checked = true;
        } else if (sexo === 'Feminino') {
            document.getElementById("genderF").checked = true;
        }
        ;
    }
</script>
<script type="text/javascript">
    document.getElementById('phone').addEventListener('input', function (e) {
        var x = e.target.value.replace(/\D/g, '').match(/(\d{0,2})(\d{0,5})(\d{0,4})/);
        e.target.value = !x[2] ? x[1] : '(' + x[1] + ') ' + x[2] + (x[3] ? '-' + x[3] : '');
    });
</script>
<script type="text/javascript">
    document.getElementById('cpf').addEventListener('input', function (e) {
        var x = e.target.value.replace(/\D/g, '').match(/(\d{0,3})(\d{0,3})(\d{0,3})(\d{0,2})/);
        e.target.value = !x[2] ? x[1] : x[1] + '.' + (!x[3] ? x[2] : x[2] + '.') + (!x[4] ? x[3] : x[3] + '-') + (x[4] ? x[4] : '');
    });
</script>
<script type="text/javascript">
    function validaCampos() {
        var validacao = true;
        var imprime = "";

        if (document.getElementById("first_name").value === "") {
            imprime += "First Name\n";
            validacao = false;
        }
        if (document.getElementById("last_name").value === "") {
            imprime += "Last Name\n";
            validacao = false;
        }
        if (document.getElementById("birthday").value === "") {
            imprime += "Pick up a date\n";
            validacao = false;
        }
        if ((document.getElementById("genderM").checked === false) && (document.getElementById("genderF").checked === false)) {
            imprime += "Gender\n";
            validacao = false;
        }
        if (document.getElementById("cpf").value === "") {
            imprime += "CPF\n";
            validacao = false;
        }
        if (document.getElementById("login").value === "") {
            imprime += "Email\n";
            validacao = false;
        }
        if (document.getElementById("phone").value === "") {
            imprime += "Phone\n";
            validacao = false;
        }
        if (document.getElementById("zipcode").value === "") {
            imprime += "Zip-Code\n";
            validacao = false;
        }
        if(document.getElementById("number").value === ""){
            imprime += "Number\n";
            validacao = false;
        }
        if (document.getElementById("password").value === "") {
            imprime += "Password\n";
            validacao = false;
        }
        if (document.getElementById("password2").value === "") {
            imprime += "Confirm Password\n";
            validacao = false;
        }
        if (document.getElementById("password").value !== document.getElementById("password2").value) {
            imprime += "Password and Confirm Password must match!";
            validacao = false;
        }
        if (imprime !== "") {
            var notificacao = "Please, fix fields:\n" + imprime;
            alert(notificacao);
            validacao = false;
        }
        return validacao;
    }
</script>
<script type="text/javascript">
    function limpa_formulario_cep() {
        // Limpa valores do formulário de cep.
        $("#address").val("");
        $("#neighborhood").val("");
        $("#city").val("");
        $("#state").val("");
        $("#ibge").val("");
    }

    function searchZipCode() {
        var zipcode = $("#zipcode").val().replace(/\D/g, '');

        //Verifica se campo cep possui valor informado.
        if (zipcode !== "") {

            //Expressão regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            if (validacep.test(zipcode)) {

                //Preenche os campos com "..." enquanto consulta webservice.
                $("#address").val("...");
                $("#neighborhood").val("...");
                $("#city").val("...");
                $("#state").val("...");
                $("#ibge").val("...");
                document.getElementById("invalid-zip-code").innerHTML = "";

                //Consulta o webservice viacep.com.br/
                $.getJSON("https://viacep.com.br/ws/" + zipcode + "/json/?callback=?", function (dados) {
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
                        alert("CEP não encontrado.");
                    }
                });
            } //end if.
            else {
                //cep é inválido.
                limpa_formulario_cep();
                document.getElementById("invalid-zip-code").innerHTML = "Invalid Zip-Code.";
            }
        } //end if.
        else {
            //cep sem valor, limpa formulário.
            limpa_formulario_cep();
        }
    }
</script>
</body>
</html>
