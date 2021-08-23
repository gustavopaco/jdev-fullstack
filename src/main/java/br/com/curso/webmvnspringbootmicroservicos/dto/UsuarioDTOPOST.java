package br.com.curso.webmvnspringbootmicroservicos.dto;

import br.com.curso.webmvnspringbootmicroservicos.model.Role;
import br.com.curso.webmvnspringbootmicroservicos.model.Telefone;
import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class UsuarioDTOPOST implements Serializable {

    private Long id;
    private String nome;
    private String username;
    private String password;
    private String cpf;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private List<Role> roles;
    private List<Telefone> telefones;

    public UsuarioDTOPOST(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        this.cpf = usuario.getCpf();
        this.cep = usuario.getCep();
        this.logradouro = usuario.getLogradouro();
        this.complemento = usuario.getComplemento();
        this.bairro = usuario.getBairro();
        this.localidade = usuario.getLocalidade();
        this.uf = usuario.getUf();
        this.roles = usuario.getRoles();
        this.telefones = usuario.getTelefones();
    }
}
