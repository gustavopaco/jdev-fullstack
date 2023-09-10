package com.curso.praticaweb.filter;

import com.curso.praticaweb.connection.SingleConnection;
import com.curso.praticaweb.connection.SingleConnectionBD2;
import com.curso.praticaweb.models.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(filterName = "Filter", urlPatterns = "/pages/*")
public class Filter implements jakarta.servlet.Filter {
    private static Connection connection;
    private static Connection connectionBD2;

    // Executa alguma coisa quando a aplicacao for iniciada
    public void init(FilterConfig config) throws ServletException {
        connection = SingleConnection.getConnection();
        connectionBD2 = SingleConnectionBD2.getConnection();
    }

    // Faz alguma coisa quando a aplicacao eh finalizada
    public void destroy() {
    }

    // Intercepta todas as requisicoes
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;

            String loginURI = req.getContextPath() + "/pages/login";
            boolean isUserLogged = (req.getSession().getAttribute("usuarioSession") != null);
            boolean isLoginRequest = (req.getRequestURI().equals(loginURI));
            boolean isLoginPage = (req.getRequestURI().endsWith("/pages/login"));
            boolean isLogoutRequest = (req.getParameter("action") != null && req.getParameter("action").equals("deslogar"));

            if (isUserLogged && isLoginRequest && !isLogoutRequest){
                req.getRequestDispatcher("homeActivity.jsp").forward(request,response);
            }else if (!isUserLogged && !isLoginPage){
                req.setAttribute("urlEscolhida",req.getServletPath());
                req.getRequestDispatcher("loginActivity.jsp").forward(request,response);
            }else {
                chain.doFilter(request, response);
            }
            System.out.println("Filtro Funcionando");
            connection.commit();
            connectionBD2.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
                connectionBD2.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


// Retorna null caso nao esteja logado, retorna null.
//        Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioSession");
//
//        if (usuario == null && !req.getServletPath().equals("/pages/login")) { // Caso usuario nao esteja logado
//            req.setAttribute("urlEscolhida",req.getServletPath());
//            req.getRequestDispatcher("loginActivity.jsp").forward(request,response);
//            return;
//        }


//  if (isUserLogged && (isLoginRequest || isLoginPage)){
//           req.getRequestDispatcher("homeActivity.jsp?action=gohome").forward(request,response);
//        }else if (!isUserLogged && !isLoginPage){
//            req.setAttribute("urlEscolhida",req.getServletPath());
//            req.getRequestDispatcher("loginActivity.jsp").forward(request,response);
//            return;
//        }else {
//            // Caso usuario esteja logado
//            System.out.println("Interceptando as requisicoes");
//            // Executa as acoes do request e do response
//            chain.doFilter(request, response);
//        }


// Verificar qual pagina esta tentando ser acessada...
//            System.out.println("Context Path do Filtro: " + req.getContextPath());
//            System.out.println("Servlet Path do Filtro: " + req.getServletPath());
//            System.out.println("Request URI do Filtro: " + req.getRequestURI());
//            System.out.println("Request URL do Filtro: " + req.getRequestURL());
//            System.out.println("Eh uma requisicao de Deslogar? " + isLogoutRequest);