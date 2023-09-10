<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 6/23/2021
  Time: 9:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../resources/css/ganttview/jquery-ui-1.8.4.css"/>
    <link rel="stylesheet" type="text/css" href="../resources/css/ganttview/reset.css"/>
    <link rel="stylesheet" type="text/css" href="../resources/css/ganttview/jquery.ganttView.css"/>
    <title>Gantt View</title>
    <style type="text/css">
        body {
            font-family: tahoma, verdana, helvetica;
            font-size: 0.8em;
            padding: 10px;
        }
    </style>
</head>
<body>
<div id="ganttChart"></div>
<br/><br/>
<div id="eventMessage"></div>

<script type="text/javascript" src="../resources/js/ganttview/jquery-1.4.2.js"></script>
<script type="text/javascript" src="../resources/js/ganttview/date.js"></script>
<script type="text/javascript" src="../resources/js/ganttview/jquery-ui-1.8.4.js"></script>
<script type="text/javascript" src="../resources/js/ganttview/jquery.ganttView.js"></script>
<%--<script type="text/javascript" src="../resources/js/ganttview/data.js"></script>--%>
<script type="text/javascript">

    $(document).ready(function () {
        $.get("buscarDatasPlanejamento", function (response) {
            var ganttData = JSON.parse(response);

            var tempData = "";
            tempData += "[";

            $.each(ganttData, function (indexI,projeto) {

                tempData += "{ \"id\": \"" + projeto.id_projeto + "\", \"name\": \"" + projeto.nome_projeto + "\", \"series\": [";

                $.each(projeto.list, function (indexJ,serie) {

                    var cores = "#3366FF,#00CC00".split(',');
                    var cor;

                    if (indexJ === 0){
                        cor = "#CC33CC";
                    } else {
                        cor = Number.isInteger(indexJ / 2) ? cores[0] : cores [1];
                    }

                    var dataInicial = serie.start_date.split('-');
                    var dataFinal = serie.end_date.split('-');

                    tempData += "{ \"name\": \"" + serie.nome_series + "\", \"start\": \"" + new Date(dataInicial[0],(dataInicial[1]-1),dataInicial[2]) + "\", \"end\": \"" + new Date(dataFinal[0],(dataFinal[1]-1),dataFinal[2]) + "\", \"color\": \"" + cor + "\", \"projeto_fk\": \"" + serie.projeto_fk + "\", \"id_series\": \"" + serie.id_series + "\" }";

                    if (indexJ < projeto.list.length - 1){
                        tempData += ",";
                    }
                }); // Fim FOR Series...

                tempData += "]}"
                if (indexI < ganttData.length -1) {
                    tempData += ",";
                }

            }); // Fim FOR Projetos...

            tempData += "]";
            tempData = JSON.parse(tempData);

            // Processa dados do GanttView

            $("#ganttChart").ganttView({
                data: tempData,
                slideWidth: 1600,
                behavior: {
                    onClick: function (data) {
                        var msg = "You clicked on an event: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
                        $("#eventMessage").text(msg);
                    },
                    onResize: function (data) {
                        var msg = "You resized an event: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
                        $.post( "buscarDatasPlanejamento", { id_series: data.id_series, dataStart: data.start.toString("yyyy-MM-dd"), dataEnd: data.end.toString("yyyy-MM-dd"), projeto_fk: data.projeto_fk})

                        $("#eventMessage").text(msg);
                    },
                    onDrag: function (data) {
                        var msg = "You dragged an event: { start: " + data.start.toString("M/d/yyyy") + ", end: " + data.end.toString("M/d/yyyy") + " }";
                        $.post( "buscarDatasPlanejamento", { id_series: data.id_series, dataStart: data.start.toString("yyyy-MM-dd"), dataEnd: data.end.toString("yyyy-MM-dd"), projeto_fk: data.projeto_fk})

                        $("#eventMessage").text(msg);
                    }
                }
            });
            // $("#ganttChart").ganttView("setSlideWidth", 600);
        });
    });
</script>
</body>
</html>
