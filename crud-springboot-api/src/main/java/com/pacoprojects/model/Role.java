package com.pacoprojects.model;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table
@Entity(name = "Role")
public class Role implements Serializable, GrantedAuthority {

    @EqualsAndHashCode.Include
    @SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence", allocationSize = 1)
    @GeneratedValue(generator = "role_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "nome_role", nullable = false)
    private String authority; /* Papel EXEMPLO, ROLE_SECRETARIO | ROLE_GERENTE | ROLE_ADMIN */

    @ManyToMany(targetEntity = Usuario.class, mappedBy = "authorities")
    private List<Usuario> usuarios;
}
