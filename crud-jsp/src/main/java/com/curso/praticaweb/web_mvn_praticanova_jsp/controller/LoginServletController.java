package com.curso.praticaweb.web_mvn_praticanova_jsp.controller;

import com.curso.praticaweb.web_mvn_praticanova_jsp.dao.UsuarioDAORepository;
import com.curso.praticaweb.web_mvn_praticanova_jsp.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/principal/login"})
public class LoginServletController extends HttpServlet {
    private UsuarioDAORepository usuarioDAORepository = new UsuarioDAORepository();

    @Override // Recebe os dados pela url em parametros.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("logout")){
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath());
        }else{
        doPost(request, response);
        }
    }

    @Override // Recebe os dados enviados de um formulario html
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String urlEscolhida = request.getParameter("urlEscolhida");

        if ((email != null && !email.isEmpty()) && (password != null && !password.isEmpty())) {

            Usuario usuario = new Usuario();

            usuario.setUser_email(email);
            usuario.setUser_password(password);

            if (usuarioDAORepository.isUserExist(usuario)) {
                usuario = usuarioDAORepository.getUsuario(usuario.getUser_email());
                request.getSession().setAttribute("usuarioSession", usuario);
                if (urlEscolhida.isEmpty()) {
//                    request.getRequestDispatcher("principal/home.jsp").forward(request, response);
                    response.sendRedirect(request.getContextPath() + "/principal/home.jsp");
                } else {
//                    request.getRequestDispatcher(urlEscolhida).forward(request,response);
                    response.sendRedirect(request.getContextPath() + urlEscolhida);
                }
            } else {
                request.setAttribute("email", usuario.getUser_email());
                request.setAttribute("msg1", "Login ou password invalidos");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("msg1", "Login ou password nao informado");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
