package br.com.curso.webmvnspringbootmicroservicos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter @Setter @ToString @RequiredArgsConstructor @AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(name = "username", columnNames = "username"))
@Entity
public class Usuario implements UserDetails {

    @Id
    @SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Nome Obrigatorio")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "Username Obrigatorio")
    @Column(name = "username")
    private String username;

    @Size(min = 3, message = "Campo password deve ter no minimo 3 caracteres")
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "usuario_role",
            joinColumns = @JoinColumn(name = "usuario_id"),
            foreignKey = @ForeignKey(name = "usuario_id", value = ConstraintMode.CONSTRAINT),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            inverseForeignKey = @ForeignKey(name = "role_id", value = ConstraintMode.CONSTRAINT),
            uniqueConstraints = @UniqueConstraint(name = "usuario_role_uq",columnNames = {"usuario_id","role_id"}))
    private List<Role> roles;

    @ToString.Exclude
    @OneToMany(mappedBy = "usuario", cascade = {CascadeType.MERGE, CascadeType.REMOVE,CascadeType.PERSIST})
    private List<Telefone> telefones;

    @Type(type = "text")
    @Column(name = "jwt")
    private String jwt;

    @CPF(message = "CPF invalido, por favor informe um CPF corretamente")
    @Column(name = "cpf")
    private String cpf;

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "localidade")
    private String localidade;

    @Column(name = "uf")
    private String uf;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double Longitude;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

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
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Usuario usuario = (Usuario) o;

        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return 1225039686;
    }
}
