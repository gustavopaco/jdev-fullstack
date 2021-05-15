package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Usuario;
import org.postgresql.core.EncodingPredictor.DecodeResult;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import org.postgresql.util.ServerErrorMessage;

import connection.SingleConnection;

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO() {
       connection = SingleConnection.getConnection();
    }


    public Boolean validaLogin(String login, String password) {

        try {

            String sql = " select login,password from usuario "
                    + " where login = ? "
                    + " and password = ? ";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public void cadastrarUsuario(Usuario usuario) throws Exception {

        try {

            String sql = "insert into usuario (login,password) values (?,?)";
            PreparedStatement insertPreparedStatement = connection.prepareStatement(sql);

            insertPreparedStatement.setString(1, usuario.getLogin());
            insertPreparedStatement.setString(2, usuario.getPassword());

            insertPreparedStatement.executeUpdate();
            connection.commit();

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new Exception("Usuario nao foi registrado: \n" + e.getLocalizedMessage());
        }

    }

    public List<Usuario> listarUsuarios() {

        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        try {

            String sql = "select * from usuario";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getLong("id"));
                usuario.setLogin(resultSet.getString("login"));
                usuario.setPassword(resultSet.getString("password"));

                usuarios.add(usuario);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public void deletarUsuario(Long id){

        try {
            String sql = "delete from usuario where id = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

}
