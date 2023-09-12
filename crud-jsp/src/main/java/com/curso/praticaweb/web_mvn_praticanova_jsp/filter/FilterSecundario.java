package com.curso.praticaweb.web_mvn_praticanova_jsp.filter;

import com.curso.praticaweb.web_mvn_praticanova_jsp.connection.SingleConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(filterName = "FilterSecundario", urlPatterns = "/*")
public class FilterSecundario implements Filter {
    private static Connection connection;

    public void init(FilterConfig config) throws ServletException {
        connection = SingleConnection.getConnection();
    }

    public void destroy() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;

            String loginURI = "/login";
            String loginURI3 = "index.jsp";
            String action = request.getParameter("action");
            boolean isUserLogged = (req.getSession().getAttribute("usuarioSession") != null);
            boolean isLoginPage = (req.getRequestURI().contains(loginURI) || req.getRequestURI().contains(loginURI3) || req.getRequestURI().equals(req.getContextPath() + "/"));
            boolean isLogoutRequest = (action != null && action.equals("logout"));

            if (isUserLogged && isLoginPage && !isLogoutRequest) {
                resp.sendRedirect(req.getContextPath() + "/principal/home.jsp");
                return;
            }

            chain.doFilter(request, response);

            connection.commit();
            System.out.println("Filtro Secundario interceptando...");

        } catch (Exception throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.setAttribute("errormsg1", throwables.getMessage());
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }
}
