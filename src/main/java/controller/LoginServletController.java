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


@WebServlet("/login")
public class LoginServletController extends HttpServlet {
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
		usuarioDAO = new UsuarioDAO();

		if(usuarioDAO.validaLogin(login, password)) { // Acesso Liberado
			usuario = usuarioDAO.pesquisarUsuarioByLogin(login);
			req.getSession().setAttribute("usuarioSession",usuario);
			req.setAttribute("usuarios",usuarioDAO.listarUsuarios());
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
			requestDispatcher.forward(req, resp);
		}else {	// Acesso Negado
			req.setAttribute("msg1","Login ou Senha incorretos.");
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
			requestDispatcher.forward(req, resp);
		}
		
	}
}
