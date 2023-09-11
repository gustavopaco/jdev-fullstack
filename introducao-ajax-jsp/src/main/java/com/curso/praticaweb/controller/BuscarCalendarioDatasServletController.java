package com.curso.praticaweb.controller;

import com.curso.praticaweb.dao.EventosDAO;
import com.curso.praticaweb.models.Eventos;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/pages/buscarCalendarioDatas")
public class BuscarCalendarioDatasServletController extends HttpServlet {
    EventosDAO eventosDAO = new EventosDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String datas = "";

        List<Eventos> eventos = eventosDAO.getEventos();

        if (!eventos.isEmpty()){
            int index = 1;
            datas += "[";
            for (Eventos evento: eventos) {
                datas += "{ \"title\": \"" +evento.getTitle() + "\", \"start\": \"" + evento.getDatastart() + "\" }";
                if (index < eventos.size()){
                    datas += ",";
                }
                index++;
            }
        datas += "]";
       response.setStatus(200);
       response.getWriter().write(datas);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("La vamos nos 2");
    }
}
