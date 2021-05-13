package controller;

import java.io.IOException;

import dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServletController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String login = req.getParameter("login");
		String password = req.getParameter("password");
		
		if(new UsuarioDAO().validaLogin(login, password)) { // Acesso Liberado
			
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
			requestDispatcher.forward(req, resp);
		}else {	// Acesso Negado
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("acessoNegadoActivity.jsp");
			requestDispatcher.forward(req, resp);
		}
		
	}
}
