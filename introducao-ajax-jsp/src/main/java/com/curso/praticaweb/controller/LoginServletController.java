package com.curso.praticaweb.controller;

import com.curso.praticaweb.dao.UsuarioDAO;
import com.curso.praticaweb.models.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "login", urlPatterns = "/pages/login")
public class LoginServletController extends HttpServlet {
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch ((action != null) ? action : "goLogin") {
            case "logar":
                doLogin(request, response);
                break;
            case "deslogar":
                request.getSession().removeAttribute("usuarioSession");
                response.sendRedirect("/mvn-ajax-jsp/index.jsp");
                break;
            case "goLogin":
                request.getRequestDispatcher("loginActivity.jsp").forward(request, response);
                break;
        }
    }

    protected void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String urlEscolhida = request.getParameter("urlEscolhida");

        usuario = new Usuario();
        usuarioDAO = new UsuarioDAO();

        usuario.setLogin(login);
        usuario.setPassword(password);

        // Neste momento pode ser feito uma validacao no banco de dados
        if (usuarioDAO.isLoginPasswordOk(usuario)) { // Se o Login foi bem sucedido
            usuario = usuarioDAO.getUserLogged(usuario.getLogin());
            request.getSession().setAttribute("usuarioSession", usuario);
            if (!urlEscolhida.isEmpty() && (!urlEscolhida.equals("/pages/login"))) {
                response.sendRedirect(request.getContextPath() + urlEscolhida);
            } else {
                response.sendRedirect("homeActivity.jsp");
            }
        } else { // Se o Login falho...
            request.setAttribute("msg1", "Login ou Password incorretos");
            request.setAttribute("login", login);
            request.setAttribute("password", password);
            request.setAttribute("urlEscolhida", urlEscolhida);
            System.out.println("Dentro do Login Falhou" + request.getContextPath());
            System.out.println("Dentro do Login Falhou" + request.getServletPath());
            request.getRequestDispatcher("loginActivity.jsp").forward(request, response);
        }
    }
}
