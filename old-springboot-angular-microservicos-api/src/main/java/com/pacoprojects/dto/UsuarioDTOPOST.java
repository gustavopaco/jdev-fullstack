package com.pacoprojects.dto;

import com.pacoprojects.model.Profissao;
import com.pacoprojects.model.Role;
import com.pacoprojects.model.Telefone;
import com.pacoprojects.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private LocalDate dataNascimento;
    private Profissao profissao;
    private BigDecimal salario;
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
        this.dataNascimento = usuario.getDataNascimento();
        this.profissao = usuario.getProfissao();
        this.salario = usuario.getSalario();
    }
}
