package br.com.curso.webmvnspringbootmicroservicos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter @Setter @ToString @RequiredArgsConstructor @AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(name = "username", columnNames = "username"))
@Entity
public class Usuario implements Serializable, UserDetails {

    @Id
    @SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    private Long id;

    @NotBlank(message = "Nome Obrigatorio")
    @Column(name = "nome", updatable = false)
    private String nome;

    @NotBlank(message = "Username Obrigatorio")
    @Column(name = "username")
    private String username;

    @Size(min = 3, message = "Campo password deve ter no minimo 3 caracteres")
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_role",
            joinColumns = @JoinColumn(name = "usuario_id"),
            foreignKey = @ForeignKey(name = "usuario_id", value = ConstraintMode.CONSTRAINT),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            inverseForeignKey = @ForeignKey(name = "role_id", value = ConstraintMode.CONSTRAINT),
            uniqueConstraints = @UniqueConstraint(name = "usuario_role_uq",columnNames = {"usuario_id","role_id"}))
    private List<Role> roles;

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
