package beans;

public class BeanProjetoJSP {

	/* Classes que ficam no pacote Beans, servem para processar algo da Pagina em tempo de Execucao na Memoria */
	
	private String imprime= "Mensagem por Java Beans";
	private String nome;
	private String ano;
	private String sexo;
	
	public BeanProjetoJSP() {
		
	}
	
	public String getImprime() {
		return imprime;
	}
	
	public int getCalculaNumero(int numero) {
		return numero * 100;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public void setImprime(String imprime) {
		this.imprime = imprime;
	}
	
	
	
}
