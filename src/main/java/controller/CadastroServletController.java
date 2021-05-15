package controller;

import java.io.IOException;

import dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Usuario;

@WebServlet("/cadastroCtl")
public class CadastroServletController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setPassword(password);
        usuarioDAO = new UsuarioDAO();

        if (!login.isEmpty() && !password.isEmpty()) {

            try {
                usuarioDAO.cadastrarUsuario(usuario);
                System.out.println("Usuario Cadastrado com Sucesso.");
                req.setAttribute("usuarios", usuarioDAO.listarUsuarios());
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
                requestDispatcher.forward(req, resp);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("cadastroActivity.jsp");
                requestDispatcher.forward(req, resp);
            }
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("cadastroActivity.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
