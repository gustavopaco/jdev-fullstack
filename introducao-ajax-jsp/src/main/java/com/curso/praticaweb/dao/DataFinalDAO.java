package com.curso.praticaweb.dao;

import com.curso.praticaweb.connection.SingleConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataFinalDAO {
    private static Connection connection;

    public DataFinalDAO() {
        connection = SingleConnection.getConnection();
    }

    public void insertDataFinal(String date){
        try {
            String sql = "insert into datafinal_projetos (datafinal) values (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, date);
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
