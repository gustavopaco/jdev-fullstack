package banco;

import dao.UsuarioDAO;
import models.Usuario;
import org.junit.Test;

import connection.SingleConnection;

import java.util.ArrayList;

public class TesteBanco {

	@Test
	public void TestBanco() {
		
		new SingleConnection();
		
	}
	
	@Test
	public void TestListaUser(){

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		ArrayList<Usuario> usuarios = new ArrayList<>();

		for (Usuario usuario :
				usuarioDAO.listarUsuarios()) {
			System.out.println(usuario);
		}


	}
	
}
