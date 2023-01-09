package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table
@Entity(name = "Telefone")
public class Telefone implements Serializable {

    @EqualsAndHashCode.Include
    @SequenceGenerator(name = "telefone_sequence", sequenceName = "telefone_sequence", allocationSize = 1)
    @GeneratedValue(generator = "telefone_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "numero", nullable = false)
    private String numero;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "usuario_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "usuario_id", value = ConstraintMode.CONSTRAINT),
            nullable = false)
    private Usuario usuario;
}
