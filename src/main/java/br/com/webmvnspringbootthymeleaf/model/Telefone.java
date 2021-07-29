package br.com.webmvnspringbootthymeleaf.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Table
@Entity(name = "Telefone")
public class Telefone {

    @Id
    @SequenceGenerator(name = "telefone_sequence", sequenceName = "telefone_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telefone_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @NotEmpty(message = "Campo numero obrigatorio")
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotEmpty(message = "Campo tipo de telefone obrigatorio")
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "pessoa_id",nullable = false, foreignKey = @ForeignKey(name = "pessoa_id"))
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        if (!(o instanceof Telefone telefone)) return false;

        return Objects.equals(id, telefone.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Telefone{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
