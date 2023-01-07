package com.pacoprojects.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table
@Entity(name = "Usuario")
public class Usuario implements Serializable {

    @EqualsAndHashCode.Include
    @SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence", allocationSize = 1)
    @GeneratedValue(generator = "usuario_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "senha", nullable = false)
    private String senha;
}
