package com.pacoprojects.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pacoprojects.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "username_unique", columnNames = "username")})
@Entity(name = "usuario")
public class Usuario implements UserDetails {

    @SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence", allocationSize = 1)
    @GeneratedValue(generator = "usuario_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Username obrigatório.")
    @Email(message = "Por favor informe um e-mail corretamente.")
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "Password obrigatório.")
    @Size(min = 8, message = "Campo de Password deve ser maior que 8 caracteres.")
    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @Column(name = "jwt")
    private String jwt;

    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_role",
    joinColumns = @JoinColumn(name = "usuario_id", nullable = false),
    foreignKey = @ForeignKey(name = "usuario_id_fk", value = ConstraintMode.CONSTRAINT),
    inverseJoinColumns = @JoinColumn(name = "role_id",nullable = false),
    inverseForeignKey = @ForeignKey(name = "role_id_fk", value = ConstraintMode.CONSTRAINT),
    uniqueConstraints = @UniqueConstraint(name = "usuario_role_unique", columnNames = {"usuario_id", "role_id"}))
    private Set<Role> authorities = new LinkedHashSet<>();

    @JsonIgnore
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
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
