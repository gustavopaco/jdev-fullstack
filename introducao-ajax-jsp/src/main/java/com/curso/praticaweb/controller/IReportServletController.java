package com.curso.praticaweb.controller;

import com.curso.praticaweb.dao.UsuarioDAO;
import com.curso.praticaweb.models.Usuario;
import com.curso.praticaweb.service.RelatorioGeralService;
import com.curso.praticaweb.service.RelatorioUsuarioService;
import com.lowagie.text.pdf.codec.Base64;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = "/pages/ireport")
public class IReportServletController extends HttpServlet {
//    RelatorioUsuarioService relatorioService = new RelatorioUsuarioService();
    UsuarioDAO usuarioDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "gerarRelatorio": doRelatorio(request, response);
            break;
        }
    }

    private void doRelatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipoRelatorio = request.getParameter("tipoExportar");
        usuarioDAO = new UsuarioDAO();

        try {
            List<Usuario> usuarios = usuarioDAO.getUsuarios();

            String fileURL = RelatorioGeralService.gerarRelatorio(usuarios,new HashMap(),"rel_usuario",request.getServletContext(),tipoRelatorio);

            //============================METODO GERANDO ARQUIVO TEMPORARIO NA PASTA RELATORIOS=====================================
            // Construir o caminho completo e absoluto do arquivo.
            File downloadFile = new File(fileURL);
            FileInputStream fileInputStream = new FileInputStream(downloadFile);

            // Obter o tipo MIME do arquivo
            String mimeType = request.getServletContext().getMimeType(fileURL);
            if (mimeType == null) {
                // Define como tipo binario se mapeamento mime nao for encontrado
                mimeType = "application/octet-stream";
            }

            // Definir os atributos para resposta
            response.setContentType(mimeType);
            response.setContentLengthLong(downloadFile.length());

            // Definir cabecalho para a resposta
            String headerKey = "Content-Disposition";
//            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
//            String headerValue = "attachment; filename= " + downloadFile.getName();
            String headerValue = "inline; filename= " + downloadFile.getName();

            response.setHeader(headerKey, headerValue);

            // Escrever bytes lidos a partir do fluxo de entrada no fluxo de saida
            response.getOutputStream().write(fileInputStream.readAllBytes());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //========================METODO GERANDO SOMENTE PDF===================================================
//            byte[] fileURL = RelatorioUsuarioService.gerarRelatorio(usuarios, new HashMap(), "rel_usuario", "rel_usuario", request.getServletContext());
//            InputStream inputStream = new ByteArrayInputStream(fileURL);
//
//            response.setHeader("Content-Disposition", "attachment;filename=relatorio.pdf");
//            response.getOutputStream().write(inputStream.readAllBytes());
//            response.getOutputStream().flush();
//            response.getOutputStream().close();
    }
}
