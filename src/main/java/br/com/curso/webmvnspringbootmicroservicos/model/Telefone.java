package br.com.curso.webmvnspringbootmicroservicos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Getter @Setter @ToString @RequiredArgsConstructor @AllArgsConstructor
@Table
@Entity
public class Telefone implements Serializable {

    @Id
    @SequenceGenerator(name = "telefone_sequence", sequenceName = "telefone_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telefone_sequence")
    private Long id;

    @NotBlank(message = "Numero de Telefone deve ser preenchido")
    @Column(name = "numero", nullable = false)
    private String numero;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "usuario_id", value = ConstraintMode.CONSTRAINT))
    private Usuario usuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Telefone telefone = (Telefone) o;

        return Objects.equals(id, telefone.id);
    }

    @Override
    public int hashCode() {
        return 1941305906;
    }
}
