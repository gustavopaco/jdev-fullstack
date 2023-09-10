package com.curso.praticaweb.dao;

import com.curso.praticaweb.connection.SingleConnection;
import com.curso.praticaweb.models.Projetos;
import com.curso.praticaweb.models.Series;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjetosDAO {
    private static Connection connection;

    public ProjetosDAO() {
        connection = SingleConnection.getConnection();
    }

    public List<Projetos> getProjetos() {
        ArrayList<Projetos> projetosArrayList = new ArrayList<>();
        try {
            String sql = "select * from projetos";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Projetos projetos = new Projetos();
                ArrayList<Series> seriesArrayList = new ArrayList<>();
                projetos.setId_projeto(resultSet.getLong("id_projeto"));
                projetos.setNome_projeto(resultSet.getString("nome_projeto"));

                String sql2 = "select * from series where projeto_fk = ?";
                PreparedStatement statement = connection.prepareStatement(sql2);
                statement.setLong(1, projetos.getId_projeto());
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    Series series = new Series();
                    series.setId_series(result.getLong("id_series"));
                    series.setNome_series(result.getString("nome_series"));
                    series.setStart_date(result.getString("start_date"));
                    series.setEnd_date(result.getString("end_date"));
                    series.setProjeto_fk(result.getLong("projeto_fk"));

                    seriesArrayList.add(series);
                }
                projetos.setList(seriesArrayList);
                projetosArrayList.add(projetos);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return projetosArrayList;
    }

    public void updateSerie(Series series){
        try {
            String sql = "update series set start_date=?, end_date=? where id_series=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,series.getStart_date());
            preparedStatement.setString(2,series.getEnd_date());
            preparedStatement.setLong(3,series.getId_series());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
