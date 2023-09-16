package br.com.curso.web_mvn_jsf_2_2;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlCommandButton;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@ManagedBean
public class Usuario implements Serializable {
    private String nome;
    private String sobrenome;
    private String password;
    private String texto;
    private String nomeCompleto;
    private List<String> nomes = new ArrayList<>();

    private HtmlCommandButton commandButton; // BackBean, controlar os componentes da tela pelo back-end

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public List<String> getNomes() {
        return nomes;
    }

    public void setNomes(List<String> nomes) {
        this.nomes = nomes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void mostrarNomeCompleto(){
        nomeCompleto =  nome + " " + sobrenome;
    }

    public String addNomes(){
        mostrarNomeCompleto();
        nomes.add(nomeCompleto);

        if (nomes.size() > 3){
            commandButton.setValue("Lista Maior que 3");
            commandButton.setDisabled(true);
            return "news?faces-redirect=true"; /* Caso a lista de usuarios seja maior que 3,
                            entao redirectiona para outra pagina sendRedirect = nomePagina?faces-redirect=true|| Foward = nomePagina */
        }
        return ""; // Null ou Vazio o JSF fica na mesma pagina -> outcome
    }

    public HtmlCommandButton getCommandButton() {
        return commandButton;
    }

    public void setCommandButton(HtmlCommandButton commandButton) {
        this.commandButton = commandButton;
    }
}
