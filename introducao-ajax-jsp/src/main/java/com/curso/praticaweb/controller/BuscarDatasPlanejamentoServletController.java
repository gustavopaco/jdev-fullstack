package com.curso.praticaweb.controller;

import com.curso.praticaweb.dao.ProjetosDAO;
import com.curso.praticaweb.models.Projetos;
import com.curso.praticaweb.models.Series;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(urlPatterns = "/pages/buscarDatasPlanejamento")
public class BuscarDatasPlanejamentoServletController extends HttpServlet {
    private ProjetosDAO projetosDAO = new ProjetosDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Projetos> list = projetosDAO.getProjetos();
        int indexprojeto = 1;
        int indexserie = 1;

        if (!list.isEmpty()) {
            String gSON = new Gson().toJson(list);
            System.out.println(gSON);
            response.setStatus(200);
            response.getWriter().write(gSON);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_series = request.getParameter("id_series");
        String dataStart = request.getParameter("dataStart");
        String dataEnd = request.getParameter("dataEnd");
        String projeto_fk = request.getParameter("projeto_fk");

        try {
        Series series = new Series();

        series.setId_series(Long.parseLong(id_series));
        series.setStart_date(dataStart);
        series.setEnd_date(dataEnd);
        series.setProjeto_fk(Long.parseLong(projeto_fk));

        projetosDAO.updateSerie(series);

        response.setStatus(200);
        response.getWriter().write("Dados gravados com sucesso");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}




//        String dadosJSON = "[";
//            for (Projetos projeto : list) {
//                dadosJSON += "{ \"id\": " + projeto.getId_projeto() + ", \"name\": " + projeto.getNome_projeto() + ", \"series\": [ ";
//
//                for (Series serie : projeto.getList()) {
//                    LocalDate startDate = LocalDate.parse(serie.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                    LocalDate endDate = LocalDate.parse(serie.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                    dadosJSON += "{ \"name\": " + serie.getNome_series() + ", \"start\": \"new Date(" + startDate.getYear() + "," + startDate.getMonthValue() + "," + startDate.getDayOfMonth() + ")\", \"end\": \"new Date(" + endDate.getYear() + "," + endDate.getMonthValue() + "," + endDate.getDayOfMonth() + ")\" } ";
//                    if (indexserie < projeto.getList().size()) {
//                        dadosJSON += ",";
//                    } else {
//                        dadosJSON += "]}";
//                    }
//                    indexserie++;
//                }
//                if (indexprojeto < list.size()) {
//                    dadosJSON += ",";
//                } else {
//                    dadosJSON += "]";
//                }
//            }