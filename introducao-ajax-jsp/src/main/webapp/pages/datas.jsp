<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 6/23/2021
  Time: 6:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <title>Calculando Datas</title>
</head>
<body>
<form action="calcularDataFinal" method="post">
    <label>Data Inicial</label>
    <input type="text" id="data" name="data">

    <label>Tempo em Horas</label>
    <input type="text" id="tempo" name="tempo">

    <button type="submit">Calcular</button>
</form>
<br><br>
<label>Data Final</label>
<input type="text" id="dataFinal" name="dataFinal" readonly value="${dataFinal}">
<label>Total de Dias</label>
<input type="text" id="diasTotais" name="diasTotais" readonly value="${diasTotais}">

<script type="text/javascript">
    $(function () {
        $("#data").datepicker({
            dateFormat: 'dd/mm/yy'});
    });
</script>
</body>
</html>
