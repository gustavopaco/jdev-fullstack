package br.com.webmvnspringbootthymeleaf.model;

import javax.persistence.*;
import java.io.Serializable;

@Table
@Entity(name = "Profissao")
public class Profissao implements Serializable {

    @Id
    @SequenceGenerator(name = "profissao_sequence", sequenceName = "profissao_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profissao_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profissao)) return false;

        Profissao profissao = (Profissao) o;

        return id != null ? id.equals(profissao.id) : profissao.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Profissao{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
