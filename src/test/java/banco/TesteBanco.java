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

	@Test
	public void TestValidaCPFBanco(){
		Boolean checkCPF = new UsuarioDAO().isCPFValid("107.775.376-48");
		System.out.println("O CPF valido eh: " + checkCPF);
	}

	@Test
	public void TestValidaLoginBanco(){
		Boolean checklogin = new UsuarioDAO().isLoginValid("GUSTAVOPACO@GMAIL.COM");
		System.out.println("O login valido eh: " + checklogin);

	}
}
