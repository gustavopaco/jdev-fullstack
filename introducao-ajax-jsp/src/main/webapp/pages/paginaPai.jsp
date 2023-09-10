<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 6/17/2021
  Time: 3:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>Title</title>
</head>
<body>
  <h1>Pagina Pai load JQuery</h1>
  <button type="button" onclick="carregar()">Carregar Pagina</button>
  <div id="mostrarPaginaFilha"> <!-- Local de carregamento da Pagina Filha -->

  </div>

<script>
  function carregar() {
    $("#mostrarPaginaFilha").load('paginaFilha.jsp'); // Pagina pagina em JQuery
  }

  function enviarDadosBackEnd() {

    $.ajax({ //Funcao Ajax
      method: "POST", // Metodo de envio para back-end
      url: "pages/qualquer", // Pagina enviada
      data: {variavelEnviada : variavelDaTela} //Variavel Enviada ao back-end ; Variavel da Tela atual
    }).done(function () { // Em caso de sucesso code = 200

    }).fail(function () { // Em caso de falha code = 404 ou 500

    })
  }
</script>
</body>
</html>
