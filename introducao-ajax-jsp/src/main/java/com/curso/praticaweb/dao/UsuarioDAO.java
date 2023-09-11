package com.curso.praticaweb.dao;

import com.curso.praticaweb.connection.SingleConnection;
import com.curso.praticaweb.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static Connection connection;

    public UsuarioDAO() {
        connection = SingleConnection.getConnection();
    }

    public void insertUsuario(Usuario usuario){
        try {
            String sql = "insert into usuario (login=?,password=?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,usuario.getLogin());
            preparedStatement.setString(2,usuario.getPassword());
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

    public void updateUsuario(Usuario usuario){
        try {
            String sql = "update usuario set login=?, password=?, imagem_base64 =? where id_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,usuario.getLogin());
            preparedStatement.setString(2,usuario.getPassword());
            preparedStatement.setString(3,usuario.getImagem_base64());
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
    }

    public boolean isLoginPasswordOk(Usuario usuario){
        try {
            String sql = "select * from usuario where login = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,usuario.getLogin());
            preparedStatement.setString(2,usuario.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Usuario getUserLogged(String login){
        Usuario usuario = new Usuario();
        try {
            String sql = "select * from usuario where login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                usuario.setId_usuario(resultSet.getLong("id_usuario"));
                usuario.setLogin(resultSet.getString("login"));
                usuario.setPassword(resultSet.getString("password"));
                usuario.setImagem_base64(resultSet.getString("imagem_base64"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  usuario;
    }

    public List<Usuario> getUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        try {
            String sql = "select * from usuario";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setId_usuario(resultSet.getLong("id_usuario"));
                usuario.setLogin(resultSet.getString("login"));
                usuario.setPassword(resultSet.getString("password"));
                usuario.setImagem_base64(resultSet.getString("imagem_base64"));
                usuarios.add(usuario);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuarios;
    }

    public Usuario getUserByID(Long id){
        Usuario usuario = new Usuario();
        try {
            String sql = "select * from usuario where id_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                usuario.setId_usuario(resultSet.getLong("id_usuario"));
                usuario.setLogin(resultSet.getString("login"));
                usuario.setPassword(resultSet.getString("password"));
                usuario.setImagem_base64(resultSet.getString("imagem_base64"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  usuario;
    }
}
