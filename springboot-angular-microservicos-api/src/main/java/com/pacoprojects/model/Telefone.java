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
@Entity(name = "telefone")
public class Telefone {

    @SequenceGenerator(name = "telefone_sequence", sequenceName = "telefone_sequence", allocationSize = 1)
    @GeneratedValue(generator = "telefone_sequence", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "Telefone obrigatório.")
    @Column(name = "numero", nullable = false)
    private String numero;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(
            name = "usuario_id", nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "usuario_id_fk", value = ConstraintMode.CONSTRAINT))
    private Usuario usuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Telefone telefone = (Telefone) o;

        if (!Objects.equals(id, telefone.id)) return false;
        return Objects.equals(numero, telefone.numero);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (numero != null ? numero.hashCode() : 0);
        return result;
    }
}
