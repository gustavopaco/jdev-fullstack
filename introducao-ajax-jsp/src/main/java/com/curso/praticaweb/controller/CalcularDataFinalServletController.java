package com.curso.praticaweb.controller;

import com.curso.praticaweb.dao.DataFinalDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@WebServlet(urlPatterns = "/pages/calcularDataFinal")
public class CalcularDataFinalServletController extends HttpServlet {
    DataFinalDAO dataFinalDAO = new DataFinalDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 08:00 as 12:00 | 13:30 as 17:30
        *  1 Dia = 8 horas */

        String data = request.getParameter("data");
        String tempo = request.getParameter("tempo");

        int diaEmHoras = 8;
        int dias = 0;

        if (Integer.parseInt(tempo) >= diaEmHoras) {
            dias = (int) Math.ceil(Double.parseDouble(tempo) / diaEmHoras);
        }

        LocalDate localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        localDate = localDate.plusDays(dias);
        String dataFinal = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dataFinalDAO.insertDataFinal(localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        request.setAttribute("diasTotais",dias);
        request.setAttribute("dataFinal", dataFinal);
        request.getRequestDispatcher("datas.jsp").forward(request,response);

    }
}
