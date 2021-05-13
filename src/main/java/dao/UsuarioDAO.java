package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			
			if(resultSet.next()) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public void cadastrarUsuario(String login, String password) throws Exception{
		
		try {
			
			String sql = "insert into usuario (login,password) values (?,?)";
			PreparedStatement insertPreparedStatement = connection.prepareStatement(sql);
			
			insertPreparedStatement.setString(1, login);
			insertPreparedStatement.setString(2, password);
			
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
	
}
