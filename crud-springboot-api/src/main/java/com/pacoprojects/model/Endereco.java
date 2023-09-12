package com.pacoprojects.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity(name = "Endereco")
public class Endereco implements Serializable {

    @EqualsAndHashCode.Include
    @SequenceGenerator(name = "endereco_sequence", sequenceName = "endereco_sequence", allocationSize = 1)
    @GeneratedValue(generator = "endereco_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(updatable = false)
    private Long id;

    private String cep;
    private String rua;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(targetEntity = Usuario.class, optional = false)
    @JoinColumn(
            name = "usuario_id",
            foreignKey = @ForeignKey(name = "usuario_id_fk", value = ConstraintMode.CONSTRAINT),
            nullable = false)
    private Usuario usuario;

    public Endereco(EnderecoAPIResponse apiResponse) {
        this.cep = apiResponse.getCep();
        this.rua = apiResponse.getLogradouro();
        this.complemento = apiResponse.getComplemento();
        this.bairro = apiResponse.getBairro();
        this.cidade = apiResponse.getLocalidade();
        this.uf = apiResponse.getUf();
    }

}
