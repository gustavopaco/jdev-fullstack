package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor @NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "login", columnNames = "login")})
@Entity(name = "Usuario")
public class Usuario implements UserDetails {

    @EqualsAndHashCode.Include
    @SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence", allocationSize = 1)
    @GeneratedValue(generator = "usuario_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "senha", nullable = false)
    private String senha;

    @OneToMany(targetEntity = Telefone.class, mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Telefone> telefones = new ArrayList<>();

    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    /* Nome da Tabela Muitos para Muitos */
    @JoinTable(name = "usuario_role",
            /* Nome do atributo para referenciar tabela Usuario */
            joinColumns = @JoinColumn(name = "usuario_id"),
            /* Nome da FK do atributo usuario_id */
            foreignKey = @ForeignKey(name = "usuario_id_fk", value = ConstraintMode.CONSTRAINT),
            /* Nome do atributo para referenciar tabela Role */
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            /* Nome da FK do atributo role_id */
            inverseForeignKey = @ForeignKey(name = "role_id_fk", value = ConstraintMode.CONSTRAINT),
            /* Nome da chave unica para nao permitir usuario com dois papeis iguais*/
            /* Nome dos atributos da tabela que nao poderao ter dois papeis iguais Usuario1(ROLE_ADMIN,ROLE_ADMIN)*/
            uniqueConstraints = @UniqueConstraint(name = "usuario_role_unique", columnNames = {"usuario_id", "role_id"}))
    private List<Role> authorities = new ArrayList<>();

/*    @OneToMany(targetEntity = Telefone.class, mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Telefone> telefones;*/

    public void adicionarTelefonesAoUsuario(Usuario usuario) {
        usuario.getTelefones().forEach(telefone -> telefone.setUsuario(usuario));
    }

    public void adicionarTelefones(Telefone telefone) {
        telefone.setUsuario(this);
        this.telefones.add(telefone);
    }

    public Usuario(String login, String nome, String senha) {
        this.login = login;
        this.nome = nome;
        this.senha = senha;
    }

    /* Sao os acessos do usuario: ROLE_ADMIN | ROLE_VISITANTE */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles;
//    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.senha;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.login;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
