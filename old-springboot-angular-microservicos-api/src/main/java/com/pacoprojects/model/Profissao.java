package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter @Setter @ToString @RequiredArgsConstructor @AllArgsConstructor
@Table
@Entity
public class Profissao implements Serializable {

    @Id
    @SequenceGenerator(name = "profissao_sequence", sequenceName = "profissao_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profissao_sequence")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "profissao")
    @ToString.Exclude
    private List<Usuario> usuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Profissao profissao = (Profissao) o;

        return Objects.equals(id, profissao.id);
    }

    @Override
    public int hashCode() {
        return 1524912009;
    }
}
