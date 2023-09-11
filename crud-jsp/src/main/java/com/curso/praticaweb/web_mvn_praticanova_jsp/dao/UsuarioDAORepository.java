package com.curso.praticaweb.web_mvn_praticanova_jsp.dao;

import com.curso.praticaweb.web_mvn_praticanova_jsp.connection.SingleConnection;
import com.curso.praticaweb.web_mvn_praticanova_jsp.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAORepository {
    private static Connection connection;

    public UsuarioDAORepository() {
        connection = SingleConnection.getConnection();
    }

    public boolean isUserExist(Usuario usuario) {
        try {
            String sql = "select * from usuario where user_email ilike(?) and user_password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,usuario.getUser_email());
            preparedStatement.setString(2,usuario.getUser_password());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Usuario getUsuario(String email){
        Usuario usuario = new Usuario();
        try {
            String sql = "select * from usuario where user_email ilike(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                usuario.setId_usuario(resultSet.getLong("id_usuario"));
                usuario.setUser_email(resultSet.getString("user_email"));
                usuario.setUser_password(resultSet.getString("user_password"));
                usuario.setUser_name(resultSet.getString("user_name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  usuario;
    }

    public boolean insertUser(Usuario usuario) {
        try {
            String sql = "insert into usuario (user_email, user_password, user_name) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,usuario.getUser_email());
            preparedStatement.setString(2,usuario.getUser_password());
            preparedStatement.setString(3,usuario.getUser_name());
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isEmailOnUpdateAlreadyTaken(Usuario usuario){
        try {
            String sql = "select * from usuario where user_email ilike(?) and id_usuario <> ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getUser_email());
            preparedStatement.setLong(2,usuario.getId_usuario());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(Usuario usuario){
        try {
            String sql = "update usuario set user_email = ?, user_password = ?, user_name = ? where id_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,usuario.getUser_email());
            preparedStatement.setString(2, usuario.getUser_password());
            preparedStatement.setString(3,usuario.getUser_name());
            preparedStatement.setLong(4,usuario.getId_usuario());
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
        return false;
    }

    public boolean deleteUser(Long id_usuario) {
        try {
            String sql = "delete from usuario where id_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id_usuario);
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
