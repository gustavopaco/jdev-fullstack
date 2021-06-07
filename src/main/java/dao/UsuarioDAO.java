package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.Endereco;
import models.Telefone;
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

            String sql = "insert into usuario (login, password, name, birthday, gender, cpf) values (?,?,?,?,?,?)";
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
        insertPreparedStatement.setString(3, usuario.getName());
        insertPreparedStatement.setObject(4, usuario.getBirthday());
        insertPreparedStatement.setString(5, usuario.getGender());
        insertPreparedStatement.setString(6, usuario.getCpf());

        insertPreparedStatement.executeUpdate();
        connection.commit();
    }

    public List<Usuario> listarUsuarios() {

        ArrayList<Usuario> usuarios = new ArrayList<>();

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

    public void deletarUsuario(Long id) {

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

    public Usuario pesquisarUsuario(Long id) {
        Usuario usuario = new Usuario();
        try {
            String sql = "select * from usuario where id = " + id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                usuario.setId(id);
                getDadosUsuarios(usuario, resultSet);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuario;
    }

    public Usuario pesquisarUsuarioByLogin(String login) {
        Usuario usuario = new Usuario();
        try {
            String sql = "select * from usuario where login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                usuario.setId(resultSet.getLong("id"));
                usuario.setLogin(login);
                usuario.setPassword(resultSet.getString("password"));
                usuario.setName(resultSet.getString("name"));
                usuario.setBirthday(resultSet.getObject("birthday", LocalDate.class));
                usuario.setGender(resultSet.getString("gender"));
                usuario.setCpf(resultSet.getString("cpf"));
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
    }

    public boolean updateUsuario(Usuario usuario) {
        try {
            String sql = " update usuario set login = ?, password = ?, name = ?, birthday = ?, gender = ?, " +
                    " cpf = ? where id = " + usuario.getId();
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

    public boolean isCPFValid(String cpf) {
        try {
            String sql = "Select cpf from usuario where cpf = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cpf);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public boolean isLoginValid(String login) {
        try {
            String sql = "select login from usuario where login ilike(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    //<<=============================Endereco====================================================>

    public boolean insertEndereco(Endereco endereco) {
        try {
                String sql = " insert into endereco (end_cep, end_rua, end_numero, end_complemento, end_bairro, end_cidade, end_estado, " +
                        " id_usuario) values (?,?,?,?,?,?,?,?) ";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, endereco.getEnd_cep());
                preparedStatement.setString(2, endereco.getEnd_rua());
                preparedStatement.setInt(3, endereco.getEnd_numero());
                preparedStatement.setString(4, endereco.getEnd_complemento());
                preparedStatement.setString(5, endereco.getEnd_bairro());
                preparedStatement.setString(6, endereco.getEnd_cidade());
                preparedStatement.setString(7, endereco.getEnd_estado());
                preparedStatement.setLong(8, endereco.getId_usuario());
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

    public List<Endereco> listEndereco(Long id_usuario){
        ArrayList<Endereco> enderecos = new ArrayList<>();
        try {
            String sql = "select * from endereco where id_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id_usuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Endereco endereco = new Endereco();
                endereco.setId_endereco(resultSet.getLong("id_endereco"));
                endereco.setEnd_cep(resultSet.getString("end_cep"));
                endereco.setEnd_rua(resultSet.getString("end_rua"));
                endereco.setEnd_numero(resultSet.getInt("end_numero"));
                endereco.setEnd_complemento(resultSet.getString("end_complemento"));
                endereco.setEnd_bairro(resultSet.getString("end_bairro"));
                endereco.setEnd_cidade(resultSet.getString("end_cidade"));
                endereco.setEnd_estado(resultSet.getString("end_estado"));
                endereco.setId_usuario(id_usuario);
                enderecos.add(endereco);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return enderecos;
    }

    public boolean deleteEndereco(Long id){
        try {
            String sql = "delete from endereco where id_endereco = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
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

    public Endereco pesquisaEndereco(Long id){
        Endereco endereco = new Endereco();
        try {
            String sql = "select * from endereco where id_endereco = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                endereco.setId_endereco(id);
                endereco.setEnd_cep(resultSet.getString("end_cep"));
                endereco.setEnd_rua(resultSet.getString("end_rua"));
                endereco.setEnd_numero(resultSet.getInt("end_numero"));
                endereco.setEnd_complemento(resultSet.getString("end_complemento"));
                endereco.setEnd_bairro(resultSet.getString("end_bairro"));
                endereco.setEnd_cidade(resultSet.getString("end_cidade"));
                endereco.setEnd_estado(resultSet.getString("end_estado"));
                endereco.setId_usuario(resultSet.getLong("id_usuario"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return endereco;
    }

    public boolean updateEndereco(Endereco endereco){
        try {
            String sql = "update endereco set end_cep = ?, end_rua = ?, end_numero = ?, end_complemento = ?, end_bairro = ?, " +
                    " end_cidade = ?, end_estado = ? where id_endereco = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,endereco.getEnd_cep());
            preparedStatement.setString(2,endereco.getEnd_rua());
            preparedStatement.setInt(3,endereco.getEnd_numero());
            preparedStatement.setString(4,endereco.getEnd_complemento());
            preparedStatement.setString(5,endereco.getEnd_bairro());
            preparedStatement.setString(6,endereco.getEnd_cidade());
            preparedStatement.setString(7,endereco.getEnd_estado());
            preparedStatement.setLong(8,endereco.getId_endereco());
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isAddressAlreadyExist(Endereco endereco){
        try {
            String sql = "select * from endereco where end_cep ilike(?) and end_numero = ? and end_complemento ilike(?) and id_endereco <> ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,endereco.getEnd_cep());
            preparedStatement.setInt(2,endereco.getEnd_numero());
            preparedStatement.setString(3,endereco.getEnd_complemento());
            preparedStatement.setLong(4,endereco.getId_endereco());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //<<=============================Telefone====================================================>

}
