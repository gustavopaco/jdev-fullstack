package controller;

import dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Usuario;

import java.io.IOException;

@WebServlet (urlPatterns = "/search")
public class BuscaUsuarioServletController extends HttpServlet {
    UsuarioDAO usuarioDAO;
    Usuario usuario;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action){
           case "searchUser" -> doSearch(req, resp);
        }
    }


    protected void doSearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String busca_usuario = req.getParameter("busca_usuario");
        usuarioDAO = new UsuarioDAO();
        req.setAttribute("usuarios",usuarioDAO.listarUsuariosPorNome(busca_usuario));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
        requestDispatcher.forward(req, resp);
    }
}
