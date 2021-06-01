package banco;

import dao.ProdutoDAO;
import dao.UsuarioDAO;
import models.Endereco;
import models.Produto;
import models.Usuario;

import connection.SingleConnection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
		boolean checkCPF = new UsuarioDAO().isCPFValid("107.775.376-48");
		System.out.println("O CPF valido eh: " + checkCPF);
	}

	@Test
	public void TestValidaLoginBanco(){
		boolean checklogin = new UsuarioDAO().isLoginValid("GUSTAVOPACO@GMAIL.COM");
		System.out.println("O login valido eh: " + checklogin);

	}

	@Test
	public void TestInsertProdutos(){
		Produto produto = new Produto();
		produto.setNomeProduto("Couve");
		produto.setQuantidade(7);
		produto.setPreco(1.10);

		if (new ProdutoDAO().insertProduto(produto)){
			System.out.println("Produto Inserido");
		}else{
			System.out.println("Produto nao inserido");
		}
	}

	@Test
	public void TestListaProdutos(){
		List<Produto> produtos = new ProdutoDAO().listProduto();
		for (Produto p:produtos) {
			System.out.println(p);
		}
	}

	@Test
	public void TestInsertEndereco(){
		Endereco endereco = new Endereco();
		endereco.setEnd_cep("35030765");
		endereco.setEnd_rua("Sargento Johnny da Silva");
		endereco.setEnd_numero(404);
		endereco.setEnd_complemento("Bloco 1, Apt 201");
		endereco.setEnd_bairro("Betania");
		endereco.setEnd_cidade("Belo Horizonte");
		endereco.setEnd_estado("MG");
		endereco.setId_usuario(72L);
		new UsuarioDAO().insertEndereco(endereco);
	}
}
