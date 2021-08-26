package br.com.curso.webmvnspringbootmicroservicos.dto;

import br.com.curso.webmvnspringbootmicroservicos.model.Profissao;
import br.com.curso.webmvnspringbootmicroservicos.model.Role;
import br.com.curso.webmvnspringbootmicroservicos.model.Telefone;
import br.com.curso.webmvnspringbootmicroservicos.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class UsuarioDTOGET implements Serializable {

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
    private String jwt;
    private LocalDate dataNascimento;
    private Profissao profissao;
    private BigDecimal salario;
    private List<Role> roles;
    private List<Telefone> telefones;

    public UsuarioDTOGET(Usuario usuario) {
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
        this.jwt = usuario.getJwt();
        this.roles = usuario.getRoles();
        this.telefones = usuario.getTelefones();
        this.dataNascimento = usuario.getDataNascimento();
        this.profissao = usuario.getProfissao();
        this.salario = usuario.getSalario();
    }
}
