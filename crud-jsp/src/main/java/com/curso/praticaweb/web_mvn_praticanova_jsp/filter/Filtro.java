package com.curso.praticaweb.web_mvn_praticanova_jsp.filter;

import com.curso.praticaweb.web_mvn_praticanova_jsp.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebFilter(filterName = "Filter", urlPatterns = "/*")
public class Filtro implements Filter {

    // Inicia os processos ou recursos quando o servidor de aplicacao sobe o projeto
    // Iniciar conexao com o banco
    public void init(FilterConfig config) throws ServletException {
    }

    // Encerra os processos quando o servidor eh parado
    // Encerra conexao com o banco
    public void destroy() {
    }

    // Intercepta as request e response
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String loginURI = "/login";
        boolean isUserLogged = (req.getSession().getAttribute("usuarioSession") != null);
        boolean isLoginRequest = (req.getRequestURI().equals(loginURI));
        boolean isLoginPage = (req.getRequestURI().endsWith(loginURI) || req.getRequestURI().contains("index.jsp"));

        if (isUserLogged && (isLoginRequest || isLoginPage)) {
            request.getRequestDispatcher("principal/home.jsp").forward(request, response);
        } else if (!isUserLogged && !isLoginPage) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?urlEscolhida=" + req.getServletPath());
        } else {
            chain.doFilter(request, response);
        }

        System.out.println("Requisicao interceptada pelo filtro");
    }
}
