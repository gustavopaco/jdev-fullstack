package servlet;

import bean.BeanUsuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println(request.getParameter("email"));
		System.out.println(request.getParameter("senha"));
		
		BeanUsuario beanUsuario = new BeanUsuario();

			/* ACESSO LIBERADO */
		if (beanUsuario.validarLoginSenha(request.getParameter("email"), request.getParameter("senha"))) {
		
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("acessoliberado.jsp");
			requestDispatcher.forward(request, response);
			
		}else {		
			
			/* ACESSO NEGADO */
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("acessonegado.jsp");
			requestDispatcher.forward(request, response);
		}
		
		
	}
	

}
