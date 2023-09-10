<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 6/17/2021
  Time: 3:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--Inicio dos scripts de Barra de Progresso por Jquery--%>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <%--Fim dos scripts de Barra de Progresso por Jquery--%>
    <style type="text/css">
        #myProgress { /* Funcao de fundo da barra de progresso*/
            width: 100%;
            background-color: #ddd; /*Cor de fundo da barra de progresso.*/
        }

        #myBar { /*Funcao de barra progresso*/
            width: 0.5%;
            height: 30px;
            background-color: #00FF00; /*Cor de carregamento da barra de progresso*/
        }

        .ui-progressbar {
            position: relative;
        }

        .progress-label {
            position: relative;
            left: 50%;
            top: 4%;
            font-weight: bold;
            text-shadow: 1px 1px 0 #fff;
        }

        .ui-progressbar-value {
            background-color: #00FF00;
        }
    </style>
    <title>Barra de progresso</title>
</head>

<body>
<h1 style="margin-top: 50px;">Barra de carregamento com JavaScript</h1>
<button type="button" onclick="startProgressBar()"
        style="display: block; margin: 50px auto;">Carregar
    Barra
</button>

<div id="myProgress">
    <div id="myBar"></div>
</div>

<h1 style="margin-top: 50px;">Barra de carregamento com Jquery</h1>
<div id="progressbar">
    <div class="progress-label">
        Carregando...
    </div>
</div>

<%--Script barra de Progresso por Javascript--%>
<script>
    var contador = 0;

    function startProgressBar() {
        var elemento = document.getElementById("myBar");
        var width = 1;

        if (contador === 0) {
            var id = setInterval(frame, 100);
        }

        function frame() {
            if (width >= 100) {
                clearInterval(id);
                contador = 0;
            } else {
                width++;
                elemento.style.width = width + "%";
                contador = 1;
            }
        }
    }
</script>
<%--Script barra de Progresso por Jquery--%>
<script>
    $(function () {
        var barrinhaDeProgresso = $("#progressbar"), textoDaBarrinha = $(".progress-label");

        barrinhaDeProgresso.progressbar({ // Cria a barra no DIV
            value: false,
            change: function () {
                textoDaBarrinha.text(barrinhaDeProgresso.progressbar('value') + "%");
            }, complete: function () {
                textoDaBarrinha.text('Completo!');
            }
        });

        function progressFrame() {
            var val = barrinhaDeProgresso.progressbar("value") || 0;
            barrinhaDeProgresso.progressbar("value", val + 2);
            if (val < 99) {
                setTimeout(progressFrame, 80);
            }
        }

        setTimeout(progressFrame, 2000); // chamado ao abrir a tela, escrevendo a barra de progresso.
    })
</script>
</body>
</html>
