package model;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Usuario.findAll", query = "select u from Usuario u"),
        @NamedQuery(name = "Usuario.findByName", query = "select u from Usuario u where u.nome_usuario =: nome")
})
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_usuario;

    private String nome_usuario;
    private String sobrenome_usuario;
    private String email_usuario;
    private String password_usuario;
    private int idade;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<TelefoneUsuario> telefoneUsuarios;

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getSobrenome_usuario() {
        return sobrenome_usuario;
    }

    public void setSobrenome_usuario(String sobrenome_usuario) {
        this.sobrenome_usuario = sobrenome_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getPassword_usuario() {
        return password_usuario;
    }

    public void setPassword_usuario(String password_usuario) {
        this.password_usuario = password_usuario;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public List<TelefoneUsuario> getTelefoneUsuarios() {
        return telefoneUsuarios;
    }

    public void setTelefoneUsuarios(List<TelefoneUsuario> telefoneUsuarios) {
        this.telefoneUsuarios = telefoneUsuarios;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", nome_usuario='" + nome_usuario + '\'' +
                ", sobrenome_usuario='" + sobrenome_usuario + '\'' +
                ", email_usuario='" + email_usuario + '\'' +
                ", password_usuario='" + password_usuario + '\'' +
                ", idade=" + idade +
                ", telefoneUsuarios=" + telefoneUsuarios +
                '}';
    }
}
