package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.Usuario;

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

            String sql = "insert into usuario (login, password, name, birthday, gender, cpf, phone) values (?,?,?,?,?,?,?)";
            updateDadosUsuario(usuario, sql);

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new Exception("Usuario nao foi registrado: \n" + e.getLocalizedMessage());
        }

    }

    private void updateDadosUsuario(Usuario usuario, String sql) throws SQLException {
        PreparedStatement insertPreparedStatement = connection.prepareStatement(sql);

        insertPreparedStatement.setString(1, usuario.getLogin());
        insertPreparedStatement.setString(2, usuario.getPassword());
        insertPreparedStatement.setString(3,usuario.getName());
        insertPreparedStatement.setObject(4,usuario.getBirthday());
        insertPreparedStatement.setString(5,usuario.getGender());
        insertPreparedStatement.setString(6,usuario.getCpf());
        insertPreparedStatement.setString(7,usuario.getPhone());

        insertPreparedStatement.executeUpdate();
        connection.commit();
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
                getDadosUsuarios(usuario, resultSet);

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

    public Usuario pesquisarUsuario(Long id){
        Usuario usuario = new Usuario();
        try {
            String sql = "select * from usuario where id = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                usuario.setId(id);
                getDadosUsuarios(usuario, resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
            return usuario;
    }

    private void getDadosUsuarios(Usuario usuario, ResultSet resultSet) throws SQLException {
        usuario.setLogin(resultSet.getString("login"));
        usuario.setPassword(resultSet.getString("password"));
        usuario.setName(resultSet.getString("name"));
        usuario.setBirthday(resultSet.getObject("birthday", LocalDate.class));
        usuario.setGender(resultSet.getString("gender"));
        usuario.setCpf(resultSet.getString("cpf"));
        usuario.setPhone(resultSet.getString("phone"));
    }

    public boolean updateUsuario(Usuario usuario){
        try {
            String sql = " update usuario set login = ?, password = ?, name = ?, birthday = ?, gender = ?, " +
                    " cpf = ?, phone = ? where id = " + usuario.getId();
            updateDadosUsuario(usuario, sql);
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

    public boolean isCPFValid(String cpf){
        try {
            String sql = "Select cpf from usuario where cpf = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,cpf);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public boolean isLoginValid(String login){
        try {
            String sql = "select login from usuario where login ilike(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
