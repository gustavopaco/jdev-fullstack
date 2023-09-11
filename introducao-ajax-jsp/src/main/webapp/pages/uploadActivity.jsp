<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gusta
  Date: 6/17/2021
  Time: 11:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>Upload Files</title>
</head>
<body>
<h1 style="text-align: center">Upload</h1>
<input type="file" id="uploadFile" name="uploadFile" onchange="carregarImagem()">
<br>
<img src="" alt="Imagem" title="Imagem" id="target" width="200" height="200" style="margin: 20px;">

<br>
<a href="" id="hserver"><button onclick="userImage()">Carregar Tabela</button></a>
<br>
<br>
<img src="" alt="Imagem do Usuario" id="perfilImage" width="100" height="100">
<br><br><br>
<table>
    <thead style="margin-left: 20%">
        <th>ID</th>
        <th>Login</th>
        <th>Image</th>
    </thead>
    <c:forEach items="${usuarios}" var="user">
        <tr>
            <td>${user.id_usuario}</td>
            <td>${user.login}</td>
            <c:choose>
                <c:when test="${(user.imagem_base64 != null || !user.imagem_base64.isEmpty())}">
            <td><a href="upload?action=download&id_usuario=${user.id_usuario}"><img src="${user.imagem_base64}" alt="" width="50px" height="50px"></a></td>
                </c:when>
                <c:otherwise>
                    <td><img src="${pageContext.request.contextPath}/resources/images/emptyF.png" alt="" width="50px" height="50px"></td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
</table>

<script>
    function carregarImagem() {
        var fileUploaded = document.querySelector("input[type=file]").files[0];
        var imagem = document.querySelector("img");

        var reader = new FileReader();
        reader.onloadend = function () {
            imagem.src = reader.result;

            // =======Ajax aqui=======
            $.ajax({
                method: "POST",
                url: "upload?action=upload",
                data: { imagemEnviada : imagem.src}
            }).done(function (response) {
                alert("Sucesso: " + response)
            }).fail(function (xhr,status,errorThrown) {
                alert(xhr.responseText)
            });
        }

        if (fileUploaded) {
            reader.readAsDataURL(fileUploaded);
        } else {
            imagem.src = "";
        }


    };

    function userImage() {
        document.getElementById("hserver").href = 'upload?action=load';
    }

    $(function () {
        document.getElementById("perfilImage").src = '${sessionScope.usuarioSession.imagem_base64}';
    })

</script>
</body>
</html>
