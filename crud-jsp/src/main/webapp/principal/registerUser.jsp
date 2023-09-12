<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 6/25/2021
  Time: 8:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<jsp:include page="include/head.jsp"/>

<body>
<!-- Pre-loader start -->
<jsp:include page="include/theme-loader.jsp"/>
<!-- Pre-loader end -->
<div id="pcoded" class="pcoded">
    <div class="pcoded-overlay-box"></div>
    <div class="pcoded-container navbar-wrapper">
        <jsp:include page="include/navbar.jsp"/>

        <div class="pcoded-main-container">
            <div class="pcoded-wrapper">
                <jsp:include page="include/navbar-main-menu.jsp"/>
                <div class="pcoded-content">
                    <!-- Page-header start -->
                    <jsp:include page="include/page-header.jsp"/>
                    <!-- Page-header end -->
                    <div class="pcoded-inner-content">
                        <!-- Main-body start -->
                        <div class="main-body">
                            <div class="page-wrapper">
                                <!-- Page-body start -->
                                <div class="page-body">
                                    <!-- task, page, download counter  start -->
                                    <!-- Basic Form Inputs card start -->
                                    <div class="row">
                                        <div class="col-md-12" style="max-width: 800px; margin: 0 auto">
                                            <div class="card">
                                                <div class="card-header">
                                                    <h3 class="st-main-title">Register User Form</h3>
                                                    <h6 class="text-success"
                                                        id="regsucesso">${sessionScope.cadastrosucesso}</h6>
                                                    <h6 class="text-danger"
                                                        id="regerror">${sessionScope.cadastroerror}</h6>
                                                </div>
                                                <div class="card-block">
                                                    <form action="${pageContext.request.contextPath}/registerUser"
                                                          method="post" class="form-material" id="regUser">
                                                        <!-- onsubmit="return checkMail()"--->
                                                        <input type="hidden" id="id_usuario" name="id_usuario" value="${sessionScope.usuarioEscolhido.id_usuario}">
                                                        <div class="form-group form-default">
                                                            <input type="text" name="user_name" class="form-control"
                                                                   required="required" id="user_name" value="${sessionScope.usuarioEscolhido.user_name}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Name</label>
                                                        </div>
                                                        <div class="form-group form-default">
                                                            <input type="email" name="user_email" class="form-control"
                                                                   required="required" id="user_email"
                                                                   autocomplete="off" value="${sessionScope.usuarioEscolhido.user_email}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Email (exa@gmail.com)</label>
                                                        </div>
                                                        <div class="form-group form-default">
                                                            <input type="password" name="user_password"
                                                                   class="form-control"
                                                                   required="required" id="user_password"
                                                                   autocomplete="off">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Password</label>
                                                        </div>
                                                        <button class="btn btn-primary waves-effect waves-light" type="submit">Save</button>
                                                        <button class="btn btn-success waves-effect waves-light" type="button" onclick="limparCampos()">Limpar</button>
                                                        <button class="btn btn-info waves-effect waves-light" type="button" onclick="confirm('Deseja realmente excluir o usuario?') ? deleteUser() : ''">Delete</button>
                                                        <button class="btn btn-warning waves-effect waves-light" data-toggle="modal" data-target="#UsuarioModal">Pesquisar Usuario</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--  project and team member end -->
                            </div>
                        </div>
                        <!-- Page-body end -->
                    </div>
                    <div id="styleSelector"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
<!-- Modal -->
<div class="modal fade" id="UsuarioModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="include/javascriptfile.jsp"/>
<script type="text/javascript">
    var globalUser = '${sessionScope.usuarioEscolhido.id_usuario}';
    $(document).ready(function() {
        <%
        request.getSession().removeAttribute("cadastroerror");
        request.getSession().removeAttribute("cadastrosucesso");
        %>
    });

    $(window).bind('hashchange',function () {
        <%request.getSession().removeAttribute("usuarioEscolhido");%>
    })

    function deleteUser() {
        $.ajax({
            url: "${pageContext.request.contextPath}/registerUser?userID=" + globalUser,
            type: "DELETE"
        }).done(function (response) {
            $("#regsucesso").html(response);
            limparCampos();
        }).fail(function (xhr,textStatus) {
            $("#regerror").html(xhr.responseText);
        })
    }

    function limparCampos() {
        var elementos = document.getElementById("regUser").elements;

        for (let i = 0; i < elementos.length; i++) {
            elementos[i].value = "";
        }
    }

    function RemoverEstaFuncaoParaValidarEmailDiretoDaTela() {

        var mailValidado;
        $("#user_email").blur(function () {
            $.ajax({
                method: "POST",
                url: "${pageContext.request.contextPath}/registerUser",
                data: {action: "validaEmail", user_email: $("#user_email").val()}
            }).done(function (response) {
                $("#user_email").removeClass("form-control-danger");
                $("#user_email").addClass("form-control-success");
                $("#regerror").html("");
                $("#regsucesso").html("Usuario Valido");
                mailValidado = true;
            }).fail(function (xhr, status, errorThrown) {
                $("#user_email").removeClass("form-control-success");
                $("#user_email").addClass("form-control-danger");
                $("#regsucesso").html("");
                $("#regerror").html("Usuario Invalido.");
                mailValidado = false;
            });
        });

        function checkMail() {
            return mailValidado;
        }
    }
</script>
</body>
</html>
