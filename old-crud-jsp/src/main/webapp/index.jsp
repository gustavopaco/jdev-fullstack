<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@page isELIgnored="false" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="resources/images/favicon.ico"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="resources/css/register-css/mainreg.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="resources/css/login-css/main.css">
    <link rel="stylesheet" type="text/css" href="resources/css/login-css/util.css">
    <!--===============================================================================================-->
</head>
<body>
<c:out value = "${'<tag> , &'}"/>
<c:out value = "${'Ola Mundo'}"/>
<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100">
            <div class="login100-pic js-tilt" data-tilt>
                <img src="resources/images/img-01.png" alt="pet">
            </div>
            <form action="login" method="post" class="login100-form validate-form">
					<span class="login100-form-title">
						Member Login
					</span>
                <div class="wrap-input100 validate-input" data-validate="Valid email is required: ex@abc.xyz">
                    <input class="input100" type="text" name="login" id="login" placeholder="Email" value="${pessoa.login}">
                    <span class="focus-input100"></span>
                    <span class="symbol-input100">
							<i class="fa fa-envelope" aria-hidden="true"></i>
						</span>
                </div>
                <div class="wrap-input100 validate-input" data-validate="Password is required">
                    <input class="input100" type="password" name="password" id="password" placeholder="Password" value="${pessoa.password}">
                    <span class="focus-input100"></span>
                    <span class="symbol-input100">
							<i class="fa fa-lock" aria-hidden="true"></i>
						</span>
                </div>
                    <h6 style="color: red; margin-left: 20px;">${msg1}</h6>
                <div class="container-login100-form-btn">
                    <button class="login100-form-btn" type="submit">
                        Login
                    </button>
                </div>
                <div class="text-center p-t-12">
						<span class="txt1">
							Forgot
						</span>
                    <a class="txt2" href="#">
                        Username / Password?
                    </a>
                </div>
                <div class="text-center p-t-136">
                    <a class="txt2" href="cadastroActivity.jsp">
                        Create your Account
                        <i class="fa fa-long-arrow-right m-l-5" aria-hidden="true"></i>
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

<!--===============================================================================================-->
<script src="resources/vendor/login-vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="resources/vendor/login-vendor/bootstrap/js/popper.js"></script>
<script src="resources/vendor/login-vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="resources/vendor/login-vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<script src="resources/vendor/login-vendor/tilt/tilt.jquery.min.js"></script>
<script >
    $('.js-tilt').tilt({
        scale: 1.1
    })
</script>
<!--===============================================================================================-->
<script src="resources/js/login-js/main.js"></script>
</body>
</html>
