package com.pacoprojects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table
@Entity(name = "endereco")
public class Endereco {

    @SequenceGenerator(name = "endereco_sequence", sequenceName = "endereco_sequence", allocationSize = 1)
    @GeneratedValue(generator = "endereco_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Cep obrigatório.")
    @Column(name = "cep", nullable = false)
    private String cep;

    @NotBlank(message = "Rua obrigatório.")
    @Column(name = "rua", nullable = false)
    private String rua;

    @NotBlank(message = "Número obrigatório.")
    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @NotBlank(message = "Bairro obrigatório.")
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotBlank(message = "Cidade obrigatório.")
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NotBlank(message = "Estado obrigatório.")
    @Column(name = "estado", nullable = false)
    private String estado;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(
            name = "usuario_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "usuario_id_fk", value = ConstraintMode.CONSTRAINT))
    private Usuario usuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endereco endereco = (Endereco) o;

        if (!Objects.equals(id, endereco.id)) return false;
        return Objects.equals(numero, endereco.numero);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (numero != null ? numero.hashCode() : 0);
        return result;
    }
}
