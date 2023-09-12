package com.curso.praticaweb.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/pages/capturarExcecao")
public class CapturarExcecaoServletController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            String valorParam = req.getParameter("valorParam");
            System.out.println("Valor Informado foi: " + valorParam);
            Integer.parseInt(valorParam);

            resp.setStatus(200); // Codigo 200 = Ok, servlet respondeu o esperado
            resp.getWriter().write("Processado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();

            resp.setStatus(500); // Codigo 500 = Erro interno do Servidor
            resp.getWriter().write("Erro ao processar: " + e.getMessage()); // Adiciona valor ao xhr.responseText
        }
    }
}
