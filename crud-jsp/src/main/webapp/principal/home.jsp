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
                                    <div class="row">
                                        <!-- task, page, download counter  start -->
                                        <h1>Conteudo da Pagina Base para o sistema</h1>
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

<jsp:include page="include/javascriptfile.jsp"/>
</body>
</html>
