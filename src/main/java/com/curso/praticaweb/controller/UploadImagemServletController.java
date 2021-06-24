package com.curso.praticaweb.controller;

import com.curso.praticaweb.dao.UsuarioDAO;
import com.curso.praticaweb.models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@WebServlet(urlPatterns = "/pages/upload")
public class UploadImagemServletController extends HttpServlet {
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "upload": doUpload(request, response);
            break;
            case "load": doLoad(request, response);
            break;
            case "download": doDownload(request, response);
            break;
        }

    }

    private void doDownload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_usuario = request.getParameter("id_usuario");
        usuarioDAO = new UsuarioDAO();
        usuario = usuarioDAO.getUserByID(Long.parseLong(id_usuario));
        String file64;
        String contentType;

        file64 = usuario.getImagem_base64().substring(usuario.getImagem_base64().indexOf(",") + 1);
        contentType = usuario.getImagem_base64().substring(usuario.getImagem_base64().indexOf(":") + 1, usuario.getImagem_base64().indexOf(";"));

        response.setHeader("Content-Disposition", "attachment;filename=arquivo." + contentType.split("/")[1]);

        // CONVERTE A STRING BASE64 DA IMAGEM PARA BYTE[]
        byte[] bytes = Base64.getDecoder().decode(file64);

        // COLOCANDO OS BYTES EM UM OBJETO DE ENTRADA PARA PROCESSAMENTO
//        InputStream inputStream = new ByteArrayInputStream(bytes);
        response.getOutputStream().write(bytes);

        // INICIO DA RESPOSTA PARA O NAVEGADOR
//        inputStream.transferTo(response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();

    }

    protected void doUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Usar variavel uploadFile para salvar no banco de dados
        String uploadFile = request.getParameter("imagemEnviada");
        if (uploadFile != null && !uploadFile.isEmpty()) {

            usuario = new Usuario();
            usuarioDAO = new UsuarioDAO();

            usuario = (Usuario) request.getSession().getAttribute("usuarioSession");

            String tipoFile = uploadFile.substring(uploadFile.indexOf(":") + 1, uploadFile.indexOf(";"));
            String nomeFile;

            // Nesse momento faz o insert no banco de dados...
            usuario.setImagem_base64(uploadFile);

            usuarioDAO.updateUsuario(usuario);

            response.setStatus(200);
            response.getWriter().write("Upload realizado com sucesso.");
        } else {
            response.setStatus(500);
            response.getWriter().write("Erro ao realizar upload do arquivo");
        }
    }

    protected void doLoad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        usuario = new Usuario();
        usuarioDAO = new UsuarioDAO();

        request.setAttribute("usuarios", usuarioDAO.getUsuarios());
        request.getRequestDispatcher("uploadActivity.jsp").forward(request, response);
    }
}
