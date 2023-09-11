package model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class TelefonePessoa implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "*Campo Obrigatorio")
    private String numeroTelefone;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelefonePessoa)) return false;
        TelefonePessoa that = (TelefonePessoa) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TelefonePessoa{" +
                "id=" + id +
                ", numeroTelefone='" + numeroTelefone + '\'' +
                '}';
    }
}
