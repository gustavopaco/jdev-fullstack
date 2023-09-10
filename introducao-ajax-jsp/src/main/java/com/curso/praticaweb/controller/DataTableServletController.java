package com.curso.praticaweb.controller;

import com.curso.praticaweb.dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/pages/carregarDataTable")
public class DataTableServletController extends HttpServlet {
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("usuarios",usuarioDAO.getUsuarios());
        request.getRequestDispatcher("dataTableJquery.jsp").forward(request,response);

//        System.out.println("Servlet Chamado");
//        String pathToFile = "";
//        try {
//            String path = ("F:\\Java\\JavaProjects\\web-mvn-ajax-jsp\\src\\main\\java\\com\\curso\\praticaweb\\JSON\\dados.json");
//            String conteudo = Files.readString(Paths.get(path));
//            System.out.println(conteudo);
//
//            response.setStatus(200);
//            response.getWriter().write(conteudo);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setStatus(500);
//            response.getWriter().write("Error");
//        }
    }
}
