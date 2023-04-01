package com.pacoprojects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table
@Entity(name = "profissao")
public class Profissao {

    @SequenceGenerator(name = "profissao_sequence", sequenceName = "profissao_sequence", allocationSize = 1)
    @GeneratedValue(generator = "profissao_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Nome da profissão obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

//    @JsonIgnore
    @OneToMany(targetEntity = Usuario.class, mappedBy = "profissao")
    @ToString.Exclude
    private Set<Usuario> usuarios = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profissao profissao = (Profissao) o;

        if (!Objects.equals(id, profissao.id)) return false;
        return Objects.equals(nome, profissao.nome);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        return result;
    }
}
