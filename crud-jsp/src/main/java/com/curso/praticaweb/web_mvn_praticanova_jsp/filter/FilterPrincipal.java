package com.curso.praticaweb.web_mvn_praticanova_jsp.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(filterName = "FilterPrincipal", urlPatterns = "/principal/*")
public class FilterPrincipal implements Filter {

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

        String loginURI = "/login";
        boolean isUserLogged = (req.getSession().getAttribute("usuarioSession") != null);
        boolean isLoginPage = (req.getServletPath().endsWith(loginURI));

        if (!isUserLogged && !isLoginPage) {
            req.setAttribute("urlEscolhida", req.getServletPath());
            req.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
        System.out.println("Requisicao interceptada pelo Filtro Principal");
    }
}
