package controller;

import dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/deletarUser")
public class DeleteServletController extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDelete(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long valueID = (long)(Integer.parseInt(req.getParameter("id")));

        usuarioDAO = new UsuarioDAO();
        usuarioDAO.deletarUsuario(valueID);
        req.setAttribute("usuarios",usuarioDAO.listarUsuarios());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
        requestDispatcher.forward(req,resp);
    }
}
