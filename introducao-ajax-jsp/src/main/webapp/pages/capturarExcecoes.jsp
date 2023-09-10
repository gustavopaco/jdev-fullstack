<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 6/15/2021
  Time: 7:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>Title</title>
</head>
<body>
    <h1>Capturar Excecoes com JQuery</h1>
    <input type="text" value="test" id="txt_valor">
    <button onclick="testarExcecao()">Testar Excecao</button>

<script>
    function testarExcecao() {
        var valorInformado = $("#txt_valor").val();

        $.ajax({
            method: "POST", // Method de envio GET ou POST
            url: "capturarExcecao?action=capturar", // Para qual Servlet?
            data: { valorParam : valorInformado} // {"Variavel" : "Valor"} -> ValorParam, sera a variavel enviada ao Servlet
        }).done(function (response) { // Resposta Ok - Nenhum Erro
            alert("Sucesso: " + response);
            // Fazer algo...
        }).fail(function (xhr,status,error) { // Resposta Erro - Algum erro ocorreu
            alert("Mensagem de erro: " + xhr.responseText + "\nResponseXMl: "
                + xhr.responseXML + "\nCodigo de Status: " + xhr.status
                + "\nTexto do Status: " + xhr.statusText
                + "\nTexto do Status2: " + status); // xhr.responseText - mostra resposta do Servidor
            // Fazer algo se der errado...
        });
    }
</script>
</body>
</html>
