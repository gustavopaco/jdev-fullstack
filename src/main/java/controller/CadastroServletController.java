package controller;

import java.io.IOException;

import dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cadastro")
public class CadastroServletController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();

				try {
					usuarioDAO.cadastrarUsuario(login, password);
					System.out.println("Usuario Cadastrado com Sucesso.");
					RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
					requestDispatcher.forward(req, resp);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					RequestDispatcher requestDispatcher = req.getRequestDispatcher("cadastroActivity.jsp");
					requestDispatcher.forward(req, resp);
				}


		
	}
	
}
