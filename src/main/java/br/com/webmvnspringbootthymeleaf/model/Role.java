package br.com.webmvnspringbootthymeleaf.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
public class Role implements GrantedAuthority, Serializable {

    @Id
    @SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    private Long id;

    private String nomeRole;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuario = new ArrayList<>();

    public String getNomeRole() {
        return nomeRole;
    }

    public void setNomeRole(String nomeRole) {
        this.nomeRole = nomeRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(List<Usuario> usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getAuthority() { /* Retorna Nivel de Permissao do Usuario - Exemplo: ROLE_ADMIN, ROLE_GERENTE */
        return nomeRole;
    }
}
