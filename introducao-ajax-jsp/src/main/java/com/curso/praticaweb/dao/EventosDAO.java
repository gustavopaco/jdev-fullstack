package com.curso.praticaweb.dao;

import com.curso.praticaweb.connection.SingleConnection;
import com.curso.praticaweb.models.Eventos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventosDAO {
    private static Connection connection;

    public EventosDAO() {
        connection = SingleConnection.getConnection();
    }

    public List<Eventos> getEventos() {
        ArrayList<Eventos> eventos = new ArrayList<>();
        try {
            String sql = "select * from eventos";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Eventos evento = new Eventos();
                evento.setId_evento(resultSet.getLong("id_evento"));
                evento.setTitle(resultSet.getString("title"));
                evento.setDatastart(resultSet.getString("datastart"));
                evento.setDataend(resultSet.getString("dataend"));
                evento.setUrl(resultSet.getString("url"));
                evento.setGroupId(resultSet.getInt("groupId"));
                eventos.add(evento);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return eventos;
    }
}
