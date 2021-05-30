<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" isELIgnored="false" %>
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

    <link rel="icon" type="image/png" href="resources/images/icons/favicon.ico"/>
    <!-- Font special for pages-->
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">
    <!-- Main CSS-->
    <link href="resources/css/mainreg.css" rel="stylesheet" media="all">
</head>
<body>
<div class="page-wrapper bg-gra-02 p-t-130 p-b-100 font-poppins">
    <div class="wrapper wrapper--w680">
        <div class="card card-4">
            <div class="card-body">
                <h2 class="title">Sign Up</h2>
                <form action="cadastroCtl" method="post" onsubmit="return validaCampos()">
                    <input type="hidden" id="action" name="action" value="register"/>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">first name</label>
                                <input class="input--style-4" type="text" name="first_name" id="first_name" value="${user.name.substring(0,user.name.indexOf(" "))}">
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">last name</label>
                                <input class="input--style-4" type="text" name="last_name" id="last_name" value="${user.name.substring(user.name.indexOf(" ")+1)}">
                            </div>
                        </div>
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Birthday</label>
                                <div class="input-group-icon">
                                    <input class="input--style-4 js-datepicker" type="text" name="birthday" id="birthday" value="${user.birthday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}">
                                    <i class="zmdi zmdi-calendar-note input-icon js-btn-calendar"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Gender</label>
                                <div class="p-t-10">
                                    <label class="radio-container m-r-45">Male
                                        <input type="radio" checked="checked" name="gender" id="genderM" value="1">
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
                    </div>
                    <div class="row row-space">
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Email</label>
                                <input class="input--style-4" type="email" name="login" id="login" value="${user.login}">
                                <h6 style="color: red">${msg1}</h6>
                            </div>
                        </div>
                        <div class="col-2">
                            <div class="input-group">
                                <label class="label">Phone Number</label>
                                <input class="input--style-4" type="text" name="phone" id="phone" value="${user.phone}">
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
                        <button class="btn btn--radius-2 btn--blue" type="submit" onclick="saveSession()">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Jquery JS-->
<script src="resources/vendor/colorlib-regform-4/jquery/jquery.min.js"></script>
<!-- Vendor JS-->
<script src="resources/vendor/colorlib-regform-4/select2/select2.min.js"></script>
<script src="resources/vendor/colorlib-regform-4/datepicker/moment.min.js"></script>
<script src="resources/vendor/colorlib-regform-4/datepicker/daterangepicker.js"></script>
<!-- Main JS-->
<script src="resources/js/globalreg.js"></script>
<script type="text/javascript">
    window.onload = function (){
       sexo()
    }

    function sexo() {
        var sexo = '${user.gender}';
        if(sexo === 'Masculino'){
           document.getElementById("genderM").checked = true;
        }else if(sexo === 'Feminino'){
            document.getElementById("genderF").checked = true;
        };
    }
</script>
<script type="text/javascript">
    document.getElementById('phone').addEventListener('input', function (e) {
        var x = e.target.value.replace(/\D/g, '').match(/(\d{0,2})(\d{0,5})(\d{0,4})/);
        e.target.value = !x[2] ? x[1] : '(' + x[1] + ') ' + x[2] + (x[3] ? '-' + x[3] : '');
    });
</script>
<script type="text/javascript">
    document.getElementById('cpf').addEventListener('input',function (e){
        var x = e.target.value.replace(/\D/g, '').match(/(\d{0,3})(\d{0,3})(\d{0,3})(\d{0,2})/);
        e.target.value = !x[2] ? x[1] : x[1] + '.' + (!x[3] ? x[2] : x[2] + '.') + (!x[4] ? x[3] :  x[3] + '-' ) + (x[4] ? x[4] : '');
    });
</script>
<script type="text/javascript">
    function validaCampos() {
        var validacao = true;
        var imprime = "";

        if(document.getElementById("first_name").value === ""){
            imprime += "First Name\n";
            validacao = false;
        }
        if(document.getElementById("last_name").value === ""){
            imprime += "Last Name\n";
            validacao = false;
        }
        if(document.getElementById("birthday").value === ""){
            imprime += "Pick up a date\n";
            validacao = false;
        }
        if(document.getElementById("cpf").value === ""){
            imprime += "CPF\n";
            validacao = false;
        }
        if(document.getElementById("login").value === ""){
            imprime += "Email\n";
            validacao = false;
        }
        if(document.getElementById("phone").value === ""){
            imprime += "Phone\n";
            validacao = false;
        }
        if(document.getElementById("password").value === ""){
            imprime += "Password\n";
            validacao = false;
        }
        if (document.getElementById("password2").value === ""){
            imprime += "Confirm Password\n";
            validacao = false;
        }
        if(document.getElementById("password").value !== document.getElementById("password2").value){
            imprime += "Password and Confirm Password must match!";
            validacao = false;
        }
        if(imprime !== ""){
            var notificacao = "Please, fix fields:\n" + imprime;
            alert(notificacao);
            validacao = false;
        }
        return validacao;
    }
</script>
<script type="text/javascript">
    function saveSession() {
        sessionStorage.setItem("login",document.getElementById("login").value);
        sessionStorage.setItem("password",document.getElementById("password").value);
    }
</script>
</body>
</html>