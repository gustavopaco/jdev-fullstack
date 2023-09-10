package bean;

public class BeanUsuario {

	private String email;
	private String senha;
	
	
	public String getLogin() {
		return email;
	}
	public void setLogin(String login) {
		this.email = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Boolean validarLoginSenha(String email, String senha) {
		
		if(email.equals("admin") && senha.equals("admin")) {
			this.email = email;
			this.senha = senha;
			return true;
		}
		return false;
	}
	
	
}
