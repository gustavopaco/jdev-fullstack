package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "username_unique", columnNames = "username"),
        @UniqueConstraint(name = "cpf_unique", columnNames = "cpf")})
@Entity(name = "usuario")
public class Usuario implements UserDetails {

    @SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence", allocationSize = 1)
    @GeneratedValue(generator = "usuario_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Nome obrigatório.")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "Username obrigatório.")
    @Email(message = "Por favor informe um e-mail corretamente.")
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "Password obrigatório.")
    @Size(min = 8, message = "Campo de Password deve ser maior que 8 caracteres.")
    @Column(name = "password", nullable = false)
    private String password;

    @CPF(message = "CPF inválido.")
    @Column(name = "cpf", nullable = false)
    private String cpf;

    //    @NotNull(message = "Salário obrigatório.")
    @Column(name = "salario")
    private Double salario;


    //    @NotNull(message = "Data de nascimento obrigatório.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDate dataNascimento;


    @JsonIgnore
    @Column(name = "jwt", columnDefinition = "TEXT")
    private String jwt;

    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_role",
            joinColumns = @JoinColumn(name = "usuario_id", nullable = false),
            foreignKey = @ForeignKey(name = "usuario_id_fk", value = ConstraintMode.CONSTRAINT),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false),
            inverseForeignKey = @ForeignKey(name = "role_id_fk", value = ConstraintMode.CONSTRAINT),
            uniqueConstraints = @UniqueConstraint(name = "usuario_role_unique", columnNames = {"usuario_id", "role_id"}))
    private Set<Role> authorities = new LinkedHashSet<>();

    @Valid
    @ToString.Exclude
    @OneToMany(targetEntity = Telefone.class, mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Telefone> telefones = new LinkedHashSet<>();

    @Valid
    @ToString.Exclude
    @OneToMany(targetEntity = Endereco.class, mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Endereco> enderecos = new LinkedHashSet<>();

    @ToString.Exclude
    @ManyToOne(targetEntity = Profissao.class)
    @JoinColumn(name = "profissao_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "profissao_id_fk", value = ConstraintMode.CONSTRAINT))
    private Profissao profissao;

    @JsonIgnore
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (!Objects.equals(id, usuario.id)) return false;
        return Objects.equals(username, usuario.username);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
