package model;

import jdk.jfr.Unsigned;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
public class Framework implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_framework;

    @Column(nullable = false)
    private String nome_framework;

    @ManyToMany(mappedBy = "frameworks", cascade = {CascadeType.MERGE})
    private List<Usuario> usuarios = new ArrayList<>();

    public Long getId_framework() {
        return id_framework;
    }

    public void setId_framework(Long id_framework) {
        this.id_framework = id_framework;
    }

    public String getNome_framework() {
        return nome_framework;
    }

    public void setNome_framework(String nome_framework) {
        this.nome_framework = nome_framework;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Framework)) return false;
        Framework framework = (Framework) o;
        return Objects.equals(id_framework, framework.id_framework);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_framework);
    }

    @Override
    public String toString() {
        return "Framework{" +
                "id_framework=" + id_framework +
                ", nome_framework='" + nome_framework + '\'' +
                '}';
    }
}
