package com.pacoprojects.role;

import com.pacoprojects.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "authority_unique", columnNames = "authority")})
@Entity(name = "role")
public class Role implements GrantedAuthority {

    @SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence", allocationSize = 1)
    @GeneratedValue(generator = "role_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Permissão obrigatório.")
    @Column(name = "authority",nullable = false)
    private String authority;

    @ManyToMany(targetEntity = Usuario.class, mappedBy = "authorities")
    @ToString.Exclude
    private Set<Usuario> usuarios = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!Objects.equals(id, role.id)) return false;
        return Objects.equals(authority, role.authority);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (authority != null ? authority.hashCode() : 0);
        return result;
    }
}
