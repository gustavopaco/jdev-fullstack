package com.pacoprojects.revjsbtapi.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table
@Entity
public class Usuario implements Serializable {

    @EqualsAndHashCode.Include
    @SequenceGenerator(name = "usuario_id", sequenceName = "usuario_id", allocationSize = 1)
    @GeneratedValue(generator = "usuario_id", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(updatable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "idade", nullable = false)
    private int idade;
}
